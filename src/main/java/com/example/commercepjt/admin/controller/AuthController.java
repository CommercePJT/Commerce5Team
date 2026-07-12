package com.example.commercepjt.admin.controller;

import com.example.commercepjt.admin.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


}
