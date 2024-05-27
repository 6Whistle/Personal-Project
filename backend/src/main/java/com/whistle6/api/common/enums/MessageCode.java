package com.whistle6.api.common.enums;

import com.whistle6.api.common.component.Messenger;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MessageCode {
    SUCCESS(200, "Success"),
    FAIL(400, "Fail"),
    NOT_FOUND(404, "Not Found"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final Integer status;
    private final String message;

    public static Messenger GenerateMessenger(MessageCode messageCode) {
        return Messenger.builder()
            .status(messageCode.getStatus())
            .message(messageCode.getMessage())
            .build();
    }
}
