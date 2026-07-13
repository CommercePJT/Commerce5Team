package com.example.commercepjt.admin.dto.request;

import com.example.commercepjt.admin.entity.AdminStatus;
import lombok.Getter;

@Getter
public class UpdateAdminStatusRequest {

    private AdminStatus status;
}
