package com.blog.domain.auth.login.currentUser;

import java.util.Optional;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.blog.domain.auth.jwt.service.JwtService;

public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

	private final JwtService jwtService;

	public CurrentUserArgumentResolver(JwtService jwtService) {
		this.jwtService = jwtService;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean hasAnnotation = parameter.hasParameterAnnotation(CurrentUser.class);
		boolean parameterType = long.class.isAssignableFrom(parameter.getParameterType());
		return hasAnnotation && parameterType;  // 둘 다 충족할 시 true
	}

	// 추후 변수, 예외처리 수정 예정
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		// Authorization 헤더에서 토큰 추출
		String token = Optional.ofNullable(webRequest.getHeader("Authorization"))
			.map(accessToken -> accessToken.replaceFirst("Bearer ", ""))
			.orElseThrow(() -> new RuntimeException("Authorization 헤더가 없습니다."));

		// 토큰에서 userId 추출
		long userId = jwtService.extractId(token);
		if (userId == -1L) {
			throw new RuntimeException("유효하지 않은 토큰입니다.");
		}
		return userId;
	}
}
