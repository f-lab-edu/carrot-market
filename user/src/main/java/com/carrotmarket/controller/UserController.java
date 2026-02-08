package com.carrotmarket.controller;

import com.carrotmarket.controller.dto.request.LoginRequestDto;
import com.carrotmarket.controller.dto.request.SignupRequestDto;
import com.carrotmarket.controller.dto.response.LoginResponseDto;
import com.carrotmarket.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto requestDto) {
        LoginResponseDto response = userService.login(requestDto);
        return ResponseEntity
                .ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        String accessToken = token.replace("Bearer ", "");
        userService.logout(accessToken);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refreshToken(@RequestHeader("Authorization") String bearerToken) {
        String refreshToken = bearerToken.replace("Bearer ", "");
        LoginResponseDto response = userService.refreshToken(refreshToken);
        return ResponseEntity
                .ok(response);
    }

}