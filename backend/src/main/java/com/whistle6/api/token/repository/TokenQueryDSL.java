package com.whistle6.api.token.repository;

import java.util.Optional;

import com.whistle6.api.token.model.Token;

public interface TokenQueryDSL {
    Boolean existsByRefreshTokenDSL(String token);
    Optional<Token> findByRefreshTokenDSL(String token);
}
