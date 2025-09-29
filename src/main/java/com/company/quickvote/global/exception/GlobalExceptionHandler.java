package com.company.quickvote.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ApiResponse> handleException(NotFoundException ex) {
		ApiResponse response = ApiResponse.create(ex.getMessage());
		return ResponseEntity
			.status(404)
			.body(response);
	}
	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<ApiResponse> handleException(ForbiddenException ex) {
		ApiResponse response = ApiResponse.create(ex.getMessage());
		return ResponseEntity
			.status(403)
			.body(response);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ApiResponse> handleException(BusinessException ex) {
		ApiResponse response = ApiResponse.create(ex.getMessage());
		return ResponseEntity
			.status(400)
			.body(response);
	}

	@ExceptionHandler(NotMatchedException.class)
	public ResponseEntity<ApiResponse> handleException(NotMatchedException ex) {
		ApiResponse response = ApiResponse.create(ex.getMessage());
		return ResponseEntity
			.status(400)
			.body(response);
	}


	public record ApiResponse(String message) {
		public static ApiResponse create(String message) {
				return new ApiResponse(message);
			}
		}
}
