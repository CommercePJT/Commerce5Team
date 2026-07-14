package com.example.commercepjt.customer.repository;

import com.example.commercepjt.customer.entity.Customer;
import com.example.commercepjt.customer.entity.CustomerStatus;
import org.springframework.data.jpa.domain.Specification;

public class CustomerSpecification {


    // 이름 또는 이메일에 keyword 포함 (요구사항: 검색 키워드는 이름, 이메일)된 고객 검색하기
    public static Specification<Customer> keyword(String keyword) {

        return (root, query, criteriaBuilder) -> {

            //  검색어가 없으면 검색 조건을 적용하지 않음
            if (keyword == null || keyword.isBlank()) {
                return null;   // 조건 무시
            }
            return criteriaBuilder.or(
                    criteriaBuilder.like(root.get("name"), "%" + keyword + "%"),
                    criteriaBuilder.like(root.get("email"), "%" + keyword + "%")
            );
        };
    }

    // 고객 상태 필터 (요구사항: 활성/비활성/정지)
    public static Specification<Customer> status(CustomerStatus status) {

        return (root, query, criteriaBuilder) -> {
            // 상태가 없으면 상태 조건을 적용하지 않는다.
            if (status == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }
}
// root.get("필드명")은 컬럼 지정, criteriaBuilder는 조건 제작 도구, null 반환은 "이 조건 빼줘"