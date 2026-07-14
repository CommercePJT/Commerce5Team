package com.example.commercepjt.customer.service;

import com.example.commercepjt.common.dto.PageInfo;
import com.example.commercepjt.common.exception.DuplicateException;
import com.example.commercepjt.common.exception.NotFoundException;
import com.example.commercepjt.customer.dto.response.CustomerListResponse;
import com.example.commercepjt.customer.dto.response.CustomerResponse;
import com.example.commercepjt.customer.dto.request.UpdateCustomerRequest;
import com.example.commercepjt.customer.dto.request.UpdateCustomerStatusRequest;
import com.example.commercepjt.customer.entity.Customer;
import com.example.commercepjt.customer.entity.CustomerStatus;
import com.example.commercepjt.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.commercepjt.customer.repository.CustomerSpecification;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    // 고객 상세 조회
    @Transactional(readOnly = true)
    public CustomerResponse findOne(Long customerId) {
        Customer customer = customerOrElseThrow(customerId);
        return new CustomerResponse(customer);
    }

    // 고객 리스트 조회
    @Transactional(readOnly = true)
    public CustomerListResponse findAll(
            String keyword,
            CustomerStatus status,
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {

        // 페이지 번호는 1 이상이어야 한다.
        if (page < 1) {
            throw new IllegalArgumentException("페이지 번호는 1 이상이어야 합니다.");
        }

        // 페이지당 조회 개수는 1 이상이어야 한다.
        if (size < 1) {
            throw new IllegalArgumentException("페이지당 개수는 1 이상이어야 합니다.");
        }

        List<String> allowedSortFields =
                List.of("name", "email", "createdAt");

        if (!allowedSortFields.contains(sortBy)) {
            throw new IllegalArgumentException("정렬 기준은 name, email, createdAt 중 하나여야 합니다.");          // sortBy 검증 추가
        }

        Sort sort;

        // 사용자가 요청한 방향에 따라 정렬 조건을 만든다.
        if (sortDir.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();

        } else if (sortDir.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortBy).descending();

        } else {
            throw new IllegalArgumentException("정렬 방향은 asc 또는 desc만 가능합니다.");
        }

        // 클라이언트의 1페이지를 Spring의 0페이지로 변환한다.
        Pageable pageable = PageRequest.of(
                page - 1,
                size,
                sort
        );

        // 이름·이메일 검색 조건과 상태 필터를 조합한다.
        Specification<Customer> specification =
                Specification
                        .where(
                                CustomerSpecification.keyword(keyword)
                        )
                        .and(
                                CustomerSpecification.status(status)
                        );

        // 검색 조건, 상태 필터, 페이징, 정렬을 모두 적용하여 조회한다.
        Page<Customer> customerPage =
                customerRepository.findAll(
                        specification,
                        pageable
                );

        List<CustomerResponse> responseList = new ArrayList<>();

        // Customer Entity 목록을 응답 DTO 목록으로 변환한다.
        for (Customer customer : customerPage.getContent()) {

            CustomerResponse customerResponse = new CustomerResponse(customer);

            responseList.add(customerResponse);
        }

        // 페이징 정보를 응답 DTO에 담는다.
        PageInfo pageInfo = new PageInfo(
                customerPage.getNumber() + 1,
                customerPage.getSize(),
                customerPage.getTotalElements(),
                customerPage.getTotalPages()
        );

        return new CustomerListResponse(
                responseList,
                pageInfo
        );
    }

    // 고객 정보 수정
    @Transactional
    public CustomerResponse update(Long customerId, UpdateCustomerRequest request) {
        Customer customer = customerOrElseThrow(customerId);

        boolean duplicatedEmail =
                customerRepository.existsByEmailAndCustomerIdNot(
                        request.getEmail(),
                        customerId
                );

        // 변경: 중복 이메일이면 409 Conflict 예외 발생
        if (duplicatedEmail) {
            throw new DuplicateException(
                    "이미 사용 중인 이메일입니다."
            );
        }

        customer.update(request.getName(),
                request.getEmail(),
                request.getPhone());
        return new CustomerResponse(customer);
    }

    // 고객 상태 변경
    @Transactional
    public CustomerResponse updateStatus(
            Long customerId,
            UpdateCustomerStatusRequest request) {
        Customer customer = customerOrElseThrow(customerId);
        customer.updateStatus(request.getStatus());
        return new CustomerResponse(customer);
    }

    // 고객 삭제
    @Transactional
    public void delete(Long customerId) {
        Customer customer = customerOrElseThrow(customerId);
        customerRepository.delete(customer);
    }

    // 고객이 있으면 Customer 반환, 없으면 404 예외
    private Customer customerOrElseThrow(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(
                        () -> new NotFoundException("고객이 존재하지 않습니다.")
                );
    }
}
