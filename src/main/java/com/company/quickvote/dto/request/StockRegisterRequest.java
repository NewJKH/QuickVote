package com.company.quickvote.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StockRegisterRequest {

	@Schema(description = "기업 ID", example = "1")
	@Positive(message = "기업 ID는 0보다 커야 합니다.")
	private long cpId;

	@Schema(description = "주식 등록 갯수", example = "10")
	@Positive(message = "주식 수는 0보다 커야 합니다.")
	private int shares;
}
