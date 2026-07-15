package com.example.commercepjt.admin.controller;

import com.example.commercepjt.admin.dto.request.RejectRequest;
import com.example.commercepjt.admin.dto.request.UpdateAdminRequest;
import com.example.commercepjt.admin.dto.request.UpdateAdminStatusRequest;
import com.example.commercepjt.admin.dto.request.UpdateRoleRequest;
import com.example.commercepjt.admin.dto.response.*;
import com.example.commercepjt.admin.entity.AdminRole;
import com.example.commercepjt.admin.entity.AdminStatus;
import com.example.commercepjt.admin.service.AdminService;
import com.example.commercepjt.common.config.LoginAdmin;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor

public class AdminController {

    private final AdminService adminService;

    // 내 프로필 조회
    @GetMapping("/me")
    public ResponseEntity<ProfileResponse> findMyProfile(
            @LoginAdmin Long adminId
    ) {
        return ResponseEntity.ok(adminService.findMyProfile(adminId));
    }

    // 관리자 리스트 조회
    @GetMapping
    public ResponseEntity<AdminListResponse> findAll(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) AdminRole role,
            @RequestParam(required = false) AdminStatus status, Pageable pageable
    ) {
        return ResponseEntity.ok(adminService.findAll(keyword, role, status, pageable));
    }

    // 관리자 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<AdminResponse> findOne(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(adminService.findOne(id));
    }

    // 내 프로필 수정
    @PatchMapping("/me")
    public ResponseEntity<ProfileResponse> updateMyProfile(
            @LoginAdmin Long adminId, 
            @Valid @RequestBody UpdateAdminRequest request
    ) {
        return ResponseEntity.ok(adminService.updateMyProfile(adminId, request));
    }

    // 관리자 정보 수정
    @PatchMapping("/{id}")
    public ResponseEntity<AdminResponse> update(
            @PathVariable Long id, 
            @Valid @RequestBody UpdateAdminRequest request
    ) {
        return ResponseEntity.ok(adminService.update(id, request));
    }

    // 관리자 역할 변경
    @PatchMapping("/{id}/role")
    public ResponseEntity<UpdateRoleResponse> updateRole(
            @PathVariable Long id, 
            @Valid @RequestBody UpdateRoleRequest request
    ) {
        return ResponseEntity.ok(adminService.updateRole(id, request));
    }

    // 관리자 상태 변경
    @PatchMapping("/{id}/status")
    public ResponseEntity<UpdateStatusResponse> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateAdminStatusRequest request
    ) {
        return ResponseEntity.ok(adminService.updateStatus(id, request));
    }

    // 관리자 승인
    @PatchMapping("/{id}/approve")
    public ResponseEntity<ApproveResponse> approve(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(adminService.approve(id));
    }

    // 관리자 거부
    @PatchMapping("/{id}/reject")
    public ResponseEntity<RejectResponse> reject(
            @PathVariable Long id,
            @Valid @RequestBody RejectRequest request
    ) {
        return ResponseEntity.ok(adminService.reject(id, request));
    }

    // 관리자 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        adminService.delete(id);

        return ResponseEntity.noContent().build();
    }
}