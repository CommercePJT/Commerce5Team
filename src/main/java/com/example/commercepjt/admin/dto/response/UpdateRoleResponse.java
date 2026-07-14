package com.example.commercepjt.admin.dto.response;

import com.example.commercepjt.admin.entity.AdminRole;
import lombok.Getter;

@Getter
public class UpdateRoleResponse {

    private final AdminRole role;

    public UpdateRoleResponse(AdminRole role) {
        this.role = role;
    }
}
