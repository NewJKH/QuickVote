package com.company.quickvote.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StockResponse {

	private long stockId;
	private long companyId;
	private String companyName;
	private int shares;
}
