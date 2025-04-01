package com.blog.global.config;

import java.util.List;

import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

public class SwaggerConfig {
	@Bean
	public OpenAPI openAPI() {
		SecurityScheme securityScheme = getSecurityScheme();
		SecurityRequirement securityRequirement = getSecurityRequireMent();

		return new OpenAPI()
			.components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
			.security(List.of(securityRequirement));
	}

	private SecurityScheme getSecurityScheme() {
		return new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
			.in(SecurityScheme.In.HEADER).name("Authorization");
	}

	private SecurityRequirement getSecurityRequireMent() {
		return new SecurityRequirement().addList("bearerAuth");
	}
}
