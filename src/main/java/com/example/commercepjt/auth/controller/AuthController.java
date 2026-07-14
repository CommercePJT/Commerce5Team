package com.example.commercepjt.auth.controller;

import com.example.commercepjt.auth.dto.request.LoginAdminRequest;
import com.example.commercepjt.auth.dto.request.SignupAdminRequest;
import com.example.commercepjt.auth.dto.request.UpdatePasswordRequest;
import com.example.commercepjt.auth.dto.response.LoginAdminInfoResponse;
import com.example.commercepjt.auth.dto.response.SignupAdminResponse;
import com.example.commercepjt.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Void> login(@Valid @RequestBody LoginAdminRequest request,
                                      HttpServletRequest servletRequest) {

        // 로그인 검증 후 관리자 정보 반환
        LoginAdminInfoResponse loginAdmin = authService.login(request);
        // 세션 생성
        HttpSession session = servletRequest.getSession();
        // 필요한 정보 세션에 저장
        session.setAttribute("adminId", loginAdmin.getId());
        session.setAttribute("adminEmail", loginAdmin.getEmail());
        session.setAttribute("adminRole", loginAdmin.getRole());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {

        // 기존 세션 조회
        HttpSession session = request.getSession(false);
        // 현재 세션 삭제
        if (session != null) session.invalidate();

        // 로그아웃 성공 시 204 No Content 반환
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PatchMapping("/me/password")
    public ResponseEntity<Void> changePassword(
            @RequestBody UpdatePasswordRequest request,
            HttpSession session
    ) {

        Long adminId = (Long) session.getAttribute("adminId");

        authService.changePassword(adminId, request);

        return ResponseEntity.noContent().build();
    }

}
