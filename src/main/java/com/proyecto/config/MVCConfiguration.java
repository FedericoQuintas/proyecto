package com.proyecto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.proyecto.asset.service.AssetService;
import com.proyecto.asset.service.AssetServiceImpl;
import com.proyecto.config.security.SessionValidator;
import com.proyecto.currency.persistence.CurrencyDAO;
import com.proyecto.currency.persistence.CurrencyDAOImpl;
import com.proyecto.currency.service.CurrencyService;
import com.proyecto.currency.service.CurrencyServiceImpl;
import com.proyecto.user.domain.service.PortfolioDomainService;
import com.proyecto.user.domain.service.PortfolioDomainServiceImpl;
import com.proyecto.user.persistence.UserDAO;
import com.proyecto.user.persistence.UserDAOImpl;
import com.proyecto.user.persistence.UserMongoDAOImpl;
import com.proyecto.user.service.UserService;
import com.proyecto.user.service.UserServiceImpl;
import com.proyecto.yahoofinance.service.YahooFinanceInformationService;
import com.proyecto.yahoofinance.service.YahooFinanceInformationServiceImpl;

@EnableWebMvc
@Configuration
@EnableScheduling
public class MVCConfiguration extends WebMvcConfigurerAdapter {

	@Bean
	public UserService userService() {
		return new UserServiceImpl();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new SessionValidator())
				.addPathPatterns("/assets/*").addPathPatterns("/users/*")
				.addPathPatterns("/logout");

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
	public CurrencyDAO currencyMongoDAO() {
		return new CurrencyDAOImpl();
	}

	@Bean
	public CurrencyService currencyService() {
		return new CurrencyServiceImpl();
	}
}
