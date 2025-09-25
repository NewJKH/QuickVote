package com.company.quickvote.dto.response;

import java.time.LocalDateTime;

import com.company.quickvote.entity.campaign.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CampaignResponse {
	private long campaignId;
	private String title;

	private long companyId;
	private String companyName;

	private Status status;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
}
