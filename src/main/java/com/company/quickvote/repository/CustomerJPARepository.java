package com.company.quickvote.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.quickvote.entity.customer.Customer;

public interface CustomerJPARepository extends JpaRepository<Customer, Long> {
	Optional<Customer> findByEmail(String email);
}
