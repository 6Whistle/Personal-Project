package com.whistle6.api.user.service;

import org.springframework.stereotype.Service;

import com.whistle6.api.common.component.Messenger;
import com.whistle6.api.common.enums.MessageCode;
import com.whistle6.api.common.enums.RoleCode;
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
    private final UserRepository userRepository;

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
            .filter(i -> userRepository.existsByIdDSL(i))
            .peek(i -> userRepository.deleteById(i))
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
        return MessageCode.GenerateMessenger(Stream.of(userDTO)
        .map(i -> userRepository.findByEmailDSL(i.getEmail()).orElseGet(User::new))
        .filter(i -> i.getId() != null)
        .filter(i -> i.getPassword().equals(userDTO.getPassword()))
        .map(i -> MessageCode.SUCCESS)
        .findAny()
        .orElseGet(() -> MessageCode.FAIL));
    }
}
