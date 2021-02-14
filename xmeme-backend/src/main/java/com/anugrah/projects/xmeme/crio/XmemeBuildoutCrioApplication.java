package com.anugrah.projects.xmeme.crio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class XmemeBuildoutCrioApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(XmemeBuildoutCrioApplication.class);
	}


	public static void main(String[] args) {
		SpringApplication.run(XmemeBuildoutCrioApplication.class, args);
	}

}
