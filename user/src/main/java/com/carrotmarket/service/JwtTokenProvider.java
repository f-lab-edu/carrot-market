package com.carrotmarket.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    private final SecretKey accessTokenSecretKey;
    private final SecretKey refreshTokenSecretKey;
    @Getter
    private final long accessTokenExpirationMs;
    @Getter
    private final long refreshTokenExpirationMs;

    public JwtTokenProvider(
            @Value("${jwt.accessToken.secret}") String accessTokenSecret,
            @Value("${jwt.refreshToken.secret}") String refreshTokenSecret,
            @Value("${jwt.accessToken.expirationMs}") long accessTokenExpirationMs,
            @Value("${jwt.refreshToken.expirationMs}") long refreshTokenExpirationMs
    ) {
        this.accessTokenSecretKey = Keys.hmacShaKeyFor(accessTokenSecret.getBytes());
        this.refreshTokenSecretKey = Keys.hmacShaKeyFor(refreshTokenSecret.getBytes());
        this.accessTokenExpirationMs = accessTokenExpirationMs;
        this.refreshTokenExpirationMs = refreshTokenExpirationMs;
    }

    public String generateAccessToken(String userId) {
        return generateToken(userId, accessTokenSecretKey, accessTokenExpirationMs);
    }

    public String generateRefreshToken(String userId) {
        return generateToken(userId, refreshTokenSecretKey, refreshTokenExpirationMs);
    }

    private String generateToken(String userId, SecretKey secretKey, long expirationMs) {
        return Jwts.builder()
                .subject(userId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUserIdFromAccessToken(String token) {
        return parseToken(token, accessTokenSecretKey);
    }

    public String getUserIdFromRefreshToken(String token) {
        return parseToken(token, refreshTokenSecretKey);
    }

    private String parseToken(String token, SecretKey secretKey) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateAccessToken(String token) {
        return validateToken(token, accessTokenSecretKey);
    }

    public boolean validateRefreshToken(String token) {
        return validateToken(token, refreshTokenSecretKey);
    }

    private boolean validateToken(String token, SecretKey secretKey) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error("토큰 검증 실패: {}", e.getMessage());
            return false;
        }
    }

}
