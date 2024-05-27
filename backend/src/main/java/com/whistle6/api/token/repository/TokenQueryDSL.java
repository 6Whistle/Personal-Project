package com.whistle6.api.token.repository;

public interface TokenQueryDSL {
    Boolean existsByRefreshTokenDSL(String token);
}
