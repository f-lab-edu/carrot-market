package com.carrotmarket.service;

import com.carrotmarket.controller.dto.SignupRequestDto;
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

}