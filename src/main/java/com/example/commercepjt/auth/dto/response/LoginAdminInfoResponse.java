package com.example.commercepjt.auth.dto.response;

import com.example.commercepjt.admin.entity.AdminRole;
import lombok.Getter;

@Getter

public class LoginAdminInfoResponse {

    private final Long id;
    private final String email;
    private final AdminRole role;

    public LoginAdminInfoResponse(Long id, String email, AdminRole role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }
}
