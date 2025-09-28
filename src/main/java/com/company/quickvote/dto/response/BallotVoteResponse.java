package com.company.quickvote.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BallotVoteResponse {
	private boolean canVote;
	private String reason;

	public static BallotVoteResponse canVote() {
		return new BallotVoteResponse(true, null);
	}

	public static BallotVoteResponse cannotVote(String reason) {
		return new BallotVoteResponse(false, reason);
	}
}