package com.company.quickvote.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.quickvote.dto.request.StockRegisterRequest;
import com.company.quickvote.dto.response.StockRegisterResponse;
import com.company.quickvote.dto.response.StockResponse;
import com.company.quickvote.entity.company.Company;
import com.company.quickvote.entity.customer.Customer;
import com.company.quickvote.entity.customerstock.CustomerStock;
import com.company.quickvote.global.exception.NotFoundException;
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
			.orElseThrow(()->new NotFoundException("고객"));

		Company company = companyJPARepository.findById(request.getCpId())
			.orElseThrow(()->new NotFoundException("기업"));

		CustomerStock customerStock = new CustomerStock(request.getShares(), customer, company);

		stockJPARepository.save(customerStock);

		return new StockRegisterResponse(
			customerStock.getId(),
			customerStock.getCompany().getId(),
			customerStock.getShares()
		);
	}

	@Transactional(readOnly = true)
	public List<StockResponse> findListById(long customerId) {
		return stockJPARepository.findAllByCustomer_Id(customerId).stream()
			.map(stock -> new StockResponse(
				stock.getId(),
				stock.getCompany().getId(),
				stock.getCompany().getName(),
				stock.getShares()
			))
			.toList();
	}

	@Transactional
	public Boolean deleteStock(Long stockId) {
		stockJPARepository.deleteById(stockId);
		return true;
	}
}
