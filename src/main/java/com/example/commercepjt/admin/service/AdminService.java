package com.example.commercepjt.admin.service;

import com.example.commercepjt.admin.entity.Admin;
import com.example.commercepjt.admin.repository.AdminRepository;
import com.example.commercepjt.auth.dto.RejectAdminRequest;
import com.example.commercepjt.common.config.PasswordEncoder;
import com.example.commercepjt.common.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    // 관리자 신청 거부
    @Transactional
    public void rejectAdmin(
            Long adminId,
            RejectAdminRequest request
    ) {
        // 관리자 조회
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new NotFoundException("관리자를 찾을 수 없습니다."));

        // 엔티티 메서드를 통해 거부 처리
        admin.reject(request.getRejectReason());
    }

}
