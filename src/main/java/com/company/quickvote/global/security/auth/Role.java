package com.company.quickvote.global.security.auth;

import java.util.Arrays;

public enum Role {
    ADMIN, USER;

	public static Role of(String role) {
		return Arrays.stream(Role.values())
			.filter(r -> r.name().equalsIgnoreCase(role))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException(""));
	}

    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}