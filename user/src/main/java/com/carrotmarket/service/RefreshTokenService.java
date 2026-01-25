package com.carrotmarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final static String REFRESH_TOKEN_PREFIX = "refreshToken-";
    private final RedisTemplate<String, String> redisTemplate;

    public void save(String userId, String refreshToken, long expirationMs) {
        redisTemplate.opsForValue()
                .set(
                        REFRESH_TOKEN_PREFIX + userId,
                        refreshToken,
                        Duration.ofMillis(expirationMs)
                );
    }

    public String get(String userId) {
        return redisTemplate
                .opsForValue()
                .get(REFRESH_TOKEN_PREFIX + userId);
    }

    public void delete(String userId) {
        redisTemplate.delete(REFRESH_TOKEN_PREFIX + userId);
    }

}
