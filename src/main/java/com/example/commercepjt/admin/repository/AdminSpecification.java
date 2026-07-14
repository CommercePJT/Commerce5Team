package com.example.commercepjt.admin.repository;

import com.example.commercepjt.admin.entity.Admin;
import com.example.commercepjt.admin.entity.AdminRole;
import com.example.commercepjt.admin.entity.AdminStatus;
import org.springframework.data.jpa.domain.Specification;

public class AdminSpecification {

    // 이름 또는 이메일 검색
    public static Specification<Admin> keyword(String keyword) {

        return (root, query, criteriaBuilder) -> {

            if (keyword == null || keyword.isBlank()) {
                return null;
            }

            return criteriaBuilder.or(
                    criteriaBuilder.like(
                            root.get("name"),
                            "%" + keyword + "%"
                    ),
                    criteriaBuilder.like(
                            root.get("email"),
                            "%" + keyword + "%"
                    )

            );
        };

    }

    // 역할 필터
    public static Specification<Admin> role(AdminRole role) {

        return (root, query, criteriaBuilder) -> {

            if (role == null) {
                return null;
            }

            return criteriaBuilder.equal(
                    root.get("role"),
                    role
            );
        };
    }

    // 상태 필터
    public static Specification<Admin> status(AdminStatus status) {

        return (root, query, criteriaBuilder) -> {

            if (status == null) {
                return null;
            }

            return criteriaBuilder.equal(
                    root.get("status"),
                    status
            );
        };
    }
}
