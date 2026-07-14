package com.example.commercepjt.auth.dto;

import com.example.commercepjt.admin.entity.AdminRole;
import lombok.Getter;

@Getter

public class LoginAdminInfo {

    private final Long id;
    private final String email;
    private final AdminRole role;

    public LoginAdminInfo(Long id, String email, AdminRole role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }
}
