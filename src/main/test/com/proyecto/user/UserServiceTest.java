package com.proyecto.user;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;

import com.proyecto.user.domain.InvertarUser;
import com.proyecto.user.service.UserService;

public class UserServiceTest {

	@Resource(name = "userService")
	private UserService userService;

	@Test
	public void whenCreatesUserThenUserIsCreated() {

		InvertarUser user = userService.store();

		Assert.assertNotNull(user);

	}

}
