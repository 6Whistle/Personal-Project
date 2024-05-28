package com.whistle6.api.toeic.model;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ToeicDTO {
    private Long id;
    private Integer part;
    private String question;
    private String answer;
    private String image;
    private String choices;
}
