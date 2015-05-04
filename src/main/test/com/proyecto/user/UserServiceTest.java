package com.proyecto.user;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.proyecto.config.AppConfig;
import com.proyecto.user.domain.InvertarUser;
import com.proyecto.user.service.UserService;

@ContextConfiguration(classes = AppConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@Test
	public void whenCreatesUserThenUserIsCreated() {

		InvertarUser user = userService.store();

		Assert.assertNotNull(user);

	}

}
