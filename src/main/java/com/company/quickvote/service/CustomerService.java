package com.company.quickvote.service;

import org.springframework.stereotype.Service;

import com.company.quickvote.dto.request.CustomerSignUpRequest;
import com.company.quickvote.dto.response.CustomerSignUpResponse;
import com.company.quickvote.entity.customer.Customer;
import com.company.quickvote.repository.CustomerJPARepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final CustomerJPARepository customerRepository;

	@Transactional
	public CustomerSignUpResponse signup(CustomerSignUpRequest request) {

		Customer customer = new Customer(request.getName(), request.getEmail(), request.getPassword());
		customerRepository.save(customer);

		return new CustomerSignUpResponse(
			customer.getId(),
			customer.getName(),
			customer.getEmail()
		);
	}

}
