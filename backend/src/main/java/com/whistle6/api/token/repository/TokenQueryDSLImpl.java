package com.whistle6.api.token.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.whistle6.api.token.model.QToken;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TokenQueryDSLImpl implements TokenQueryDSL{

    private final JPAQueryFactory jpaQueryFactory;
    private final QToken qToken = QToken.token;

    @Override
    public Boolean existsByRefreshTokenDSL(String token) {
        return jpaQueryFactory.selectFrom(qToken).where(qToken.refreshToken.eq(token)).fetchFirst() != null;
    }
}
