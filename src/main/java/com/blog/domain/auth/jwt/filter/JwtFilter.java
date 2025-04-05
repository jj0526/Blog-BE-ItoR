package com.blog.domain.auth.jwt.filter;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.blog.domain.auth.jwt.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
	@Value("#{'${blog.excluded-urls}'.split(',')}")
	private List<String> excludedUrls;

	private final JwtService jwtService;

	@Autowired
	public JwtFilter(JwtService jwtService) {
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if (excludedUrls.contains(request.getRequestURI())) {
			filterChain.doFilter(request, response);
			return;
		}

		String accessToken = jwtService.extractAccessToken(request)
			.filter(jwtService::isTokenValid)
			.orElse(null);	// 먼저 Access Token 추출

		if (accessToken != null) {// Access Token이 유효
			filterChain.doFilter(request, response);
			return;
		}

		String refreshToken = jwtService.extractRefreshToken(request)
			.filter(jwtService::isTokenValid)
			.orElse(null);

		if (refreshToken != null) {
			jwtService.checkAccessTokenAndRefreshToken(request, response, filterChain, refreshToken);
		}
	}

}
