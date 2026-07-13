package com.example.commercepjt.admin.dto.request;

import lombok.Getter;

/** 관리자 정보 수정 / 내 프로필 수정 공용 요청 */
@Getter
public class UpdateAdminRequest {

    private String name;
    private String email;
    private String phone;
}
