package com.whistle6.api.user.repository;

import java.util.Optional;
import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.whistle6.api.user.model.QUser;
import com.whistle6.api.user.model.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserQueryDSLImpl implements UserQueryDSL{

    private final JPAQueryFactory jpaQueryFactory;
    private final QUser qUser = QUser.user;

    @Override
    public Boolean existsByIdDSL(Long id) {
        return jpaQueryFactory.selectFrom(qUser)
            .where(qUser.id.eq(id))
            .fetchFirst() != null;
    }

    @Override
    public Boolean existsByEmailDSL(String email) {
        return jpaQueryFactory.selectFrom(qUser)
            .where(qUser.email.eq(email))
            .fetchFirst() != null;
    }

    @Override
    public Long countDSL() {
        return jpaQueryFactory.select(qUser.id.count()).from(qUser).fetchFirst();
    }

    @Override
    public List<User> findAllDSL() {
        return jpaQueryFactory.selectFrom(qUser).fetch();
    }

    @Override
    public Optional<User> findByIdDSL(Long id) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(qUser)
            .where(qUser.id.eq(id))
            .fetchFirst());
    }

    @Override
    public Optional<User> findByEmailDSL(String email) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(qUser)
            .where(qUser.email.eq(email))
            .fetchFirst());
    }

    

}
