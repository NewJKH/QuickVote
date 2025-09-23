package com.company.quickvote.service;

import org.springframework.stereotype.Service;

import com.company.quickvote.dto.request.CustomerLoginRequest;
import com.company.quickvote.dto.request.CustomerSignUpRequest;
import com.company.quickvote.dto.response.CustomerLoginResponse;
import com.company.quickvote.dto.response.CustomerResponse;
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

	public CustomerLoginResponse login(CustomerLoginRequest request) {
		Customer customer = customerRepository.findByEmail(request.getEmail())
			.orElseThrow(()->new IllegalArgumentException(" 존재하는 회원이 없습니다. "));

		if ( !customer.getPwd().equals(request.getPassword())) {
			throw new IllegalArgumentException(" 비밀번호 입력을 다시해주세요. ");
		}

		return new CustomerLoginResponse(customer.getId(),customer.getName());
	}

	public CustomerResponse findById(Long customerId) {
		Customer customer = customerRepository.findById(customerId)
			.orElseThrow(()->new IllegalArgumentException(" 존재하는 고객이 없습니다."));

		return new CustomerResponse(customer.getId(),customer.getEmail(),customer.getName(),customer.getCreateAt(),customer.getUpdatedAt());
	}
}
