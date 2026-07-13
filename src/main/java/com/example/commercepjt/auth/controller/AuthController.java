package com.example.commercepjt.auth.controller;

import com.example.commercepjt.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


}
