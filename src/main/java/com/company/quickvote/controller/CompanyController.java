package com.company.quickvote.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.quickvote.dto.response.CompanyResponse;
import com.company.quickvote.service.CompanyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "기업/회사", description = "기업 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CompanyController {

	private final CompanyService companyService;

	@Operation(summary = "모든 기업 조회", description = "모든 기업을 조회합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "성공적으로 기업이 조회되었습니다."),
	})
	@GetMapping("/companies")
	public ResponseEntity<List<CompanyResponse>> find(){
		return ResponseEntity
			.status(200)
			.body(companyService.findAll());
	}

	@Operation(summary = "단일 기업 조회", description = "단일 기업을 조회합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "성공적으로 기업이 조회되었습니다."),
	})
	@GetMapping("/companies/{companyId}")
	public ResponseEntity<CompanyResponse> find(@PathVariable Long companyId){
		return ResponseEntity.status(200)
			.body(companyService.findById(companyId));
	}
}
