package com.example.commercepjt.admin.dto.response;

import com.example.commercepjt.admin.entity.Admin;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

//관리자 리스트,상세 조회 응답 항목
@Getter
@AllArgsConstructor
public class AdminResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final String phone;
    private final String role;
    private final String status;
    private final LocalDateTime createdAt;
    private final LocalDateTime approvedAt;

    public AdminResponse(Admin admin) {
        this.id = admin.getId();
        this.name = admin.getName();
        this.email = admin.getEmail();
        this.phone = admin.getPhone();
        this.role = admin.getRole().name();
        this.status = admin.getStatus().name();
        this.createdAt = admin.getCreatedAt();
        this.approvedAt = admin.getApprovedAt();
    }
}
