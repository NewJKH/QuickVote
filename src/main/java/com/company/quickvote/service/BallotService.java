package com.company.quickvote.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.quickvote.dto.request.BallotCreateRequest;
import com.company.quickvote.dto.response.BallotCreateResponse;
import com.company.quickvote.dto.response.BallotResponse;
import com.company.quickvote.dto.response.BallotVoteResponse;
import com.company.quickvote.entity.ballot.Ballot;
import com.company.quickvote.entity.campaign.Campaign;
import com.company.quickvote.entity.campaign.Status;
import com.company.quickvote.global.exception.NotFoundException;
import com.company.quickvote.global.security.auth.Auth;
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
			.orElseThrow(()->new NotFoundException("캠페인"));

		Ballot ballot = new Ballot(
			ballotCreateRequest.getShares(),
			ballotCreateRequest.getVoteType(),
			ballotCreateRequest.getVoteChoice(),
			ballotCreateRequest.getDelegateId(),
			campaign
		);
		return new BallotCreateResponse(ballot.getId());
	}

	public Boolean delete(long ballotId) {
		this.ballotJPARepository.deleteById(ballotId);
		return true;
	}

	@Transactional(readOnly = true)
	public List<BallotResponse> findByMe(Auth auth) {
		return ballotJPARepository.findAll()
			.stream()
			.filter(ballot -> ballot.getCustomer().getId()==auth.id())
			.map(BallotResponse::from)
			.toList();
	}

	@Transactional(readOnly = true)
	public BallotVoteResponse isVote(Auth auth, Long campaignId) {
		Campaign campaign = campaignJPARepository.findById(campaignId)
			.orElseThrow(() -> new NotFoundException("캠페인"));

		LocalDateTime now = LocalDateTime.now();

		// 상태 체크
		if (campaign.getStatus() != Status.OPEN) {
			return BallotVoteResponse.cannotVote("캠페인 상태가 유효하지 않습니다.");
		}

		// 기간 체크
		if (now.isBefore(campaign.getStartAt())) {
			return BallotVoteResponse.cannotVote("아직 시작 전입니다.");
		}
		if (now.isAfter(campaign.getEndAt())) {
			return BallotVoteResponse.cannotVote("기간만료");
		}

		// 이미 투표했는지 체크
		boolean alreadyVoted = ballotJPARepository.existsByCustomerIdAndCampaignId(auth.id(), campaignId);
		if (alreadyVoted) {
			return BallotVoteResponse.cannotVote("이미 투표 또는 위임 완료");
		}

		return BallotVoteResponse.canVote();
	}
}
