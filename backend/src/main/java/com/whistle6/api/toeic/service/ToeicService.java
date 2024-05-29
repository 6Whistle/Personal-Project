package com.whistle6.api.toeic.service;

import java.util.stream.Stream;

import com.whistle6.api.common.command.CommandService;
import com.whistle6.api.common.query.QueryService;
import com.whistle6.api.toeic.model.Toeic;
import com.whistle6.api.toeic.model.ToeicDTO;

public interface ToeicService extends CommandService<Toeic, ToeicDTO>, QueryService<Toeic, ToeicDTO>{
    default ToeicDTO toDTO(Toeic entity) {
        return ToeicDTO.builder()
            .id(entity.getId())
            .part(entity.getPart())
            .question(entity.getQuestion())
            .answer(entity.getAnswer())
            .image(entity.getImage())
            .choices(Stream.of(entity.getChoices().split(",")).toList())
            .build();
    }
}
