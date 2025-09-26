package com.company.quickvote.global.security.jwt;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.company.quickvote.global.security.auth.Auth;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain) throws ServletException, IOException {

		String bearerJwt = request.getHeader("Authorization");

		if (!JwtUtil.isValidBearerToken(bearerJwt)) {
			chain.doFilter(request, response);
			return;
		}

		String jwt = jwtUtil.extractToken(bearerJwt);

		try {
			Claims claims = jwtUtil.extractClaims(jwt);
			Auth auth = jwtUtil.extractAuth(claims);

			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				auth, null, List.of(new SimpleGrantedAuthority(auth.role().getAuthority()))
			);
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);

			chain.doFilter(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			//TODO 예외처리
		}
	}
}