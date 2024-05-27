package com.whistle6.api.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whistle6.api.common.component.Messenger;
import com.whistle6.api.user.model.UserDTO;
import com.whistle6.api.user.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    // ==================== Command ====================

    @PostMapping("/register")
    public ResponseEntity<Messenger> register(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(
            userService.save(userDTO)
        );
    }

    @PutMapping("/update")
    public ResponseEntity<Messenger> update(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(
            userService.update(userDTO)
        );
    }

    @PutMapping("/delete")
    public ResponseEntity<Messenger> delete(@RequestParam(name = "id") Long id) {
        return ResponseEntity.ok(
            userService.deleteById(id)
        );
    }

    // ==================== Query ====================

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(
            userService.findAll()
        );
    }

    @GetMapping("/check-email")
    public ResponseEntity<Messenger> existsByEmail(@RequestParam(name = "email") String email) {
        return ResponseEntity.ok(
            userService.existsByEmail(email)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<Messenger> login(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(
            userService.login(userDTO)
        );
    }

}
