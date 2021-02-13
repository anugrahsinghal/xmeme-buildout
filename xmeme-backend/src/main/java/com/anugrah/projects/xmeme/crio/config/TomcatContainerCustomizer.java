package com.anugrah.projects.xmeme.crio.config;

import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.connector.Connector;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Configuration for: <br>
 * <ol>
 *     <li>Running swagger api on different port</li>
 *     <li>CORS configuration to enable client - server communications</li>
 * </ol>
 *
 * @implNote Swagger running on different port than application was accomplished by help of<br>
 * <a href="https://github.com/mafor/swagger-ui-port">Swagger Port</a>
 */
@Component
public class TomcatContainerCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

	private static final Logger log = org.apache.logging.log4j.LogManager.getLogger(TomcatContainerCustomizer.class);
	@Value("${swagger.port}")

	private int swaggerPort;

	@Value("${swagger.paths}")
	private List<String> swaggerPaths;

	@Override
	public void customize(TomcatServletWebServerFactory factory) {

		Connector swaggerConnector = new Connector();
		swaggerConnector.setPort(swaggerPort);
		factory.addAdditionalTomcatConnectors(swaggerConnector);
	}

	@Bean
	public FilterRegistrationBean<SwaggerFilter> swaggerFilterRegistrationBean() {

		FilterRegistrationBean<SwaggerFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new SwaggerFilter());
		filterRegistrationBean.setOrder(-100);
		filterRegistrationBean.setName("SwaggerFilter");

		return filterRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilterRegistrationBean() {

		FilterRegistrationBean<CorsFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new CorsFilter());
		filterRegistrationBean.setOrder(100);
		filterRegistrationBean.setName("CorsFilter");

		return filterRegistrationBean;
	}

	/**
	 * To enable CORS access
	 */
	private class CorsFilter extends OncePerRequestFilter {

		@Override
		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
			System.out.println("CORS REQUEST came here outer");
			response.setHeader("Access-Control-Allow-Methods", "GET, PATCH, POST, PUT, DELETE, OPTIONS");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Max-Age", "3600");
			response.setHeader("Access-Control-Allow-Headers", "*");
			response.addHeader("Access-Control-Expose-Headers", "*");
			if ("OPTIONS".equals(request.getMethod())) {
				log.trace("CORS REQUEST came here");
				response.setStatus(HttpServletResponse.SC_OK);
				response.setHeader("Access-Control-Allow-Methods", "GET, PATCH, POST, PUT, DELETE, OPTIONS");
				log.trace(response);
			} else {
				filterChain.doFilter(request, response);
			}
		}
	}

	/**
	 * To enable Swagger Access
	 */
	private class SwaggerFilter extends OncePerRequestFilter {

		private final AntPathMatcher pathMatcher = new AntPathMatcher();

		@Override
		protected void doFilterInternal(HttpServletRequest httpServletRequest,
		                                HttpServletResponse httpServletResponse,
		                                FilterChain filterChain) throws ServletException, IOException {

			boolean isSwaggerPath = swaggerPaths.stream()
					.anyMatch(path -> pathMatcher.match(path, httpServletRequest.getServletPath()));
			boolean isSwaggerPort = httpServletRequest.getLocalPort() == swaggerPort;

			if (isSwaggerPath == isSwaggerPort) {
				filterChain.doFilter(httpServletRequest, httpServletResponse);
			} else {
				httpServletResponse.sendError(404);
			}
		}
	}


}