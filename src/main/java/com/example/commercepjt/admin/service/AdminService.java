package com.example.commercepjt.admin.service;

import com.example.commercepjt.admin.dto.request.SignupRequest;
import com.example.commercepjt.admin.dto.response.SignupResponse;
import com.example.commercepjt.admin.entity.Admin;
import com.example.commercepjt.admin.repository.AdminRepository;
import com.example.commercepjt.common.config.PasswordEncoder;
import com.example.commercepjt.common.exception.DuplicateException;   // ← BusinessException/ErrorCode 대신

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    // 관리자 회원가입
    @Transactional
    public SignupResponse signup(SignupRequest request) {

        // 1. 이미 가입된 이메일인지 확인
        validateDuplicateEmail(request.getEmail());

        // 2. 원본 비밀번호를 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 3. 관리자(Admin) 객체 생성 (status는 빌더에서 PENDING)
        Admin admin = Admin.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encodedPassword)
                .phone(request.getPhone())
                .role(request.getRole())
                .build();

        // 4. DB에 저장
        Admin savedAdmin = adminRepository.save(admin);

        // 5. Entity → Response DTO 변환하여 반환
        return SignupResponse.from(savedAdmin);
    }

    // 이메일 중복 검사
    private void validateDuplicateEmail(String email) {
        if (adminRepository.existsByEmail(email)) {
            throw new DuplicateException("이미 사용 중인 이메일입니다.");   // ← 메시지 방식
        }
    }
}