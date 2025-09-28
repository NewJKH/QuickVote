package com.company.quickvote.global.security.jwt;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.company.quickvote.global.security.auth.Auth;
import com.company.quickvote.global.security.auth.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {

	private static final String BEARER_PREFIX = "Bearer ";
	private static final Pattern BEARER_PATTERN = Pattern.compile("^Bearer\\s+[A-Za-z0-9-_.]+$");

	private static final long ACCESS_TOKEN_TIME = 60 * 60 * 1000L;
	private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
	@Value("${jwt.secret.key}")
	private String secretKey;
	private Key key;

	public static boolean isValidBearerToken(String authorizationHeader) {
		return authorizationHeader != null && BEARER_PATTERN.matcher(authorizationHeader).matches();
	}

	@PostConstruct
	public void init() {
		byte[] bytes = Base64.getDecoder().decode(secretKey);
		key = Keys.hmacShaKeyFor(bytes);
	}

	public String createAccessToken(Auth auth) {
		Date date = new Date();

		return Jwts.builder()
			.setSubject(String.valueOf(auth.id()))
			.claim("nickname", auth.nickname())
			.claim("role", auth.role().name())
			.setExpiration(new Date(date.getTime() + ACCESS_TOKEN_TIME))
			.setIssuedAt(date)
			.signWith(key, signatureAlgorithm)
			.compact();
	}

	public String getBearerToken(String token) {
		return BEARER_PREFIX + token;
	}

	public long getRemainingExpiration(String token) {
		Date expiration = extractClaims(token).getExpiration();
		return expiration.getTime() - System.currentTimeMillis();
	}

	public String extractToken(String tokenValue) {
		if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
			return tokenValue.substring(BEARER_PREFIX.length()); // "Bearer " 제거 후 반환
		}
		return null; //TODO : 공통 예외처리 로직
	}

	public Claims extractClaims(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	public Auth extractAuth(Claims claims) {
		return new Auth(
			Long.parseLong(claims.getSubject()),
			claims.get("nickname", String.class),
			Role.of(claims.get("role", String.class))
		);
	}

	public Long extractUserIdFromToken(String token) {
		Claims claims = extractClaims(token);
		return Long.parseLong(claims.getSubject());
	}
}
