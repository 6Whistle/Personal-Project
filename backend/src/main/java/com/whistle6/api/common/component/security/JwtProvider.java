package com.whistle6.api.common.component.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

import com.whistle6.api.user.model.UserDTO;

@Component
public class JwtProvider {
    private final String issuer;

    private final SecretKey secretKey;

    private final Long accessExpiredDate;

    private final Long refreshExpiredDate;

    public JwtProvider(
        @Value("${jwt.iss}") String issuer,
        @Value("${jwt.secret}") String secretKey,
        @Value("${jwt.access-expired}") Long accessExpired,
        @Value("${jwt.refresh-expired}") Long refreshExpired
    ) {
        this.issuer = issuer;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
        this.accessExpiredDate = accessExpired;
        this.refreshExpiredDate = refreshExpired;
    }

    public String createAccessToken(UserDTO user){
        return Jwts.builder()
        .signWith(secretKey)
        .issuer(issuer)
        .expiration(Date.from(Instant.now().plus(accessExpiredDate, ChronoUnit.MILLIS)))
        .subject("access")
        .claim("role", user.getRole())
        .compact();
    }

    public String createRefreshToken(UserDTO user){
        return Jwts.builder()
        .signWith(secretKey)
        .issuer(issuer)
        .expiration(Date.from(Instant.now().plus(refreshExpiredDate, ChronoUnit.MILLIS)))
        .subject("refresh")
        .claim("role", user.getRole())
        .compact();
    }

    public Long getExpiredAt(){
        return Instant.now().plus(refreshExpiredDate, ChronoUnit.MILLIS).toEpochMilli();
    }

    public String parseBearerToken(HttpServletRequest request, String headerName){
        return Optional.ofNullable(request.getHeader(headerName))
                .stream()
                .filter(i -> i.startsWith("Bearer "))
                .map(i -> i.substring(7))
                .findAny()
                .orElseGet(() -> "undefined");
    }

    public Boolean validateToken(String token, String type){
        try {
            return Stream.of(Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token))
            .filter(i -> i.getPayload().getSubject().equals(type))
            .filter(i -> i.getPayload().getExpiration().after(Date.from(Instant.now())))
            .map(i -> true)
            .findAny()
            .orElseGet(() -> false);
        } catch (Exception e) {
            return false;
        }
    }
}
