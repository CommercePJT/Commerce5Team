package com.example.commercepjt.admin.service;

import com.example.commercepjt.admin.dto.*;
import com.example.commercepjt.admin.dto.request.RejectRequest;
import com.example.commercepjt.admin.dto.request.UpdateAdminRequest;
import com.example.commercepjt.admin.dto.request.UpdateAdminStatusRequest;
import com.example.commercepjt.admin.dto.request.UpdateRoleRequest;
import com.example.commercepjt.admin.dto.response.AdminListResponse;
import com.example.commercepjt.admin.dto.response.AdminResponse;
import com.example.commercepjt.admin.dto.response.ProfileResponse;
import com.example.commercepjt.admin.entity.Admin;
import com.example.commercepjt.admin.entity.AdminRole;
import com.example.commercepjt.admin.entity.AdminStatus;
import com.example.commercepjt.admin.repository.AdminRepository;
import com.example.commercepjt.admin.repository.AdminSpecification;
import com.example.commercepjt.common.config.PasswordEncoder;
import com.example.commercepjt.common.dto.PageInfo;
import com.example.commercepjt.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    //관리자 상세 조회
    public AdminResponse getAdmin(Long id) {

        Admin admin = adminRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("관리자를 찾을 수 없습니다."));

        return new AdminResponse(admin);

    }

    @Transactional
    // 관리자 정보 수정
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

    @Transactional
    // 관리자 역할 변경
    public UpdateRoleResponse updateRole(Long id, UpdateRoleRequest request) {

        // 1. 관리자 조회
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("관리자를 찾을 수 없습니다.")
                );


        // 2. 역할 변경
        admin.changeRole(request.getRole());


        // 3. 변경된 역할 반환
        return new UpdateRoleResponse(admin.getRole());

    }

    @Transactional
    // 관리자 상태 변경
    public UpdateStatusResponse updateStatus(Long id, UpdateAdminStatusRequest request) {

        // 1. 관리자 조회
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("관리자를 찾을 수 없습니다.")
                );


        // 2. 상태 변경
        admin.changeStatus(request.getStatus());


        // 3. 변경된 상태 반환
        return new UpdateStatusResponse(admin.getStatus());
    }

    @Transactional
    // 관리자 승인
    public ApproveResponse approveAdmin(Long id) {

        // 1. 관리자 조회
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("관리자를 찾을 수 없습니다.")
                );


        // 2. 승인 상태 확인
        if (admin.getStatus() != AdminStatus.PENDING) {
            throw new IllegalArgumentException(
                    "승인 대기 상태의 관리자만 승인할 수 있습니다."
            );
        }


        // 3. 승인 처리
        admin.approve();


        // 4. 응답 반환
        return new ApproveResponse(
                admin.getStatus(),
                admin.getApprovedAt()
        );
    }

    @Transactional
    // 관리자 거부
    public RejectResponse rejectAdmin(Long id, RejectRequest request) {

        // 1. 관리자 조회
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("관리자를 찾을 수 없습니다.")
                );


        // 2. 승인 대기 상태 확인
        if (admin.getStatus() != AdminStatus.PENDING) {
            throw new IllegalArgumentException(
                    "승인 대기 상태의 관리자만 거부할 수 있습니다."
            );
        }


        // 3. 거부 사유 확인
        if (request.getRejectReason() == null
                || request.getRejectReason().isBlank()) {

            throw new IllegalArgumentException(
                    "거부 사유는 필수입니다."
            );
        }


        // 4. 거부 처리
        admin.reject(request.getRejectReason());


        // 5. 응답 반환
        return new RejectResponse(
                admin.getStatus(),
                admin.getRejectedAt(),
                admin.getRejectReason()
        );
    }

    // 관리자 삭제
    public void deleteAdmin(Long id) {

        // 1. 관리자 조회
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("관리자를 찾을 수 없습니다.")
                );


        // 2. 삭제
        adminRepository.delete(admin);

    }

    // 내 프로필 조회
    @Transactional(readOnly = true)
    public ProfileResponse getMyProfile(Long adminId) {

        // 1. 로그인한 관리자 조회
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() ->
                        new NotFoundException("관리자를 찾을 수 없습니다.")
                );

        // 2. 응답 반환
        return new ProfileResponse(
                admin.getName(),
                admin.getEmail(),
                admin.getPhone()
        );
    }

    // 관리자 리스트 조회
    public AdminListResponse getAdmins(
            String keyword,
            AdminRole role,
            AdminStatus status,
            Pageable pageable
    ) {
        // 1. 검색 조건 생성
        Specification<Admin> spec =
                Specification.where(AdminSpecification.keyword(keyword))
                        .and(AdminSpecification.role(role))
                        .and(AdminSpecification.status(status));

        // 2. 조건에 맞는 관리자 조회 (페이징 적용)
        Page<Admin> adminPage =
                adminRepository.findAll(spec, pageable);

        // 3. Entity -> DTO 변환
        List<AdminResponse> admins =
                adminPage.getContent()
                        .stream()
                        .map(AdminResponse::new)
                        .toList();

        // 4. 페이징 정보 생성
        PageInfo pageInfo =
                new PageInfo(
                        adminPage.getNumber() + 1,
                        adminPage.getSize(),
                        adminPage.getTotalElements(),
                        adminPage.getTotalPages()
                );

        // 5. 최종 응답 반환
        return new AdminListResponse(
                admins,
                pageInfo
        );
    }

    @Transactional
    public ProfileResponse updateMyProfile(
            Long adminId,
            UpdateAdminRequest request
    ) {

        // 1. 로그인한 관리자 조회
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() ->
                        new NotFoundException("관리자를 찾을 수 없습니다.")
                );

        // 2. 정보 수정
        admin.update(
                request.getName(),
                request.getEmail(),
                request.getPhone()
        );

        // 3. 응답 반환
        return new ProfileResponse(
                admin.getName(),
                admin.getEmail(),
                admin.getPhone()
        );
    }
}
