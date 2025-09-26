package com.company.quickvote.service;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.quickvote.dto.request.CampaignCreateRequest;
import com.company.quickvote.dto.response.CampaignResponse;
import com.company.quickvote.entity.campaign.Campaign;
import com.company.quickvote.entity.campaign.Status;
import com.company.quickvote.entity.company.Company;
import com.company.quickvote.global.exception.NotFoundException;
import com.company.quickvote.repository.CampaignJPARepository;
import com.company.quickvote.repository.CompanyJPARepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CampaignService {

	private final CampaignJPARepository campaignJPARepository;
	private final CompanyJPARepository companyJPARepository;

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
			.orElseThrow(()->new NotFoundException("캠페인"));

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

	@Transactional
	public CampaignResponse save(CampaignCreateRequest request) {
		Company company = companyJPARepository.findById(request.getCompanyId())
			.orElseThrow(()->new NotFoundException("기업"));

		Campaign campaign = new Campaign(
			request.getTitle(),
			request.getDescription(),
			request.getStartDate(),
			request.getEndDate(),
			Status.CLOSE,
			company
		);

		campaignJPARepository.save(campaign);

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
