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
import com.company.quickvote.entity.customer.Customer;
import com.company.quickvote.global.exception.NotFoundException;
import com.company.quickvote.global.security.auth.Auth;
import com.company.quickvote.repository.CampaignJPARepository;
import com.company.quickvote.repository.CompanyJPARepository;
import com.company.quickvote.repository.CustomerJPARepository;
import com.company.quickvote.repository.StockJPARepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CampaignService {

	private final CampaignJPARepository campaignJPARepository;
	private final CompanyJPARepository companyJPARepository;
	private final StockJPARepository stockJPARepository;
	private final CustomerJPARepository customerJPARepository;

	@Transactional(readOnly = true)
	public List<CampaignResponse> findAll() {
		return StreamSupport.stream(this.campaignJPARepository.findAll().spliterator(), false)
			.map(campaign -> new CampaignResponse(
				campaign.getId(),
				campaign.getTitle(),
				campaign.getCompany().getId(),
				campaign.getCompany().getName(),
				campaign.getStartAt(),
				campaign.getEndAt()))
			.toList();
	}

	@Transactional(readOnly = true)
	public CampaignResponse findById(Long campaignId) {
		Campaign campaign = campaignJPARepository.findById(campaignId)
			.orElseThrow(()->new NotFoundException("캠페인"));

		return CampaignResponse.from(campaign);
	}

	@Transactional
	public CampaignResponse save(Auth auth, CampaignCreateRequest request) {
		Customer proposer = customerJPARepository.findById(auth.id())
			.orElseThrow(()->new NotFoundException("회원"));

		Company company = companyJPARepository.findById(request.getCompanyId())
			.orElseThrow(()->new NotFoundException("기업"));

		Campaign campaign = Campaign.builder()
			.title(request.getTitle())
			.description(request.getDescription())
			.startAt(request.getStartDate())
			.endAt(request.getEndDate())
			.status(Status.CLOSE)
			.company(company)
			.customer(proposer)
			.build();

		campaignJPARepository.save(campaign);

		return CampaignResponse.from(campaign);
	}

	@Transactional(readOnly = true)
	public List<CampaignResponse> findAvailableCampaignsByCustomerId(Auth auth) {
		List<Long> companies = stockJPARepository.findCompanyIdsByCustomerId(auth.id());

		if (companies.isEmpty()) {
			return List.of();
		}

		return campaignJPARepository.findByCompanyIdInAndStatus(companies, Status.OPEN)
			.stream()
			.map(CampaignResponse::from)
			.toList();
	}
}
