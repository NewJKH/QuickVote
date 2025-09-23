package com.company.quickvote.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerSignUpResponse {
	private long userId;
	private String name;
	private String email;
}
