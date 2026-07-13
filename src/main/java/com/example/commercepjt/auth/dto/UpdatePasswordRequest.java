package com.example.commercepjt.auth.dto;

import lombok.Getter;

@Getter
public class UpdatePasswordRequest {

    private String password;
    private String newPassword;
}
