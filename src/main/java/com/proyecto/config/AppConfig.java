package com.proyecto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.proyecto.asset.persistence.AssetDAO;
import com.proyecto.asset.persistence.AssetDAOImpl;
import com.proyecto.asset.service.AssetService;
import com.proyecto.asset.service.AssetServiceImpl;
import com.proyecto.user.persistence.UserDAO;
import com.proyecto.user.persistence.UserDAOImpl;
import com.proyecto.user.service.PortfilioServiceImpl;
import com.proyecto.user.service.PortfolioService;
import com.proyecto.user.service.UserAssetService;
import com.proyecto.user.service.UserAssetServiceImpl;
import com.proyecto.user.service.UserService;
import com.proyecto.user.service.UserServiceImpl;
import com.proyecto.yahoofinance.service.YahooFinanceInformationService;
import com.proyecto.yahoofinance.service.YahooFinanceInformationServiceImpl;

@Configuration
public class AppConfig {

	@Bean
	public UserService userService() {
		return new UserServiceImpl();
	}

	@Bean
	public UserAssetService userAssetService() {
		return new UserAssetServiceImpl();
	}

	@Bean
	public PortfolioService portfolioService() {
		return new PortfilioServiceImpl();
	}

	@Bean
	public YahooFinanceInformationService yahooFinanceService() {
		return new YahooFinanceInformationServiceImpl();
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

}