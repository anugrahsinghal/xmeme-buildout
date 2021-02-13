package com.anugrah.projects.xmeme.crio.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * To Inject Bean Dependencies Needed by other classes
 */
@Getter
@Setter
@Configuration
public class AppConfig {

	/**
	 * @return instance of model mapper
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	/**
	 * @return instance of rest temolate
	 */
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	/*https://www.dariawan.com/tutorials/spring/documenting-spring-boot-rest-api-springdoc-openapi-3/*/

	/**
	 * @return OPEN API documentation
	 */
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().info(new Info()
				.title("X MEME application")
				.termsOfService("http://swagger.io/terms/")
				.license(new License().name("Apache 2.0").url("http://springdoc.org")));

	}

}

