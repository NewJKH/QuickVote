package com.company.quickvote.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.quickvote.dto.request.CampaignCreateRequest;
import com.company.quickvote.dto.response.CampaignResponse;
import com.company.quickvote.service.CampaignService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "캠페인", description = "캠페인 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CampaignController {

	private final CampaignService campaignService;

	@Operation(summary = "모든 캠페인 조회", description = "모든 캠페인을 조회 합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "성공적으로 캠페인이 조회되었습니다."),
	})
	@GetMapping("/campaigns")
	public ResponseEntity<List<CampaignResponse>> findAll(){
		return ResponseEntity
			.status(200)
			.body(campaignService.findAll());
	}
	@Operation(summary = "특정 캠페인 조회", description = "특정 캠페인을 조회합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "성공적으로 캠페인이 조회되었습니다."),
	})
	@GetMapping("/campaigns/{campaignId}")
	public ResponseEntity<CampaignResponse> findCampaignById(@PathVariable Long campaignId){
		return ResponseEntity
			.status(200)
			.body(campaignService.findById(campaignId));
	}

	//TODO : 내 정보 : JWT 안에 포함되어 있으므로 일단 패스
	// @GetMapping("/campaigns/me")
	// public ResponseEntity<CampaignResponse> findCampaignById(){
	// 	return ResponseEntity
	// 		.status(200)
	// 		.body(campaignService.findById(campaignId));
	// }

	//TODO : 제안자 : 로그인 한 유저이므로 Auth 준비 후 추가
	@Operation(summary = "캠페인 생성", description = "캠페인을 생성 합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "성공적으로 캠페인이 생성 되었습니다."),
	})
	@PostMapping("/campaigns")
	public ResponseEntity<CampaignResponse> createCampaign(@RequestBody CampaignCreateRequest request){
		return ResponseEntity
			.status(201)
			.body(campaignService.save(request));

	}

}
