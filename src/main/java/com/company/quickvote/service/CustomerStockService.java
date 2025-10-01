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
	/*
	현재 방식 : 1번 투표하면 그 뒤로는 투표가 불가능하다.

	전자증권법 제146조 : 발행인은 기준일을 설정하고, 전자등록기관(예탁결제원)에서 소유자 명세를 교부받아야 한다.
	상법 제354조 : 주주명부 폐쇄/기준일 제도 → 특정 기준일로 권리 확정.
	따라서, 투표권은 기준일 명부의 snapshot 할 것.
	 */
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
