package com.example.commercepjt.admin.controller;

import com.example.commercepjt.admin.dto.request.SignupRequest;
import com.example.commercepjt.admin.dto.response.SignupResponse;
import com.example.commercepjt.admin.service.AdminService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;


    // ⚠ /me 매핑은 /{adminId} 보다 위에 선언할 것 (경로 충돌 방지)
}