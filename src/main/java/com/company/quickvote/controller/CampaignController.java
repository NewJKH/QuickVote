package com.company.quickvote.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
