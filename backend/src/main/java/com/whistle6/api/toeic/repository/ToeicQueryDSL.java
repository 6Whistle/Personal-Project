package com.whistle6.api.toeic.repository;

import java.util.List;
import java.util.Optional;

import com.whistle6.api.toeic.model.Toeic;

public interface ToeicQueryDSL {
    Boolean existsByIdDSL(Long id);
    Boolean existsByQuestionDSL(String question);
    List<Toeic> findAllDSL();
    Optional<Toeic> findByIdDSL(Long id);
    Optional<Toeic> finaByIdDSL(Long id);
    Long countAllDSL();

}
