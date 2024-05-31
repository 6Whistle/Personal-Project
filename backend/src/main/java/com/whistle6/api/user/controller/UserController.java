package com.whistle6.api.user.controller;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whistle6.api.common.component.Messenger;
import com.whistle6.api.common.component.exception.AuthException;
import com.whistle6.api.common.enums.MessageCode;
import com.whistle6.api.user.model.UserDTO;
import com.whistle6.api.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    // ==================== Command ====================

    @PostMapping("/register")
    public ResponseEntity<Messenger> register(@RequestBody UserDTO userDTO) {
        log.info("register: {}", userDTO);
        return ResponseEntity.ok(
            userService.save(userDTO)
        );
    }

    @PutMapping("/update")
    public ResponseEntity<Messenger> update(@RequestHeader(name = "Authorization") String token, @RequestBody UserDTO userDTO) {
        log.info("update: {}", userDTO);
        return ResponseEntity.ok(
            Stream.of(token)
            .filter(i -> userService.checkTokenWithId(i, userDTO.getId()))
            .map(i -> userService.update(userDTO))
            .findAny()
            .orElseThrow(() -> new AuthException())
        );
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Messenger> delete(@RequestHeader(name = "Authorization") String token, @RequestParam(name = "id") Long id) {
        log.info("delete: {}", id);
        return ResponseEntity.ok(
            Stream.of(token)
            .filter(i -> userService.checkTokenWithId(i, id))
            .map(i -> userService.deleteById(id))
            .findAny()
            .orElseThrow(() -> new AuthException())
        );
    }

    // ==================== Query ====================

    @GetMapping("/detail")
    public ResponseEntity<Messenger> findById(@RequestHeader(name = "Authorization") String token, @RequestParam(name = "id") Long id) {
        log.info("findById: {}", id);
        return ResponseEntity.ok(
            Stream.of(token)
            .filter(i -> userService.checkTokenWithId(i, id))
            .map(i -> userService.findById(id))
            .map(i -> Messenger.builder().status(MessageCode.SUCCESS.getStatus()).message(MessageCode.SUCCESS.getMessage()).values(i).build())
            .findAny()
            .orElseThrow(() -> new AuthException())
        );
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> findAll(@RequestHeader(name = "Authorization") String token) {
        log.info("findAll");
        return ResponseEntity.ok(
            userService.findAll()
        );
    }

    @GetMapping("/check-email")
    public ResponseEntity<Messenger> existsByEmail(@RequestParam(name = "email") String email) {
        log.info("existsByEmail: {}", email);
        return ResponseEntity.ok(
            userService.existsByEmail(email)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<Messenger> login(@RequestBody UserDTO userDTO) {
        log.info("login: {}", userDTO);
        return ResponseEntity.ok(
            userService.login(userDTO)
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<Messenger> logout(@RequestBody UserDTO userDTO) {
        log.info("logout: {}", userDTO);
        return ResponseEntity.ok(
            userService.logout(userDTO)
        );
    }

    @GetMapping("/refresh")
    public ResponseEntity<Messenger> refresh(@RequestHeader(name = "Refresh-Token") String refreshToken) {
        log.info("refresh: {}", refreshToken);
        return ResponseEntity.ok(
            userService.refresh(refreshToken)
        );
    }

}
