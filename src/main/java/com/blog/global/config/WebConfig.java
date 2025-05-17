package com.blog.global.config;

import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.blog.domain.user.domain.repository.UserRepository;
import com.blog.domain.auth.jwt.filter.JwtFilter;
import com.blog.domain.auth.jwt.service.JwtService;
import com.blog.domain.auth.jwt.service.TokenProvider;
import com.blog.domain.auth.login.currentUser.CurrentUserArgumentResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	private final TokenProvider tokenProvider;
	private final JwtService jwtService;
	private final UserRepository userRepository;
	private final JwtFilter jwtFilter;


	public WebConfig(TokenProvider tokenProvider, JwtService jwtService, UserRepository userRepository,
		JwtFilter jwtFilter) {
		this.tokenProvider = tokenProvider;
		this.jwtService = jwtService;
		this.userRepository = userRepository;
		this.jwtFilter = jwtFilter;
	}

	@Bean
	public FilterRegistrationBean<JwtFilter> jwtAuthenticationFilter() {
		FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(jwtFilter);
		registrationBean.addUrlPatterns("/api/*"); // /api/* 경로에서 필터 적용
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<JwtFilter> customLoginFilter() {
		FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(jwtFilter);
		registrationBean.addUrlPatterns("/login"); // /login 경로에서 필터 적용
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<JwtFilter> customKakaoLoginFilter() {
		FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(jwtFilter);
		registrationBean.addUrlPatterns("/auth/login/kakao"); // 카카오 로그인 경로에서 필터 적용
		return registrationBean;
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new CurrentUserArgumentResolver(jwtService));
	}

}
