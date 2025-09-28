package com.company.quickvote.dto.response;

import com.company.quickvote.entity.ballot.Ballot;
import com.company.quickvote.entity.ballot.VoteChoice;
import com.company.quickvote.entity.ballot.VoteType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BallotResponse {
	private long ballotId;
	private long campaignId;
	private int shares;

	private VoteType voteType;
	private VoteChoice voteChoice;
	private long delegateTo;

	private boolean revoked;

	public static BallotResponse from(Ballot ballot) {
		return new BallotResponse(
			ballot.getId(),
			ballot.getCampaign().getId(),
			ballot.getShares(),
			ballot.getVoteType(),
			ballot.getVoteChoice(),
			ballot.getDelegateToId(),
			ballot.isRevoked()
		);
	}
}
