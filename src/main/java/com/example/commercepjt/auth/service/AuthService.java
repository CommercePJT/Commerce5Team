package com.example.commercepjt.auth.service;

import com.example.commercepjt.admin.entity.Admin;
import com.example.commercepjt.admin.entity.AdminStatus;
import com.example.commercepjt.admin.repository.AdminRepository;
import com.example.commercepjt.auth.dto.LoginAdminInfo;
import com.example.commercepjt.auth.dto.LoginAdminRequest;
import com.example.commercepjt.auth.dto.SignupAdminRequest;
import com.example.commercepjt.auth.dto.SignupAdminResponse;
import com.example.commercepjt.auth.session.SessionConst;
import com.example.commercepjt.common.config.PasswordEncoder;
import com.example.commercepjt.common.exception.DuplicateException;
import com.example.commercepjt.common.exception.ForbiddenException;
import com.example.commercepjt.common.exception.UnauthorizedException;
import jakarta.servlet.http.HttpSession;
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
    @Transactional(readOnly = true)
    public LoginAdminInfo login(LoginAdminRequest request) {

        // 이메일로 관리자 조회
        Admin admin = adminRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("이메일 또는 비밀번호가 일치하지 않습니다."));

        // 비밀번호 검증
        // 입력 비밀번호와 암호화된 비밀번호 비교
        if (!passwordEncoder.matches(request.getPassword(),admin.getPassword()))
            throw new UnauthorizedException("이메일 또는 비밀번호가 일치하지 않습니다.");

        return new LoginAdminInfo(
                admin.getId(),
                admin.getEmail(),
                admin.getRole());
    }

    // 로그아웃
    public void logout(HttpSession session) {

        // 현재 세션을 무효화하여 로그인 정보 삭제
        session.invalidate();
    }

    // 이메일 중복 검사 메서드
    private void validateDuplicateEmail(String email) {

        // 이미 존재하는 이메일이면 예외 발생
        if (adminRepository.existsByEmail(email)) {
            throw new DuplicateException("이미 사용 중인 이메일 입니다.");
        }
    }



    // 계정 상태에 따른 로그인 실패 분리
    // 로그인 가능한 관리자 상태인지 확인
    private void validateAdminStatus(Admin admin) {

        switch (admin.getStatus()) {

            // 정상 계정은 로그인 허용
            case ACTIVE -> {
                return;
            }
                // 관리자 승인 대기 상태
            case PENDING -> throw new ForbiddenException("계정 승인 대기 중입니다.");

            // 관리자 신청 거부 상태
            case REJECTED -> throw new ForbiddenException("관리자 신청이 거부되었습니다."
                            + "거부 날짜: " + admin.getRejectedAt()
                            + "거부 사유: " + admin.getRejectReason());

            // 계정 정지 상태
            case SUSPENDED -> throw new ForbiddenException("정지된 계정입니다.");

            // 계정 비활성화 상태
            case INACTIVE ->throw new ForbiddenException("비활성화된 계정입니다.");
        }
    }
}
