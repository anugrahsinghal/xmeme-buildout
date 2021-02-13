//package com.anugrah.projects.xmeme.crio.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.domain.Pageable;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
///**
// * Configuration file for Swagger documentation.
// */
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//
//	@Value("${server.port}")
//	private int serverPort;
//
//	@Value("#{systemEnvironment['GITPOD_WORKSPACE_URL']}")
//	private String gitpodHostName;
//
//	@Bean
//	public Docket api() {
//		String host;
//		if (gitpodHostName != null && !gitpodHostName.isEmpty()) {
//			final String newHost = gitpodHostName.replace("https://", "");
//			host = serverPort + "-" + newHost;
//		} else {
//			host = "localhost:" + serverPort;
//		}
//
//
//		return new Docket(DocumentationType.SWAGGER_2)
//				.host(host)
//				.ignoredParameterTypes(Pageable.class)
//				.select()
//				.apis(RequestHandlerSelectors.basePackage("com.anugrah.projects.xmeme.crio"))
//				.paths(PathSelectors.any())
//				.build();
//	}
//
//}
