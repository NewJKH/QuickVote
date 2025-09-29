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
import com.company.quickvote.entity.customerstock.CustomerStock;
import com.company.quickvote.global.exception.BusinessException;
import com.company.quickvote.global.exception.NotFoundException;
import com.company.quickvote.global.security.auth.Auth;
import com.company.quickvote.repository.BallotJPARepository;
import com.company.quickvote.repository.CampaignJPARepository;
import com.company.quickvote.repository.StockJPARepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BallotService {

	private final BallotJPARepository ballotJPARepository;
	private final CampaignJPARepository campaignJPARepository;
	private final StockJPARepository customerStockJPARepository;

	@Transactional
	public BallotCreateResponse save(BallotCreateRequest request, Auth auth) {

		Campaign campaign = campaignJPARepository.findById(request.getCampaignId())
			.orElseThrow(() -> new NotFoundException("캠페인"));

		Long companyId = campaign.getCompany().getId();

		// 보유 주식 확인
		CustomerStock stock = customerStockJPARepository.findByCustomerIdAndCompanyId(auth.id(), companyId)
			.orElseThrow(() -> new NotFoundException("기업"));

		if (request.getShares() <= 0 || request.getShares() > stock.getShares()) {
			throw new BusinessException("보유 주식 수를 초과하는 투표는 불가능합니다.");
		}

		// 중복 투표 확인
		boolean exists = ballotJPARepository.existsByCustomerIdAndCampaignId(auth.id(), request.getCampaignId());
		if (exists) {
			throw new BusinessException("이미 해당 캠페인에 투표했습니다.");
		}

		Ballot ballot = Ballot.builder()
			.shares(request.getShares())
			.voteType(request.getVoteType())
			.voteChoice(request.getVoteChoice())
			.delegateToId(request.getDelegateId())
			.customer(stock.getCustomer())
			.build();

		Ballot saved = ballotJPARepository.save(ballot);
		return new BallotCreateResponse(saved.getId());
	}

	public Boolean delete(long ballotId) {
		this.ballotJPARepository.deleteById(ballotId);
		return true;
	}

	@Transactional(readOnly = true)
	public List<BallotResponse> findByMe(Auth auth) {
		return ballotJPARepository.findAll()
			.stream()
			.filter(ballot -> ballot.getCustomer().getId() == auth.id())
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
