package com.company.quickvote.service;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.quickvote.dto.response.CampaignResponse;
import com.company.quickvote.entity.campaign.Campaign;
import com.company.quickvote.repository.CampaignJPARepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CampaignService {

	private final CampaignJPARepository campaignJPARepository;

	@Transactional(readOnly = true)
	public List<CampaignResponse> findAll() {
		return StreamSupport.stream(this.campaignJPARepository.findAll().spliterator(), false)
			.map(campaign -> new CampaignResponse(
				campaign.getId(),
				campaign.getTitle(),
				campaign.getCompany().getId(),
				campaign.getCompany().getName(),
				campaign.getStatus(),
				campaign.getStartAt(),
				campaign.getEndAt()))
			.toList();
	}

	@Transactional(readOnly = true)
	public CampaignResponse findById(Long campaignId) {
		Campaign campaign = campaignJPARepository.findById(campaignId)
			.orElseThrow(()->new IllegalArgumentException("캠페인을 찾을 수 없습니다."));

		return new CampaignResponse(
			campaign.getId(),
			campaign.getTitle(),
			campaign.getCompany().getId(),
			campaign.getCompany().getName(),
			campaign.getStatus(),
			campaign.getStartAt(),
			campaign.getEndAt()
		);
	}
}
