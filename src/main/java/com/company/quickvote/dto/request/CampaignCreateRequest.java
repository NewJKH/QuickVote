package com.company.quickvote.dto.request;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CampaignCreateRequest {
	@Schema(description = "기업 번호", example = "1")
	private long companyId;

	@Schema(description = "제목", example = "테스트용 제목")
	private String title;

	@Schema(description = "내용", example = "의결 제안")
	private String description;

	@Schema(description = "캠페인 시작일", example = "2025-09-26")
	private LocalDateTime startDate;

	@Schema(description = "캠페인 종료일", example = "2025-09-29")
	private LocalDateTime endDate;
}
