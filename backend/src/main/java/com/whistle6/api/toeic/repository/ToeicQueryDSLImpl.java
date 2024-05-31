package com.whistle6.api.toeic.repository;

import java.util.List;
import java.util.Optional;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.whistle6.api.toeic.model.QToeic;
import com.whistle6.api.toeic.model.Toeic;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ToeicQueryDSLImpl implements ToeicQueryDSL{

    private final JPAQueryFactory queryFactory;
    private final QToeic qToeic = QToeic.toeic;

    @Override
    public Boolean existsByIdDSL(Long id) {
        return queryFactory.select(qToeic.id.count()).from(qToeic).where(qToeic.id.eq(id)).fetchOne() == 1L;
    }

    @Override
    public Boolean existsByQuestionDSL(String question) {
        return queryFactory.select(qToeic.id.count()).from(qToeic).where(qToeic.question.eq(question)).fetchOne() == 1L;
    }

    @Override
    public Optional<Toeic> findByIdDSL(Long id) {
        return Optional.ofNullable(queryFactory.selectFrom(qToeic).where(qToeic.id.eq(id)).fetchOne());
    }

    @Override
    public List<Toeic> findAllDSL() {
        return queryFactory.selectFrom(qToeic).fetch();
    }

    @Override
    public Optional<Toeic> finaByIdDSL(Long id) {
        return Optional.ofNullable(queryFactory.selectFrom(qToeic).where(qToeic.id.eq(id)).fetchOne());
    }

    @Override
    public Long countAllDSL() {
        return queryFactory.select(qToeic.id.count()).from(qToeic).fetchOne();
    }

    @Override
    public Optional<Toeic> findRandomDSL(Long offset) {
        return Optional.ofNullable(queryFactory.selectFrom(qToeic).offset(offset).limit(1).fetchOne());
    }
    
}
