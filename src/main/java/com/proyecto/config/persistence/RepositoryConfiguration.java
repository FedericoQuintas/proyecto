package com.proyecto.config.persistence;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RepositoryConfiguration {

	@Bean
	public MongoAccessConfiguration mongoAcessConfiguration()
			throws UnknownHostException {
		return new MongoAccessConfiguration();
	}
}
