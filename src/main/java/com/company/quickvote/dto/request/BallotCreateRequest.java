package com.company.quickvote.dto.request;

import com.company.quickvote.entity.ballot.VoteType;

import lombok.Getter;

@Getter
public class BallotCreateRequest {
	private long campaignId;
	private int shares;
	private VoteType voteType;

}
