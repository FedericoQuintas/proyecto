package com.proyecto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.proyecto.asset.persistence.AssetDAO;
import com.proyecto.asset.persistence.AssetDAOImpl;
import com.proyecto.asset.persistence.AssetMongoDAOImpl;
import com.proyecto.asset.service.AssetService;
import com.proyecto.asset.service.AssetServiceImpl;
import com.proyecto.user.domain.service.PortfolioDomainService;
import com.proyecto.user.domain.service.PortfolioDomainServiceImpl;
import com.proyecto.user.persistence.UserDAO;
import com.proyecto.user.persistence.UserDAOImpl;
import com.proyecto.user.persistence.UserMongoDAOImpl;
import com.proyecto.user.service.UserService;
import com.proyecto.user.service.UserServiceImpl;
import com.proyecto.yahoofinance.service.YahooFinanceInformationService;
import com.proyecto.yahoofinance.service.YahooFinanceInformationServiceImpl;

@WebAppConfiguration
@EnableWebMvc
@Import({ SecurityConfig.class })
@ComponentScan({ "com.proyecto.*" })
public class AppConfig {

	private static String properties;

	public static String getProperties() {
		return properties;
	}

	public static void setProperties(String properties) {
		AppConfig.properties = properties;
	}

	@Bean
	public UserService userService() {
		return new UserServiceImpl();
	}

	@Bean
	public PortfolioDomainService portfolioService() {
		return new PortfolioDomainServiceImpl();
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
	public UserDAO userMongoDAO() {
		return new UserMongoDAOImpl();
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
	public AssetDAO assetMongoDAO() {
		return new AssetMongoDAOImpl();
	}

}