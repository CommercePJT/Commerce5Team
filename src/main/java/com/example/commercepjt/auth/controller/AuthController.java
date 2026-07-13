package com.example.commercepjt.auth.controller;

import com.example.commercepjt.auth.dto.LoginAdminRequest;
import com.example.commercepjt.auth.dto.SignupAdminRequest;
import com.example.commercepjt.auth.dto.SignupAdminResponse;
import com.example.commercepjt.auth.service.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.commercepjt.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admins")

public class AuthController {

    private final AuthService authService;

    // 관리자 회원가입 (가입 신청 -> 승인 대기 상태로 생성됌)
    @PostMapping("/signup")
    public ResponseEntity<SignupAdminResponse> signup(@Valid @RequestBody SignupAdminRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(request));  // 201 C
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @Valid @RequestBody LoginAdminRequest request,
            HttpSession session
    ) {
        // 로그인 검증 및 세션 생성
        authService.login(request, session);

        // 로그인 성공 시 200 OK 반환
        return ResponseEntity.ok().build();
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            HttpSession session
    ) {
        // 현재 세션 삭제
        authService.logout(session);

        // 로그아웃 성공 시 204 No Content 반환
        return ResponseEntity.noContent().build();
    }
}
