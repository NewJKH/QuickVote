package com.company.quickvote.dto.request;

import com.company.quickvote.entity.ballot.VoteChoice;
import com.company.quickvote.entity.ballot.VoteType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BallotCreateRequest {
	@Schema(description = "캠페인 번호", example = "1")
	private long campaignId;

	@Schema(description = "주식 수", example = "3")
	private int shares;

	@Schema(description = "타입", example = "DIRECT")
	private VoteType voteType;

	@Schema(description = "투표 타입", example = "YES")
	private VoteChoice voteChoice;

	@Schema(description = "위임한 아이디", example = "999")
	private long delegateId;
}
