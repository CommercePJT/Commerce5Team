package com.example.commercepjt.admin.dto.response;

import com.example.commercepjt.admin.entity.Admin;
import lombok.AllArgsConstructor;
import lombok.Getter;

//내 프로필 조회,수정 응답
@Getter
@AllArgsConstructor
public class ProfileResponse {

    private final String name;
    private final String email;
    private final String phone;


    public ProfileResponse(Admin admin) {
        this.name = admin.getName();
        this.email = admin.getEmail();
        this.phone = admin.getPhone();

    }
}
