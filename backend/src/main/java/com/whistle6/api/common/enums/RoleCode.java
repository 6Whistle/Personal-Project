package com.whistle6.api.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RoleCode {
    ADMIN("ROLE_ADMIN", "Adminstrator"),
    USER("ROLE_USER", "User");

    private final String code;
    private final String description;
}
