package com.example.commercepjt.auth.dto.response;

import com.example.commercepjt.admin.entity.Admin;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SignupAdminResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final String phone;
    private final String role;
    private final String status;
    private final LocalDateTime createdAt;

    public SignupAdminResponse(Admin admin) {
        this.id = admin.getId();
        this.name = admin.getName();
        this.email = admin.getEmail();
        this.phone = admin.getPhone();
        this.role = admin.getRole().name();
        this.status = admin.getStatus().name();
        this.createdAt = admin.getCreatedAt();
    }
}