package com.company.quickvote.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerSignUpRequest {

	@Schema(description = "이메일", example = "test@gmail.com")
	@NotBlank(message = "이메일은 비어있지 않습니다.")
	@Email(message = "올바른 이메일 형식이어야 합니다.")
	private String email;

	@Schema(description = "닉네임", example = "TEST1")
	@NotBlank(message = "닉네임은 비어있지 않습니다.")
	@Size(min = 2, max = 12, message = "닉네임은 2~12자 사이여야 합니다.")
	@Pattern(regexp = "^[a-zA-Z0-9가-힣]+$", message = "닉네임은 한글, 영문, 숫자만 허용됩니다.")
	private String name;

	@Schema(description = "비밀번호", example = "hi123")
	@NotBlank(message = "비밀번호는 비어있지 않습니다.")
	@Size(min = 8, max = 20, message = "비밀번호는 8~20자 사이여야 합니다.")
	@Pattern(
		regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]+$",
		message = "비밀번호는 영문, 숫자, 특수문자를 모두 포함해야 합니다."
	)
	private String password;
}
