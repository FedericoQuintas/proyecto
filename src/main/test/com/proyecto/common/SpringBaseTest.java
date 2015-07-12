package com.proyecto.common;

import javax.annotation.Resource;

import org.apache.log4j.BasicConfigurator;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.proyecto.asset.persistence.AssetDAO;
import com.proyecto.config.AppConfig;
import com.proyecto.user.persistence.UserDAO;

@ContextConfiguration(classes = AppConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringBaseTest {

	@Resource(name = "assetMongoDAO")
	private AssetDAO assetDAO;

	@Resource(name = "userMongoDAO")
	private UserDAO userDAO;

	@BeforeClass
	public static void init() {
		BasicConfigurator.configure();

	}

	@After
	public void after() {
		userDAO.flush();
		assetDAO.flush();
	}

}
