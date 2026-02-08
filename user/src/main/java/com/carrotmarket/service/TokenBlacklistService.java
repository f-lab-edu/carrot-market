package com.carrotmarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenBlacklistService {
    private static final String BLACKLIST_PREFIX = "blacklist-";

    private final RedisTemplate<String, String> redisTemplate;

    public void blacklistToken(String userId, String token, long accessTokenExpirationMs) {
        redisTemplate.opsForSet()
                .add(
                        BLACKLIST_PREFIX + userId,
                        token
                );

        redisTemplate.expire(
                BLACKLIST_PREFIX + userId,
                accessTokenExpirationMs,
                TimeUnit.MILLISECONDS
        );
    }

    public boolean isTokenBlacklisted(String userId, String token) {
        return Boolean.TRUE.equals(
                redisTemplate.opsForSet()
                        .isMember(BLACKLIST_PREFIX + userId, token)
        );
    }

}
