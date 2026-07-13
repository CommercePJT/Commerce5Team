package com.example.commercepjt.admin.repository;

import com.example.commercepjt.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    // 이메일 중복 확인 및 로그인 시 관리자 조회
    Optional<Admin> findByEmail(String email);

}
