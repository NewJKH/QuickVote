package com.company.quickvote.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.quickvote.dto.request.BallotCreateRequest;
import com.company.quickvote.dto.response.BallotCreateResponse;
import com.company.quickvote.entity.ballot.Ballot;
import com.company.quickvote.entity.campaign.Campaign;
import com.company.quickvote.repository.BallotJPARepository;
import com.company.quickvote.repository.CampaignJPARepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BallotService {

	private final BallotJPARepository ballotJPARepository;
	private final CampaignJPARepository campaignJPARepository;

	@Transactional
	public BallotCreateResponse save(BallotCreateRequest ballotCreateRequest) {

		Campaign campaign = campaignJPARepository.findById(ballotCreateRequest.getCampaignId())
			.orElseThrow(()->new IllegalArgumentException("캠페인을 찾을 수 없습니다"));

		Ballot ballot = new Ballot(
			ballotCreateRequest.getShares(),
			ballotCreateRequest.getVoteType(),
			ballotCreateRequest.getVoteChoice(),
			ballotCreateRequest.getDelegateId(),
			campaign
		);
		return new BallotCreateResponse(ballot.getId());
	}

	@Transactional(readOnly = true)
	public void findAllById(){


	}

	public Boolean delete(long ballotId) {
		this.ballotJPARepository.deleteById(ballotId);
		return true;
	}


}
