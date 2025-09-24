package com.company.quickvote.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.quickvote.dto.request.StockRegisterRequest;
import com.company.quickvote.dto.response.StockRegisterResponse;
import com.company.quickvote.entity.company.Company;
import com.company.quickvote.entity.customer.Customer;
import com.company.quickvote.entity.customerstock.CustomerStock;
import com.company.quickvote.repository.CompanyJPARepository;
import com.company.quickvote.repository.CustomerJPARepository;
import com.company.quickvote.repository.StockJPARepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerStockService {

	private final StockJPARepository stockJPARepository;
	private final CompanyJPARepository companyJPARepository;
	private final CustomerJPARepository customerJPARepository;
	//TODO: 일단 번호별로 저장 -> 추 후 이미 등록되어 있는 경우 갯수만 합산예정
	@Transactional
	public StockRegisterResponse save(Long customerId, StockRegisterRequest request){
		Customer customer = customerJPARepository.findById(customerId)
			.orElseThrow(()->new IllegalArgumentException(" 존재하는 고객이 없습니다. "));

		Company company = companyJPARepository.findById(request.getCpId())
			.orElseThrow(()->new IllegalArgumentException(" 존재하는 기업이 없습니다. "));

		CustomerStock customerStock = new CustomerStock(request.getShares(), customer, company);

		stockJPARepository.save(customerStock);

		return new StockRegisterResponse(
			customerStock.getId(),
			customerStock.getCompany().getId(),
			customerStock.getShares()
		);
	}
}
