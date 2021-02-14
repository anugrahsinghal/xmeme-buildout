package com.anugrah.projects.xmeme.crio.config;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * <li>CORS configuration to enable client - server communications</li>
 */
@Component
public class TomcatContainerCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

	private static final Logger log = org.apache.logging.log4j.LogManager.getLogger(TomcatContainerCustomizer.class);

	@Override
	public void customize(TomcatServletWebServerFactory factory) {
	}

	/**
	 * Config to Inject CorsBean to enable client-server communication
	 */
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilterRegistrationBean() {

		FilterRegistrationBean<CorsFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new CorsFilter());
		filterRegistrationBean.setOrder(100);
		filterRegistrationBean.setName("CorsFilter");

		return filterRegistrationBean;
	}

	/**
	 * Config Class To enable CORS access
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


}