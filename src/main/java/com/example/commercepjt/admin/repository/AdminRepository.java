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

    // 현재 관리자를 제외하고 동일한 이메일이 존재하는지 확인
    boolean existsByEmailAndIdNot(String email, Long id);

    // 비밀번호 중복 검사
    boolean existsByPhone(String phone);

}