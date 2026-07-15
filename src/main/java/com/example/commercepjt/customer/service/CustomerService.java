package com.example.commercepjt.customer.service;

import com.example.commercepjt.common.dto.PageInfo;
import com.example.commercepjt.common.exception.DuplicateException;
import com.example.commercepjt.common.exception.NotFoundException;
import com.example.commercepjt.customer.dto.request.UpdateCustomerRequest;
import com.example.commercepjt.customer.dto.request.UpdateCustomerStatusRequest;
import com.example.commercepjt.customer.dto.response.CustomerListResponse;
import com.example.commercepjt.customer.dto.response.CustomerResponse;
import com.example.commercepjt.customer.entity.Customer;
import com.example.commercepjt.customer.entity.CustomerStatus;
import com.example.commercepjt.customer.repository.CustomerRepository;
import com.example.commercepjt.customer.repository.CustomerSpecification;
import com.example.commercepjt.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    // 고객 리스트 조회
    @Transactional(readOnly = true)
    public CustomerListResponse findAll(
            String keyword,
            CustomerStatus status,
            Pageable pageable

    ) {
        // 1. 이름, 이메일 검색 조건과 상태 필터를 조합한다.
        Specification<Customer> specification =
                Specification.where(CustomerSpecification.keyword(keyword))
                        .and(CustomerSpecification.status(status));
        //2. 조건에 맞는 고객 조회 (페이징 적용)
        Page<Customer> customerPage = customerRepository.findAll(specification, pageable);

        // 조건에 맞는 고객이 없으면 404
        if (customerPage.isEmpty()) {
            throw new NotFoundException("조건에 맞는 고객이 존재하지 않습니다.");
        }

        //3. Entity -> DTO 변환
        List<CustomerResponse> customers = customerPage.getContent().stream()
                .map(CustomerResponse::new)
                .toList();

        // 페이징 정보를 응답 DTO에 담는다.
        PageInfo pageInfo = new PageInfo(customerPage);

        //최종응답
        return new CustomerListResponse(customers, pageInfo);
    }


    // 고객 상세 조회
    @Transactional(readOnly = true)
    public CustomerResponse findOne(Long customerId) {
        Customer customer = customerOrElseThrow(customerId);

        return new CustomerResponse(customer);
    }


    // 고객 정보 수정
    @Transactional
    public CustomerResponse update(Long customerId, UpdateCustomerRequest request) {
        Customer customer = customerOrElseThrow(customerId);

        // 중복 확인을 위한 변수 생성
        boolean duplicatedEmail = customerRepository.existsByEmailAndCustomerIdNot(
                        request.getEmail(),
                        customerId
        );

        // 변경: 중복 이메일이면 409 Conflict 예외 발생
        if (duplicatedEmail) {
            throw new DuplicateException("이미 사용 중인 이메일입니다.");
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

        // 주문 이력이 있는 고객은 삭제 불가 (FK 제약 위반 방지)
        if (orderRepository.existsByCustomerCustomerId(customerId)) {
            throw new DuplicateException("주문 이력이 있는 고객은 삭제할 수 없습니다.");
        }

        customerRepository.delete(customer);
    }


    // 고객이 있으면 Customer 반환, 없으면 404 예외
    private Customer customerOrElseThrow(Long customerId) {

        return customerRepository.findById(customerId).orElseThrow(
                        () -> new NotFoundException("고객이 존재하지 않습니다.")
        );
    }
}
