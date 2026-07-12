package com.example.commercepjt.customer.repository;

import com.example.commercepjt.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {


}
