package com.example.commercepjt.auth.dto.request;

import com.example.commercepjt.admin.entity.AdminRole;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter

public class SignupAdminRequest {

    // 이름은 null, 빈 문자열, 공백 입력할 수 없음.
    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    // 이메일은 필수. 이메일 형식이어야 함.
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    // 비밀번호도 필수. 최소 8자 이상이어야 함
    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    private String password;

    // 전화번호 필수.  010 - xxxx - xxxx 형식이어야 함.
    @NotBlank(message = "전화번호는 필수입니다.")
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$",
            message = "전화번호는 010-xxxx-xxxx 형식이어야 합니다.")
    private String phone;

    // 역할은 반드시 선택해야 함
    @NotNull(message = "관리자 역할은 필수입니다.")
    private AdminRole role;

    @Getter
    public static class LoginRequest {

        private String email;
        private String password;
    }
}
