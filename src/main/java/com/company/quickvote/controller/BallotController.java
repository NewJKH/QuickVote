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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "투표/위임", description = "투표위윔 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BallotController {

	private final BallotService ballotService;
	@Operation(summary = "의결권 등록", description = "의결권을 등록 합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "성공적으로 기업이 조회되었습니다."),
	})
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
