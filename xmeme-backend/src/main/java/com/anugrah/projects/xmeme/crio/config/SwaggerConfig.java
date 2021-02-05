package com.anugrah.projects.xmeme.crio.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuration file for Swagger documentation.
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Value("${server.port}")
	private int serverPort;

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				// .host("localhost:" + serverPort)
				.ignoredParameterTypes(Pageable.class)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}

}
