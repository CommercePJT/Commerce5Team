package com.example.commercepjt.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 리스트 조회 응답에 공통으로 포함되는 페이징 정보.
 * 모든 도메인(관리자/고객/상품/주문)의 리스트 응답 DTO에서 사용합니다.
 */
@Getter
@AllArgsConstructor
public class PageInfo {

    private int currentPage;
    private int size;
    private long totalElements;
    private int totalPages;
}
