package com.company.quickvote.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.quickvote.dto.request.BallotCreateRequest;
import com.company.quickvote.dto.response.BallotCreateResponse;
import com.company.quickvote.service.BallotService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BallotController {

	private final BallotService ballotService;

	@PostMapping("/ballots")
	public ResponseEntity<BallotCreateResponse> save(@RequestBody BallotCreateRequest request){
		return ResponseEntity
			.status(201)
			.body(ballotService.save(request));
	}
	// TODO : 내 투표 위임 내역 조회

	@DeleteMapping("/ballots/{ballotId}")
	public Boolean delete(@PathVariable("ballotId") long ballotId){
		return ballotService.delete(ballotId);
	}

	//TODO: 투표 가능 여부 확인
	// @GetMapping("/ballots/{campaignId}/status")
	// public ResponseEntity<BallotVoteResponse> isVote(@PathVariable String campaignId){
	// 	return ResponseEntity
	// 		.status(200)
	// 		.body(ballotService.isVote(campaignId));
	// }
	//

}
