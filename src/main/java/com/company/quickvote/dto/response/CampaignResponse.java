package com.company.quickvote.dto.response;

import java.time.LocalDateTime;

import com.company.quickvote.entity.campaign.Campaign;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CampaignResponse {
	private long campaignId;
	private String title;

	private long companyId;
	private String companyName;

	private LocalDateTime startDate;
	private LocalDateTime endDate;

	public static CampaignResponse from(Campaign campaign) {
		return new CampaignResponse(
			campaign.getId(),
			campaign.getTitle(),
			campaign.getCompany().getId(),
			campaign.getCompany().getName(),
			campaign.getStartAt(),
			campaign.getEndAt()
		);
	}
}
