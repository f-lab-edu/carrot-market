package com.carrotmarket.service;

import com.carrotmarket.controller.dto.request.LoginRequestDto;
import com.carrotmarket.controller.dto.request.SignupRequestDto;
import com.carrotmarket.controller.dto.response.LoginResponseDto;
import com.carrotmarket.model.User;
import com.carrotmarket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
        }
        if (userRepository.existsByNickname(req.nickname())) {
            throw new IllegalArgumentException("이미 사용중인 닉네임입니다.");
        }

        String encoded = passwordEncoder.encode(req.password());

        User user = new User(req.email(), encoded, req.name(), req.nickname());
        User saved = userRepository.save(user);
        return saved.getId();
    }

    public LoginResponseDto login(LoginRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.email())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(requestDto.password(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
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
    }

    public LoginResponseDto refreshToken(String refreshToken) {
        if (!jwtTokenProvider.validateRefreshToken(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않은 refresh token입니다.");
        }

        String userId = jwtTokenProvider.getUserIdFromRefreshToken(refreshToken);
        String storedRefreshToken = refreshTokenService.get(userId);

        if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
            throw new IllegalArgumentException("refresh token이 일치하지 않습니다.");
        }

        String newAccessToken = jwtTokenProvider.generateAccessToken(userId);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(userId);

        refreshTokenService.save(userId, newRefreshToken, jwtTokenProvider.getRefreshTokenExpirationMs());

        return new LoginResponseDto(userId, newAccessToken, newRefreshToken);
    }

}