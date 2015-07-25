package com.proyecto.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.proyecto.config.persistence.RepositoryConfiguration;

@Configuration
@Import({ SecurityConfig.class, MVCConfiguration.class,
		RepositoryConfiguration.class })
@ComponentScan({ "com.proyecto.*" })
public class AppConfig {

}