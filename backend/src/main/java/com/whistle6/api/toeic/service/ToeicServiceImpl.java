package com.whistle6.api.toeic.service;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.whistle6.api.common.component.Messenger;
import com.whistle6.api.common.enums.MessageCode;
import com.whistle6.api.toeic.model.Toeic;
import com.whistle6.api.toeic.model.ToeicDTO;
import com.whistle6.api.toeic.repository.ToeicRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ToeicServiceImpl implements ToeicService {
    private final ToeicRepository toeicRepository;

    // ==================== Command ====================

    @Override
    @Transactional
    public Messenger save(ToeicDTO dto) {
        return Stream.of(dto)
        .filter(i -> i.getPart() != null && i.getQuestion() != null && i.getAnswer() != null)
        .filter(i -> !toeicRepository.existsByQuestionDSL(i.getQuestion()))
        .map(i -> toeicRepository.save(
            Toeic.builder()
            .part(i.getPart())
            .question(i.getQuestion())
            .answer(i.getAnswer())
            .image(i.getImage())
            .choices(i.getChoices() == null ? null : String.join(",", i.getChoices()))
            .build()))
        .map(i -> MessageCode.GenerateMessenger(MessageCode.SUCCESS))
        .findAny()
        .orElseGet(() -> MessageCode.GenerateMessenger(MessageCode.FAIL));
    }

    @Override
    @Transactional
    public Messenger update(ToeicDTO dto) {
        return Stream.of(dto)
        .filter(i -> i.getId() != null && i.getPart() != null && i.getQuestion() != null && i.getAnswer() != null)
        .filter(i -> !toeicRepository.existsByQuestionDSL(i.getQuestion()))
        .map(i -> toeicRepository.findByIdDSL(i.getId()).orElseGet(Toeic::new))
        .filter(i -> i.getId() != null)
        .peek(i -> i.setPart(dto.getPart()))
        .peek(i -> i.setQuestion(dto.getQuestion()))
        .peek(i -> i.setAnswer(dto.getAnswer()))
        .peek(i -> i.setImage(dto.getImage()))
        .peek(i -> i.setChoices(i.getChoices() == null ? null : String.join(",", i.getChoices())))
        .map(i -> toeicRepository.save(i))
        .map(i -> MessageCode.GenerateMessenger(MessageCode.SUCCESS))
        .findAny()
        .orElseGet(() -> MessageCode.GenerateMessenger(MessageCode.FAIL));
    }

    @Override
    public Messenger deleteById(Long id) {
        return Stream.of(id)
        .filter(i -> toeicRepository.existsByIdDSL(id))
        .peek(i -> toeicRepository.deleteById(id))
        .map(i -> MessageCode.GenerateMessenger(MessageCode.SUCCESS))
        .findAny()
        .orElseGet(() -> MessageCode.GenerateMessenger(MessageCode.FAIL));
    }

    @Override
    public Messenger deleteAll() {
        return Stream.of(true)
        .peek(i -> toeicRepository.deleteAll())
        .map(i -> MessageCode.GenerateMessenger(MessageCode.SUCCESS))
        .findAny()
        .get();
    }

    // ==================== Query ====================

    @Override
    public List<ToeicDTO> findAll() {
        return toeicRepository.findAllDSL().stream().map(this::toDTO).toList();
    }

    @Override
    public ToeicDTO findById(Long id) {
        return toeicRepository.finaByIdDSL(id).stream().map(this::toDTO).findAny().orElseGet(ToeicDTO::new);
    }

    @Override
    public Long count() {
        return toeicRepository.countAllDSL();
    }

    @Override
    public Boolean existsById(Long id) {
        return toeicRepository.existsByIdDSL(id);
    }
    
}
