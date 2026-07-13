package com.example.commercepjt.admin.repository;

import com.example.commercepjt.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long>, JpaSpecificationExecutor<Admin> {

    // 이메일 중복 확인 및 로그인 시 관리자 조회
    Optional<Admin> findByEmail(String email);

    // 이메일 중복 검사
    boolean existsByEmail(String email);

}