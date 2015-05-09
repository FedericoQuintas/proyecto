package com.proyecto.common;

import javax.annotation.Resource;

import org.apache.log4j.BasicConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.proyecto.config.AppConfig;
import com.proyecto.portfolio.persistence.PortfolioDAO;
import com.proyecto.user.persistence.UserDAO;

@ContextConfiguration(classes = AppConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringBaseTest {

	@Resource
	private PortfolioDAO portfolioDAO;

	@Resource
	private UserDAO userDAO;

	@BeforeClass
	public static void init() {
		BasicConfigurator.configure();
	}

	@Before
	public void before() {

	}

	// TODO (FEDEQ): Solo se hace para restartear cada test. Cuando saquemos
	// persistencia en memoria
	// y pasemos todo a la implementación de una base de datos, va a ir
	// cambiando.
	@After
	public void after() {
		portfolioDAO.flush();
		userDAO.flush();
	}

}
