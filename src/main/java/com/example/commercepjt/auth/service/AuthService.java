package com.example.commercepjt.auth.service;

import com.example.commercepjt.admin.entity.Admin;
import com.example.commercepjt.admin.repository.AdminRepository;
import com.example.commercepjt.auth.dto.LoginAdminRequest;
import com.example.commercepjt.auth.dto.SignupAdminRequest;
import com.example.commercepjt.auth.dto.SignupAdminResponse;
import com.example.commercepjt.auth.exception.CustomException;
import com.example.commercepjt.auth.exception.ErrorCode;
import com.example.commercepjt.common.config.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor

public class AuthService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 = admin 생성
    @Transactional
    public SignupAdminResponse signup(SignupAdminRequest request) {

        // 이메일 중복확인
        validateDuplicateEmail(request.getEmail());
        /* if (adminRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("다른 관리자가 사용 중인 이메일 입니다.");
        } */

        // 사용자가 입력한 원본 비밀번호를 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 관리자(Admin) 객체 생성 (상태(status)는 생성자에서 자동으로 PENDING)
        Admin admin = Admin.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encodedPassword)
                .phone(request.getPhone())
                .role(request.getRole())
                .build();

        // 생성한 관리자 정보를 DB에 저장
        Admin savedAdmin = adminRepository.save(admin);

        // 저장된 Admin Entity를 Response DTO로 변환하여 반환
        return SignupAdminResponse.from(savedAdmin);
    }

    // 로그인
    @Transactional
    public Long login(LoginAdminRequest request) {

        // admin 조회
        Admin admin = adminRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않은 관리자입니다."));

        // 비밀번호 확인
        if (!admin.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치 하지 않습니다.");
        }

        // 로그인 성공 반환
        return admin.getId();

    }

    // 이메일 중복 검사
    private void validateDuplicateEmail(String email) {

        // 이미 존재하는 이메일이면 예외 발생
        if (adminRepository.existsByEmail(email)) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }
    }
}
