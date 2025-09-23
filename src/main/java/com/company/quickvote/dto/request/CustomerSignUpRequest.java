package com.company.quickvote.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerSignUpRequest {

	@Schema(description = "이메일", example = "test@gmail.com")
	private String email;

	@Schema(description = "닉네임", example = "TEST1")
	private String name;

	@Schema(description = "비밀번호", example = "hi123")
	private String password;
}
