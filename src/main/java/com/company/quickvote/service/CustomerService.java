package com.company.quickvote.service;

import org.springframework.stereotype.Service;

import com.company.quickvote.dto.request.CustomerLoginRequest;
import com.company.quickvote.dto.request.CustomerSignUpRequest;
import com.company.quickvote.dto.response.CustomerLoginResponse;
import com.company.quickvote.dto.response.CustomerResponse;
import com.company.quickvote.dto.response.CustomerSignUpResponse;
import com.company.quickvote.entity.customer.Customer;
import com.company.quickvote.global.exception.NotFoundException;
import com.company.quickvote.global.exception.NotMatchedException;
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

	public CustomerLoginResponse login(CustomerLoginRequest request) {
		Customer customer = customerRepository.findByEmail(request.getEmail())
			.orElseThrow(()->new NotFoundException("회원"));

		if ( !customer.getPwd().equals(request.getPassword())) {
			throw new NotMatchedException("비밀번호가 일치하지 않습니다.");
		}

		return new CustomerLoginResponse(customer.getId(),customer.getName());
	}

	public CustomerResponse findById(Long customerId) {
		Customer customer = customerRepository.findById(customerId)
			.orElseThrow(()->new NotFoundException("회원"));

		return new CustomerResponse(customer.getId(),customer.getEmail(),customer.getName(),customer.getCreateAt(),customer.getUpdatedAt());
	}
}
