package com.example.commercepjt.admin.dto.response;

import com.example.commercepjt.admin.entity.AdminStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RejectResponse {

    private final AdminStatus status;
    private final LocalDateTime rejectedAt;
    private final String rejectReason;


    public RejectResponse(
            AdminStatus status,
            LocalDateTime rejectedAt,
            String rejectReason
    ) {
        this.status = status;
        this.rejectedAt = rejectedAt;
        this.rejectReason = rejectReason;
    }
}