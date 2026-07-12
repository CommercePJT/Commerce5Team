package com.example.commercepjt.admin.dto;

import com.example.commercepjt.admin.entity.AdminStatus;
import lombok.Getter;

@Getter
public class UpdateAdminStatusRequest {

    private AdminStatus status;
}
