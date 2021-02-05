package com.anugrah.projects.xmeme.crio.config;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
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

	@Value("#{systemEnvironment['GITPOD_WORKSPACE_URL']}")
	private String hostname;

	@Bean
	public Docket api() throws SocketException {

		final Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
		while (networkInterfaces.hasMoreElements()) {
			final Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
			while (inetAddresses.hasMoreElements()) {
				final InetAddress inetAddress = inetAddresses.nextElement();
				System.out.println("inetAddress.getHostAddress() = " + inetAddress.getHostAddress());
				System.out.println("inetAddress.getHostName() = " + inetAddress.getHostName());
				System.out.println("inetAddress.getCanonicalHostName() = " + inetAddress.getCanonicalHostName());
			}
		}

        final String newHost = hostname.replace("https://", "");
        String host = serverPort + "-" + newHost;
        System.err.println(host);
		return new Docket(DocumentationType.SWAGGER_2)
				.host(host)
				.ignoredParameterTypes(Pageable.class)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}

}
