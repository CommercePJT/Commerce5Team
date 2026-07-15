package com.example.commercepjt.admin.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

// 관리자 정보 수정 , 내 프로필 수정 공용 요청
@Getter
public class UpdateAdminRequest {

    // 이름은 null, 빈 문자열, 공백 입력할 수 없음.
    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    // 이메일은 필수. 이메일 형식이어야 함.
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    // 전화번호 필수.  010 - xxxx - xxxx 형식이어야 함.
    @NotBlank(message = "전화번호는 필수입니다.")
    @Pattern(
            regexp = "^010-\\d{4}-\\d{4}$",
            message = "전화번호는 010-xxxx-xxxx 형식이어야 합니다."
    )
    private String phone;
}
