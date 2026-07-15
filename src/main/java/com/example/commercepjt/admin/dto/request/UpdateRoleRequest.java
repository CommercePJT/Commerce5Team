package com.example.commercepjt.admin.dto.request;

import com.example.commercepjt.admin.entity.AdminRole;
import lombok.Getter;

import jakarta.validation.constraints.NotNull;

@Getter
public class UpdateRoleRequest {

    @NotNull(message = "관리자 역할은 필수입니다.")
    private AdminRole role;
}
