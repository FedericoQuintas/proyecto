package com.proyecto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.proyecto.user.service.UserService;
import com.proyecto.user.service.UserServiceImpl;

@Configuration
public class AppConfig {

	@Bean
	public UserService userService() {
		return new UserServiceImpl();
	}

}