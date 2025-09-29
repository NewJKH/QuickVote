package com.company.quickvote.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.quickvote.dto.request.BallotCreateRequest;
import com.company.quickvote.dto.response.BallotCreateResponse;
import com.company.quickvote.dto.response.BallotResponse;
import com.company.quickvote.dto.response.BallotVoteResponse;
import com.company.quickvote.global.security.auth.Auth;
import com.company.quickvote.service.BallotService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
	public ResponseEntity<BallotCreateResponse> save(@Valid @RequestBody BallotCreateRequest request, @AuthenticationPrincipal Auth auth) {
		return ResponseEntity
			.status(201)
			.body(ballotService.save(request,auth));
	}

	@Operation(summary = "내 투표 조회", description = "투표 및 위임 내역 확인합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "성공적으로 내역이 조회되었습니다."),
	})
	@GetMapping("/ballots/me")
	public ResponseEntity<List<BallotResponse>> findByMe(@AuthenticationPrincipal Auth auth){
		return ResponseEntity
			.status(200)
			.body(ballotService.findByMe(auth));
	}


	@Operation(summary = "투표 철회", description = "본인의 투표를 철회합니다.")
	@PostMapping("/ballots/{ballotId}/revoke")
	public ResponseEntity<Void> revokeBallot(@AuthenticationPrincipal Auth auth, @PathVariable Long ballotId) {
		ballotService.revoke(ballotId, auth);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "투표 가능 여부확인", description = "현재 투표또는 위임이 가능한지 여부 입니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "성공적으로 조회되었습니다."),
	})
	@GetMapping("/ballots/{campaignId}/status")
	public ResponseEntity<BallotVoteResponse> canVote(@AuthenticationPrincipal Auth auth, @PathVariable Long campaignId){
		return ResponseEntity
			.status(200)
			.body(ballotService.canVote(auth, campaignId));
	}




}
