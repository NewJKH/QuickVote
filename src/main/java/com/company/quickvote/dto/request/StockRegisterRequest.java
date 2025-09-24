package com.company.quickvote.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StockRegisterRequest {

	@Schema(description = "기업 ID", example = "1")
	private long cpId;

	@Schema(description = "주식 등록갯수", example = "10")
	private int shares;
}
