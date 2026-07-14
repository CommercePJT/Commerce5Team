package com.example.commercepjt.auth.dto.response;

import com.example.commercepjt.admin.entity.Admin;
import com.example.commercepjt.admin.entity.AdminRole;
import lombok.Getter;

@Getter

public class LoginAdminInfoResponse {

    private final Long id;
    private final String email;
    private final AdminRole role;

    public LoginAdminInfoResponse(Admin admin) {
        this.id = admin.getId();
        this.email = admin.getEmail();
        this.role = admin.getRole();
    }
}
