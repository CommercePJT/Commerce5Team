package com.example.commercepjt.admin.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RejectRequest {

    @NotBlank(message = "거절 사유는 필수 입니다.")
    private String rejectReason;
}
