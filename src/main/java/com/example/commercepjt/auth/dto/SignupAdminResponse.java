package com.example.commercepjt.auth.dto;

import com.example.commercepjt.admin.entity.Admin;
import com.example.commercepjt.admin.entity.AdminRole;
import com.example.commercepjt.admin.entity.AdminStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor // 모든 필드를 받는 생성자 생성
public class SignupAdminResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final String phone;
    private final AdminRole role;
    private final AdminStatus status;
    private final LocalDateTime createdAt;


    public static SignupAdminResponse from(Admin admin) {
        return new SignupAdminResponse(
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                admin.getPhone(),
                admin.getRole(),
                admin.getStatus(),
                admin.getCreatedAt());
    }
}
