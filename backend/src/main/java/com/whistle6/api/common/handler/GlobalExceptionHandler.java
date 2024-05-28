package com.whistle6.api.common.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.whistle6.api.common.component.Messenger;
import com.whistle6.api.common.component.exception.AuthException;
import com.whistle6.api.common.enums.MessageCode;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Messenger> handleAuthException(AuthException e) {
        return ResponseEntity.badRequest().body(MessageCode.GenerateMessenger(MessageCode.UNAUTHORIZED));
    }
}
