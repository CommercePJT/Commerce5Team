package com.example.commercepjt.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/** 고객 리스트/상세 조회 응답 항목 */
@Getter
@AllArgsConstructor
public class CustomerResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String status;
    private LocalDateTime createdAt;

}
