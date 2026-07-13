package com.example.commercepjt.admin.service;

import com.example.commercepjt.admin.dto.AdminResponse;
import com.example.commercepjt.admin.dto.UpdateAdminRequest;
import com.example.commercepjt.admin.entity.Admin;
import com.example.commercepjt.admin.repository.AdminRepository;
import com.example.commercepjt.common.config.PasswordEncoder;
import com.example.commercepjt.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminResponse getAdmin(Long id) {

        Admin admin = adminRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("관리자를 찾을 수 없습니다."));

        return new AdminResponse(admin);

    }

    public AdminResponse updateAdmin(Long id, UpdateAdminRequest request) {

        // 1. 수정할 관리자 조회
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("관리자를 찾을 수 없습니다.")
                );


        // 2. Entity 수정 메서드 호출
        admin.update(
                request.getName(),
                request.getEmail(),
                request.getPhone()
        );


        // 3. DTO 변환 후 반환
        return new AdminResponse(admin);
    }

}

