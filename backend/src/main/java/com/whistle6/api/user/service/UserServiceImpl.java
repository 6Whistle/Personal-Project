package com.whistle6.api.user.service;

import org.springframework.stereotype.Service;

import com.whistle6.api.common.component.Messenger;
import com.whistle6.api.common.component.exception.AuthException;
import com.whistle6.api.common.component.security.JwtProvider;
import com.whistle6.api.common.enums.MessageCode;
import com.whistle6.api.common.enums.RoleCode;
import com.whistle6.api.token.model.Token;
import com.whistle6.api.token.repository.TokenRepository;
import com.whistle6.api.user.model.User;
import com.whistle6.api.user.model.UserDTO;
import com.whistle6.api.user.repository.UserRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    // ==================== Command ====================

    @Override
    @Transactional
    public Messenger save(UserDTO dto) {
        return MessageCode.GenerateMessenger(
            Stream.of(dto)
            .filter(i -> userRepository.existsByEmailDSL(i.getEmail()).equals(false))
            .map(i -> userRepository.save(User.builder()
                .email(i.getEmail())
                .password(i.getPassword())
                .name(i.getName())
                .phone(i.getPhone())
                .birth(i.getBirth())
                .role(RoleCode.USER.getCode())
                .build()))
            .map(i -> MessageCode.SUCCESS)
            .findAny()
            .orElseGet(() -> MessageCode.FAIL));
    }

    @Override
    @Transactional
    public Messenger update(UserDTO dto) {
        return MessageCode.GenerateMessenger(
                Stream.of(dto)
                .map(i -> userRepository.findByIdDSL(i.getId()).orElseGet(User::new))
                .filter(i -> i.getId() != null)
                .peek(i -> i.setPassword(dto.getPassword()))
                .peek(i -> i.setName(dto.getName()))
                .peek(i -> i.setPhone(dto.getPhone()))
                .peek(i -> i.setBirth(dto.getBirth()))
                .map(i -> userRepository.save(i))
                .map(i -> MessageCode.SUCCESS)
                .findAny()
                .orElseGet(() -> MessageCode.FAIL)
            );
    }

    @Override
    @Transactional
    public Messenger deleteById(Long id) {
        return MessageCode.GenerateMessenger(
            Stream.of(id)
            .map(i -> userRepository.findByIdDSL(i).orElseGet(User::new))
            .filter(i -> i.getId() != null)
            .peek(i -> deleteToken(i))
            .peek(i -> userRepository.deleteById(i.getId()))
            .map(i -> MessageCode.SUCCESS)
            .findAny()
            .orElseGet(() -> MessageCode.FAIL)    
        );
    }

    @Override
    @Transactional
    public Messenger deleteAll() {
        return MessageCode.GenerateMessenger(
            Stream.of(MessageCode.SUCCESS)
            .peek(i -> tokenRepository.deleteAll())
            .peek(i -> userRepository.deleteAll())
            .map(i -> MessageCode.SUCCESS)
            .findAny()
            .get()
        );
    }

    // ==================== Query ====================

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAllDSL().stream().map(this::toDTO).toList();
    }

    @Override
    public UserDTO findById(Long id) {
        return userRepository.findByIdDSL(id).stream().map(this::toDTO).findAny().orElseGet(UserDTO::new);
    }

    @Override
    public Long count() {
        return userRepository.countDSL();
    }

    @Override
    public Boolean existsById(Long id) {
        return userRepository.existsByIdDSL(id);
    }

    // ==================== Custom ====================

    @Override
    public Messenger existsByEmail(String email) {
        return Stream.of(userRepository.existsByEmailDSL(email) == true ? MessageCode.SUCCESS : MessageCode.FAIL)
        .map(i -> MessageCode.GenerateMessenger(i))
        .findAny().get();
    }

    @Override
    public Messenger login(UserDTO userDTO) {
        return Stream.of(userDTO)
        .map(i -> userRepository.findByEmailDSL(i.getEmail()).orElseGet(User::new))
        .filter(i -> i.getId() != null)
        .filter(i -> i.getPassword().equals(userDTO.getPassword()))
        .map(i -> deleteToken(i))
        .peek(i -> i.setToken(tokenRepository.save(
                                Token.builder()
                                .user(i)
                                .expiredAt(jwtProvider.getExpiredAt())
                                .refreshToken(jwtProvider.createRefreshToken(UserDTO.builder().role(i.getRole()).build()))
                                .build())))
        .map(i -> userRepository.save(i))
        .map(i -> Messenger.builder()
            .status(MessageCode.SUCCESS.getStatus())
            .message(MessageCode.SUCCESS.getMessage())
            .refreshToken(i.getToken().getRefreshToken())
            .accessToken(jwtProvider.createAccessToken(UserDTO.builder().role(i.getRole()).build()))
            .build())
        .findAny()
        .orElseGet(() -> MessageCode.GenerateMessenger(MessageCode.FAIL));
    }

    @Override
    @Transactional
    public User deleteToken(User user) {
        return Stream.of(user)
        .filter(i -> i.getToken() != null)
        .peek(i -> tokenRepository.deleteById(i.getToken().getId()))
        .peek(i -> i.setToken(null))
        .map(i -> userRepository.save(i))
        .findAny()
        .orElseGet(() -> user);
    }

    @Override
    public Messenger refresh(String refreshToken) {
        return Stream.of(refreshToken)
        .filter(i -> i.startsWith("Bearer "))
        .map(i -> i.substring(7))
        .filter(i -> jwtProvider.validateToken(i, "refresh"))
        .map(i -> tokenRepository.findByRefreshTokenDSL(i).orElseGet(() -> Token.builder().build()))
        .filter(i -> i.getId() != null)
        .map(i -> jwtProvider.createAccessToken(toDTO(i.getUser())))
        .map(i -> Messenger.builder()
                    .accessToken(i)
                    .status(MessageCode.SUCCESS.getStatus())
                    .message(MessageCode.SUCCESS.getMessage())
                    .build())
        .findAny()
        .orElseThrow(() -> new AuthException());
    }
}
