package com.carrotmarket.controller;

import com.carrotmarket.controller.dto.request.TokenValidationRequest;
import com.carrotmarket.controller.dto.response.TokenValidationResponse;
import com.carrotmarket.exception.TokenValidatorException;
import com.carrotmarket.service.JwtTokenProvider;
import com.carrotmarket.service.TokenBlacklistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.carrotmarket.exception.ExceptionCode.BLACKLISTED_TOKEN;
import static com.carrotmarket.exception.ExceptionCode.INVALID_ACCESS_TOKEN;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/token")
@RestController
public class TokenValidationController {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenBlacklistService tokenBlacklistService;

    @PostMapping("/validate")
    public ResponseEntity<TokenValidationResponse> validateToken(
            @RequestBody @Validated TokenValidationRequest request
    ) {
        String accessToken = request.token();

        if (!jwtTokenProvider.validateAccessToken(accessToken)) {
            throw new TokenValidatorException(INVALID_ACCESS_TOKEN);
        }

        String userId = jwtTokenProvider.getUserIdFromAccessToken(accessToken);
        boolean isBlacklisted = tokenBlacklistService.isTokenBlacklisted(userId, accessToken);

        if (isBlacklisted) {
            throw new TokenValidatorException(BLACKLISTED_TOKEN);
        }

        return ResponseEntity.ok(new TokenValidationResponse(true));
    }

}
