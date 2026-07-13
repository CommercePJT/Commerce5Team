package com.example.commercepjt.admin.entity;

import com.example.commercepjt.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "admins")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdminRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdminStatus status;

    // 승인된 시간
    private LocalDateTime approvedAt;

    // 거부된 시간
    private LocalDateTime rejectedAt;

    // 거부 사유
    private String rejectReason;

    // 회원가입 시 관리자 생성
    public Admin(
            String name,
            String email,
            String password,
            String phone,
            AdminRole role,
            AdminStatus status
    ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.status = status;
    }

    // 관리자 정보 수정
    public void update(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // 관리자 역할 변경
    public void changeRole(AdminRole role) {
        this.role = role;
    }

    // 관리자 상태 변경
    public void changeStatus(AdminStatus status) {
        this.status = status;
    }

    // 관리자 승인
    public void approve() {
        this.status = AdminStatus.ACTIVE;
        this.approvedAt = LocalDateTime.now();
    }

    // 관리자 가입 거부
    public void reject(String rejectReason) {
        this.status = AdminStatus.REJECTED;
        this.rejectedAt = LocalDateTime.now();
        this.rejectReason = rejectReason;
    }

    // 비밀번호 변경
    public void changePassword(String password) {
        this.password = password;
    }

}

