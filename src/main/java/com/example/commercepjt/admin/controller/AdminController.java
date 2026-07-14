package com.example.commercepjt.admin.controller;

import com.example.commercepjt.admin.dto.*;
import com.example.commercepjt.admin.dto.request.RejectRequest;
import com.example.commercepjt.admin.dto.request.UpdateAdminRequest;
import com.example.commercepjt.admin.dto.request.UpdateAdminStatusRequest;
import com.example.commercepjt.admin.dto.request.UpdateRoleRequest;
import com.example.commercepjt.admin.dto.response.AdminListResponse;
import com.example.commercepjt.admin.dto.response.AdminResponse;
import com.example.commercepjt.admin.dto.response.ProfileResponse;
import com.example.commercepjt.admin.entity.AdminRole;
import com.example.commercepjt.admin.entity.AdminStatus;
import com.example.commercepjt.admin.service.AdminService;
import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<ProfileResponse> getMyProfile(
            HttpSession session
    ) {

        Long adminId = (Long) session.getAttribute("adminId");

        return ResponseEntity.ok(
                adminService.getMyProfile(adminId)
        );
    }

    // 관리자 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<AdminResponse> getAdmin(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                adminService.getAdmin(id)
        );

    }

    // 관리자 정보 수정
    @PatchMapping("/{id}")
    public ResponseEntity<AdminResponse> updateAdmin(
            @PathVariable Long id,
            @RequestBody UpdateAdminRequest request
    ) {

        return ResponseEntity.ok(
                adminService.updateAdmin(id, request)
        );
    }

    // 관리자 역할 변경
    @PatchMapping("/{id}/role")
    public ResponseEntity<UpdateRoleResponse> updateRole(
            @PathVariable Long id,
            @RequestBody UpdateRoleRequest request
    ) {

        return ResponseEntity.ok(
                adminService.updateRole(id, request)
        );

    }

    // 관리자 상태 변경
    @PatchMapping("/{id}/status")
    public ResponseEntity<UpdateStatusResponse> updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateAdminStatusRequest request
    ) {

        return ResponseEntity.ok(
                adminService.updateStatus(id, request)
        );
    }

    // 관리자 승인
    @PatchMapping("/{id}/approve")
    public ResponseEntity<ApproveResponse> approveAdmin(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                adminService.approveAdmin(id)
        );
    }

    // 관리자 거부
    @PatchMapping("/{id}/reject")
    public ResponseEntity<RejectResponse> rejectAdmin(
            @PathVariable Long id,
            @RequestBody RejectRequest request
    ) {

        return ResponseEntity.ok(
                adminService.rejectAdmin(id, request)
        );
    }

    // 관리자 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(
            @PathVariable Long id
    ) {

        adminService.deleteAdmin(id);

        return ResponseEntity.noContent().build();
    }

    // 관리자 리스트 조회
    @GetMapping
    public ResponseEntity<AdminListResponse> getAdmins(

            @RequestParam(required = false) String keyword,

            @RequestParam(required = false) AdminRole role,

            @RequestParam(required = false) AdminStatus status,

            Pageable pageable

    ) {

        return ResponseEntity.ok(
                adminService.getAdmins(
                        keyword,
                        role,
                        status,
                        pageable
                )
        );

    }
    // 내 프로필 수정
    @PatchMapping("/me")
    public ResponseEntity<ProfileResponse> updateMyProfile(
            @RequestBody UpdateAdminRequest request,
            HttpSession session
    ) {

        Long adminId = (Long) session.getAttribute("adminId");

        return ResponseEntity.ok(
                adminService.updateMyProfile(adminId, request)
        );
    }
}