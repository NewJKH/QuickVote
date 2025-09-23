package com.company.quickvote.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.quickvote.entity.customer.Customer;

public interface CustomerJPARepository extends JpaRepository<Customer, Long> {
}
