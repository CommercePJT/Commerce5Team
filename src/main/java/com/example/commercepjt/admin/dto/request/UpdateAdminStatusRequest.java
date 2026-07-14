package com.example.commercepjt.admin.dto.request;

import com.example.commercepjt.admin.entity.AdminStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateAdminStatusRequest {

    // 상태은 반드시 선택해야 함
    @NotNull(message = "관리자 상태 필수입니다.")
    private AdminStatus status;
}
