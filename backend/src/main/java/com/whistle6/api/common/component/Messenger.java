package com.whistle6.api.common.component;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Messenger {
    private Long id;
    private String message;
    private Integer status;
    private String accessToken;
    private String refreshToken;
    private Object values;
}
