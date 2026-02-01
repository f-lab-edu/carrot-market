package com.carrotmarket.controller;

import com.carrotmarket.controller.dto.request.TokenValidationRequest;
import com.carrotmarket.controller.dto.response.TokenValidationResponse;
import com.carrotmarket.service.JwtTokenProvider;
import com.carrotmarket.service.TokenBlacklistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/token")
@RestController
public class TokenValidationController {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenBlacklistService tokenBlacklistService;

    @PostMapping("/validate")
    public ResponseEntity<TokenValidationResponse> validateToken(
            @RequestHeader HttpHeaders headers,
            @RequestBody @Validated TokenValidationRequest request
    ) {
        try {
            String accessToken = request.token();

            if (!jwtTokenProvider.validateAccessToken(accessToken)) {
                return ResponseEntity.ok(new TokenValidationResponse(false, null, "유효하지 않은 토큰입니다."));
            }

            String userId = jwtTokenProvider.getUserIdFromAccessToken(accessToken);
            boolean isBlacklisted = tokenBlacklistService.isTokenBlacklisted(userId, accessToken);

            if (isBlacklisted) {
                return ResponseEntity.ok(new TokenValidationResponse(false, userId, "블랙리스트된 토큰입니다."));
            }

            return ResponseEntity.ok(new TokenValidationResponse(true, userId, null));
        } catch (Exception e) {
            headers.getFirst(HttpHeaders.AUTHORIZATION);
            log.error("[ERROR] TID: {} | message : {}", headers.get("TID"), e.getMessage());
            return ResponseEntity.ok(new TokenValidationResponse(false, null, "토큰 검증 중 오류가 발생했습니다."));
        }
    }

}
