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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CampaignController {

	private final CampaignService campaignService;

	@GetMapping("/campaigns")
	public ResponseEntity<List<CampaignResponse>> findAll(){
		return ResponseEntity
			.status(200)
			.body(campaignService.findAll());
	}

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
	@PostMapping("/campaigns")
	public ResponseEntity<CampaignResponse> createCampaign(@RequestBody CampaignCreateRequest request){
		return ResponseEntity
			.status(201)
			.body(campaignService.save(request));

	}

}
