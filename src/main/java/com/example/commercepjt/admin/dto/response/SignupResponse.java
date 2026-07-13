package com.example.commercepjt.admin.dto.response;

import com.example.commercepjt.admin.entity.Admin;
import com.example.commercepjt.admin.entity.AdminRole;
import com.example.commercepjt.admin.entity.AdminStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SignupResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final String phone;
    private final AdminRole role;
    private final AdminStatus status;
    private final LocalDateTime createdAt;

    public static SignupResponse from(Admin admin) {
        return new SignupResponse(
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                admin.getPhone(),
                admin.getRole(),
                admin.getStatus(),
                admin.getCreatedAt()
        );
    }

}
