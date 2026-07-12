package com.example.commercepjt.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** 내 프로필 조회/수정 응답 */
@Getter
@AllArgsConstructor
public class ProfileResponse {

    private String name;
    private String email;
    private String phone;


}
