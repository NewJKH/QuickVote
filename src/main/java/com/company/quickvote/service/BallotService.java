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
import com.company.quickvote.entity.snapshot.CustomerStockSnapshot;
import com.company.quickvote.global.exception.BusinessException;
import com.company.quickvote.global.exception.ForbiddenException;
import com.company.quickvote.global.exception.NotFoundException;
import com.company.quickvote.global.security.auth.Auth;
import com.company.quickvote.repository.BallotJPARepository;
import com.company.quickvote.repository.CampaignJPARepository;
import com.company.quickvote.repository.StockSnapshotJPARepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BallotService {

	private final BallotJPARepository ballotJPARepository;
	private final CampaignJPARepository campaignJPARepository;
	private final StockSnapshotJPARepository stockSnapshotJPARepository;

	/**
	 * 투표/위임 생성
	 * 흐름 설명
	 * @note 캠페인을 조회 -> 캠페인 상태를 확인한다. ( 현재시간이 시작과 종료 사이인지 확인 ) -> 로그인한 사용자가 해당 회사 주식을 보유 중인지 체크 -> 이미 해당 캠페인에 투표한 이력이 있는지 확인 -> 투표 엔티티 저장
	 *
	 */
	@Transactional
	public BallotCreateResponse save(BallotCreateRequest request, Auth auth) {
		Campaign campaign = campaignJPARepository.findById(request.getCampaignId())
			.orElseThrow(() -> new NotFoundException("캠페인"));

		// 캠페인 상태 확인
		Status status = resolveStatus(campaign);
		if (status != Status.OPEN) {
			throw new BusinessException("현재 캠페인은 투표할 수 없는 상태입니다.");
		}

		// 보유 주식 확인
		CustomerStockSnapshot snapshot = stockSnapshotJPARepository.findByCampaignIdAndCustomerId(campaign.getId(), auth.id())
			.orElseThrow(() -> new NotFoundException("기준일 당시 보유 주식"));

		if (request.getShares() <= 0 || request.getShares() > snapshot.getStockCount()) {
			throw new BusinessException("기준일 보유 주식 수를 초과하는 투표는 불가능합니다.");
		}


		// 중복 투표 확인
		boolean exists = ballotJPARepository.existsByCustomerIdAndCampaignId(auth.id(), campaign.getId());
		if (exists) {
			throw new BusinessException("이미 해당 캠페인에 투표 또는 위임했습니다.");
		}

		Ballot ballot = Ballot.builder()
			.shares(request.getShares())
			.voteType(request.getVoteType())
			.voteChoice(request.getVoteChoice())
			.delegateToId(request.getDelegateId())
			.customer(snapshot.getCustomer())
			.campaign(campaign)
			.build();

		Ballot saved = ballotJPARepository.save(ballot);
		return new BallotCreateResponse(saved.getId());
	}

	/**
	 * 투표 삭제
	 */
	@Transactional
	public Boolean delete(long ballotId, Auth auth) {
		Ballot ballot = ballotJPARepository.findById(ballotId)
			.orElseThrow(() -> new NotFoundException("투표"));

		if (ballot.getCustomer().getId() != auth.id()) {
			throw new ForbiddenException("본인 투표만 삭제할 수 있습니다.");
		}

		ballotJPARepository.delete(ballot);
		return true;
	}

	/**
	 * 내가 한 투표/위임 조회
	 */
	@Transactional(readOnly = true)
	public List<BallotResponse> findByMe(Auth auth) {
		return ballotJPARepository.findAll()
			.stream()
			.filter(ballot->ballot.getCustomer().getId()==auth.id())
			.map(BallotResponse::from)
			.toList();
	}

	/**
	 * 현재 캠페인 투표 가능 여부
	 */
	@Transactional(readOnly = true)
	public BallotVoteResponse canVote(Auth auth, Long campaignId) {
		Campaign campaign = campaignJPARepository.findById(campaignId)
			.orElseThrow(() -> new NotFoundException("캠페인"));

		Status status = resolveStatus(campaign);
		if (status != Status.OPEN) {
			return BallotVoteResponse.cannotVote("캠페인 상태가 유효하지 않습니다.");
		}

		boolean alreadyVoted = ballotJPARepository.existsByCustomerIdAndCampaignId(auth.id(), campaignId);
		if (alreadyVoted) {
			return BallotVoteResponse.cannotVote("이미 투표 또는 위임 완료");
		}

		return BallotVoteResponse.canVote();
	}

	/**
	 * 투표 철회
	 */
	@Transactional
	public void revoke(Long ballotId, Auth auth) {
		Ballot ballot = ballotJPARepository.findById(ballotId)
			.orElseThrow(() -> new NotFoundException("투표"));

		if (ballot.getCustomer().getId() != auth.id()) {
			throw new ForbiddenException("본인 투표만 철회할 수 있습니다.");
		}

		ballot.setRevoked(true);
	}

	/**
	 * 현재 시간 기준 캠페인 상태 계산
	 */
	private Status resolveStatus(Campaign campaign) {
		LocalDateTime now = LocalDateTime.now();
		if (now.isBefore(campaign.getStartAt())) return Status.CLOSE;
		if (now.isAfter(campaign.getEndAt())) return Status.CLOSE;
		return Status.OPEN;
	}
}
