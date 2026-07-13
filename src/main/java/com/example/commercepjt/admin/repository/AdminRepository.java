package com.example.commercepjt.admin.repository;

import com.example.commercepjt.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    // 이메일 찾기
    Optional<Admin> findByEmail(String email);

    // 이메일 중복 검사
    boolean existsByEmail(String email);

}
