package com.example.commercepjt.admin.controller;

import com.example.commercepjt.admin.service.AdminService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // ⚠ /me 매핑은 /{adminId} 보다 위에 선언할 것 (경로 충돌 방지)
}