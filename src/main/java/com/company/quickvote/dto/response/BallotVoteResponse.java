package com.company.quickvote.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BallotVoteResponse {
	private boolean canVote;
	private String reason;
}
