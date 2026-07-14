package com.example.commercepjt.admin.dto.response;

import com.example.commercepjt.admin.entity.AdminStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApproveResponse {

    private AdminStatus status;
    private LocalDateTime approvedAt;

    public ApproveResponse(
            AdminStatus status,
            LocalDateTime approvedAt
    ) {
        this.status = status;
        this.approvedAt = approvedAt;
    }
}
