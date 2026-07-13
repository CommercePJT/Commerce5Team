package com.example.commercepjt.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/** 관리자 리스트/상세 조회 응답 항목 */
@Getter
@AllArgsConstructor
public class AdminResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String role;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime approvedAt;

}
