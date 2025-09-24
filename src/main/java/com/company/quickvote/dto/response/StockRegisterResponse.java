package com.company.quickvote.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StockRegisterResponse {
	private long stockId;
	private long companyId;
	private int shares;
}
