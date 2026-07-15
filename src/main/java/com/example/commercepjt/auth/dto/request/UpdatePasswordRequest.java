package com.example.commercepjt.auth.dto.request;

import lombok.Getter;

@Getter
public class UpdatePasswordRequest {

    private String password;
    private String newPassword;
}
