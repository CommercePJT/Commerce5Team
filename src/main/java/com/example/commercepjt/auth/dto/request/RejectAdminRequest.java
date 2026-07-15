package com.example.commercepjt.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

public class RejectAdminRequest {

    @NotBlank(message = "거부 사유는 필수입니다.")
    private String rejectReason;
}
