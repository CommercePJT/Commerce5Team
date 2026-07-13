package com.example.commercepjt.admin.entity;


import com.example.commercepjt.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "admins")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends BaseEntity {

    // 관리자 고유 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 관리자 이름
    @Column(nullable = false)
    private String name;

    // 로그인에 사용하는 이메일
    // unique -> 같은 이메일로 중복 가입 할 수 없음.
    @Column(nullable = false, unique = true)
    private String email;

    // BCrypt로 암호화된 비밀번호가 저장
    @Column(nullable = false)
    private String password;

    // 전화번호
    @Column(nullable = false, unique = true)
    private String phone;

    // 슈퍼 관리자, 운영 관리자, CS 관리자
    @Enumerated(EnumType.STRING)                   // Enum.Type.String-> DB에 의미가 그대로 저장됌.
    @Column(nullable = false)
    private AdminRole role;

    // 승인 대기, 활성, 비활성, 정지, 거부
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdminStatus status;

    // 관리자가 승인된 날짜
    private LocalDateTime approvedAt;

    // 관리자가 가입 신청이 거부된 날짜
    private LocalDateTime rejectedAt;

    // 관리자 가입 신청 거부 사유
    @Column
    private String rejectReason;

    // 관리자 회원가입에 사용하는 생성자
    @Builder                                       // @Builder -> 어떤 값이 어떤 자리에 들어가는지 이름 보면서 만들 수 있게 해줌
    public Admin(
            String name,
            String email,
            String password,
            String phone,
            AdminRole role
    ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;

        // 회원가입 직후 무조건 승인 대기 상태로
        this.status = AdminStatus.PENDING;
    }

}
