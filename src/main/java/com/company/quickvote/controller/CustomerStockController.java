package com.company.quickvote.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.quickvote.dto.request.StockRegisterRequest;
import com.company.quickvote.dto.response.StockRegisterResponse;
import com.company.quickvote.service.CustomerStockService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CustomerStockController {

	private final CustomerStockService customerStockService;

	@PostMapping("/customers/{customerId}/stocks")
	public ResponseEntity<StockRegisterResponse> registerStock(@PathVariable long customerId, @RequestBody StockRegisterRequest request){
		return ResponseEntity
			.status(201)
				.body(customerStockService.save(customerId,request));
	}


}
