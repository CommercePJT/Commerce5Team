package com.example.commercepjt.admin.dto.request;

import com.example.commercepjt.admin.entity.AdminRole;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateRoleRequest {

    @NotNull(message = "관리자 역할은 필수입니다.")
    private AdminRole role;
}