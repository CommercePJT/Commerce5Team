package com.example.commercepjt.admin.controller;

import com.example.commercepjt.admin.dto.*;
import com.example.commercepjt.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

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
}
    // ⚠️ /me 매핑은 /{adminId}보다 위에 선언할 것 (경로 충돌 방지)