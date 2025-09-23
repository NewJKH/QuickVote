package com.company.quickvote.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerResponse {
	private long id;
	private String email;
	private String name;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
