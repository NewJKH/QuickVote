package com.company.quickvote.global.exception;

public class ForbiddenException extends RuntimeException {
	public ForbiddenException(String message) {
		super(message);
	}
}
