package com.example.commercepjt.admin.service;

import com.example.commercepjt.admin.repository.AdminRepository;
import com.example.commercepjt.common.config.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;


}
