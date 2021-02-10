package com.anugrah.projects.xmeme.crio.config;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class AppConfig {

	@Value("${meme.page.size:100}")
	private Integer defaultPageSize;

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}

