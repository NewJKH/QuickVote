package com.company.quickvote.dto.request;

import com.company.quickvote.entity.ballot.VoteChoice;
import com.company.quickvote.entity.ballot.VoteType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BallotCreateRequest {

	@Schema(description = "캠페인 번호", example = "1")
	@Positive(message = "캠페인 번호는 0보다 커야 합니다.")
	private long campaignId;

	@Schema(description = "주식 수", example = "3")
	@Positive(message = "주식 수는 0보다 커야 합니다.")
	private int shares;

	@Schema(description = "타입", example = "DIRECT")
	@NotNull(message = "투표 방식은 반드시 지정해야 합니다.")
	private VoteType voteType;

	@Schema(description = "투표 타입", example = "YES")
	@NotNull(message = "투표 선택은 반드시 지정해야 합니다.")
	private VoteChoice voteChoice;

	@Schema(description = "위임한 아이디", example = "999")
	@PositiveOrZero(message = "위임 아이디는 0 이상이어야 합니다.")
	private Long delegateId;
}