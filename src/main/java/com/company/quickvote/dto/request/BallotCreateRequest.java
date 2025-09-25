package com.company.quickvote.dto.request;

import com.company.quickvote.entity.ballot.VoteChoice;
import com.company.quickvote.entity.ballot.VoteType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BallotCreateRequest {
	private long campaignId;
	private int shares;
	private VoteType voteType;
	private VoteChoice voteChoice;
	private long delegateId;
}
