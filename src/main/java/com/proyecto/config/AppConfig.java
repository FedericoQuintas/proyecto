package com.proyecto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.proyecto.asset.persistence.AssetDAO;
import com.proyecto.asset.persistence.AssetDAOImpl;
import com.proyecto.asset.service.AssetService;
import com.proyecto.asset.service.AssetServiceImpl;
import com.proyecto.portfolio.persistence.PortfolioDAO;
import com.proyecto.portfolio.persistence.PortfolioDAOImpl;
import com.proyecto.portfolio.service.PortfolioService;
import com.proyecto.portfolio.service.PortfolioServiceImpl;
import com.proyecto.user.persistence.UserDAO;
import com.proyecto.user.persistence.UserDAOImpl;
import com.proyecto.user.service.UserService;
import com.proyecto.user.service.UserServiceImpl;

@Configuration
public class AppConfig {

	@Bean
	public UserService userService() {
		return new UserServiceImpl();
	}

	@Bean
	public UserDAO userDAO() {
		return new UserDAOImpl();
	}

	@Bean
	public AssetService assetService() {
		return new AssetServiceImpl();
	}

	@Bean
	public AssetDAO assetDAO() {
		return new AssetDAOImpl();
	}

	@Bean
	public PortfolioService portfolioService() {
		return new PortfolioServiceImpl();
	}

	@Bean
	public PortfolioDAO portfolioDAO() {
		return new PortfolioDAOImpl();
	}

}