package com.company.quickvote.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.quickvote.dto.request.CustomerSignUpRequest;
import com.company.quickvote.dto.response.CustomerSignUpResponse;
import com.company.quickvote.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "고객/유저", description = "유저 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CustomerController {

	private final CustomerService customerService;

	@Operation(summary = "회원가입", description = "새로운 계정을 등록합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "성공적으로 계정이 생성되었습니다."),
	})
	@PostMapping("/users/signup")
	public ResponseEntity<CustomerSignUpResponse> sign(@RequestBody CustomerSignUpRequest request){
		return ResponseEntity
			.status(200)
			.body(customerService.signup(request));
	}

}
