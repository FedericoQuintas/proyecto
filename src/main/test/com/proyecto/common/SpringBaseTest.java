package com.proyecto.common;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.proyecto.asset.persistence.AssetDAO;
import com.proyecto.config.AppConfig;
import com.proyecto.user.persistence.UserDAO;

@ContextConfiguration(classes = AppConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class SpringBaseTest {

	@Resource(name = "assetMongoDAO")
	private AssetDAO assetDAO;

	@Resource(name = "userMongoDAO")
	private UserDAO userDAO;

	@After
	public void after() {
		userDAO.flush();
		assetDAO.flush();
	}

}
