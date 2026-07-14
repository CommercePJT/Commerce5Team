package com.example.commercepjt.customer.repository;

import com.example.commercepjt.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    boolean existsByEmailAndCustomerIdNot(           //  SELECT COUNT(*) > 0 FROM customers WHERE email = ? AND customer_id != ?
            String email,
            Long customerId
    );


}
