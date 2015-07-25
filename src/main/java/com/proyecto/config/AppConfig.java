package com.proyecto.config;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.proyecto.config.persistence.RepositoryConfiguration;

@Configuration
@Import({ SecurityConfig.class, MVCConfiguration.class })
@ComponentScan({ "com.proyecto.*" })
public class AppConfig {

	@Bean
	public RepositoryConfiguration userService() throws UnknownHostException {
		return new RepositoryConfiguration();
	}

}