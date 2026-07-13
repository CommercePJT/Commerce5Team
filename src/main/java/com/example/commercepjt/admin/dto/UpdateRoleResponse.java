package com.example.commercepjt.admin.dto;

import com.example.commercepjt.admin.entity.AdminRole;
import lombok.Getter;

@Getter
public class UpdateRoleResponse {

    private AdminRole role;

    public UpdateRoleResponse(AdminRole role) {
        this.role = role;
    }
}
