package com.company.quickvote.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerLoginResponse {
	private long id;
	private String name;
	private String token;
}
