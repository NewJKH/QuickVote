package com.company.quickvote.dto.request;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CampaignCreateRequest {

	@Schema(description = "기업 번호", example = "1")
	@Positive(message = "기업 번호는 0보다 커야 합니다.")
	private long companyId;

	@Schema(description = "제목", example = "테스트용 제목")
	@NotBlank(message = "제목은 비어 있을 수 없습니다.")
	@Size(max = 100, message = "제목은 최대 100자까지 가능합니다.")
	private String title;

	@Schema(description = "내용", example = "의결 제안")
	@NotBlank(message = "내용은 비어 있을 수 없습니다.")
	private String description;

	@Schema(description = "캠페인 시작일", example = "2025-09-26")
	@NotNull(message = "캠페인 시작일은 반드시 필요합니다.")
	private LocalDateTime startDate;

	@Schema(description = "캠페인 종료일", example = "2025-09-29")
	@NotNull(message = "캠페인 종료일은 반드시 필요합니다.")
	private LocalDateTime endDate;
}