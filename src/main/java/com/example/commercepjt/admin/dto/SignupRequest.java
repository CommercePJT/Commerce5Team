package com.example.commercepjt.admin.dto;

import com.example.commercepjt.admin.entity.AdminRole;
import lombok.Getter;

@Getter
public class SignupRequest {


    private String name;
    private String email;
    private String password;
    private String phone;
    private AdminRole role;
}
