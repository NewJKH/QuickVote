package com.company.quickvote.dto.request;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CampaignCreateRequest {
	private long companyId;
	private String title;
	private String description;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
}
