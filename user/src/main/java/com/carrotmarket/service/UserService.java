package com.carrotmarket.service;

import com.carrotmarket.controller.dto.request.LoginRequestDto;
import com.carrotmarket.controller.dto.request.SignupRequestDto;
import com.carrotmarket.controller.dto.response.LoginResponseDto;
import com.carrotmarket.exception.CustomException;
import com.carrotmarket.model.User;
import com.carrotmarket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.carrotmarket.exception.ExceptionCode.*;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenBlacklistService tokenBlacklistService;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public Long signup(SignupRequestDto req) {
        if (userRepository.existsByEmail(req.email())) {
            throw new CustomException(EMAIL_ALREADY_EXISTS);
        }
        if (userRepository.existsByNickname(req.nickname())) {
            throw new CustomException(NICKNAME_ALREADY_EXISTS);
        }

        String encoded = passwordEncoder.encode(req.password());

        User user = new User(req.email(), encoded, req.name(), req.nickname());
        User saved = userRepository.save(user);
        return saved.getId();
    }

    public LoginResponseDto login(LoginRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.email())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        if (!passwordEncoder.matches(requestDto.password(), user.getPassword())) {
            throw new CustomException(INVALID_PASSWORD);
        }

        String userId = user.getId().toString();
        String accessToken = jwtTokenProvider.generateAccessToken(userId);
        String refreshToken = jwtTokenProvider.generateRefreshToken(userId);

        refreshTokenService.save(userId, refreshToken, jwtTokenProvider.getRefreshTokenExpirationMs());

        return new LoginResponseDto(userId, accessToken, refreshToken);
    }

    public void logout(String accessToken) {
        String userId = jwtTokenProvider.getUserIdFromAccessToken(accessToken);
        tokenBlacklistService.blacklistToken(userId, accessToken, jwtTokenProvider.getAccessTokenExpirationMs());
        refreshTokenService.delete(userId);
    }

    public LoginResponseDto refreshToken(String refreshToken) {
        if (!jwtTokenProvider.validateRefreshToken(refreshToken)) {
            throw new CustomException(INVALID_REFRESH_TOKEN);
        }

        String userId = jwtTokenProvider.getUserIdFromRefreshToken(refreshToken);
        String storedRefreshToken = refreshTokenService.get(userId);

        if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
            throw new CustomException(NOT_MATCH_REFRESH_TOKEN);
        }

        String newAccessToken = jwtTokenProvider.generateAccessToken(userId);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(userId);

        refreshTokenService.save(userId, newRefreshToken, jwtTokenProvider.getRefreshTokenExpirationMs());

        return new LoginResponseDto(userId, newAccessToken, newRefreshToken);
    }

}