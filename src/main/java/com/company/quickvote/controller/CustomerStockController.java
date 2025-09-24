package com.company.quickvote.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.quickvote.dto.request.StockRegisterRequest;
import com.company.quickvote.dto.response.StockRegisterResponse;
import com.company.quickvote.dto.response.StockResponse;
import com.company.quickvote.service.CustomerStockService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "고객 주식", description = "고객 주식 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CustomerStockController {

	private final CustomerStockService customerStockService;

	@Operation(summary = "보유 주식 등록", description = "자신이 보유한 주식을 등록합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "성공적으로 주식을 등록했습니다."),
	})
	@PostMapping("/customers/{customerId}/stocks")
	public ResponseEntity<StockRegisterResponse> registerStock(@PathVariable long customerId, @RequestBody StockRegisterRequest request){
		return ResponseEntity
			.status(201)
				.body(customerStockService.save(customerId,request));
	}

	@Operation(summary = "특정 고객의 보유 주식 조회", description = "고객의 주식을 전부 조회합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "성공적으로 주식을 조회했습니다."),
	})
	@GetMapping("/customers/{customerId}")
	public ResponseEntity<List<StockResponse>> getStock(@PathVariable long customerId){
		return ResponseEntity
			.status(200)
			.body(customerStockService.findListById(customerId));
	}

	@Operation(summary = "특정 주식 삭제", description = "등록된 주식을 삭제합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "성공적으로 주식이 삭제되었습니다."),
	})
	@DeleteMapping("/stocks/{stockId}")
	public ResponseEntity<Boolean> deleteStock(@PathVariable long stockId){
		return ResponseEntity
			.status(200)
			.body(customerStockService.deleteStock(stockId));
	}
}
