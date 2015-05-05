package com.proyecto.user;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.proyecto.common.SpringBaseTest;
import com.proyecto.user.domain.InvertarUser;
import com.proyecto.user.service.UserService;

public class UserServiceTest extends SpringBaseTest{

	@Autowired
	private UserService userService;

	@Test
	public void whenCreatesUserThenUserIsCreated() {

		InvertarUser user = userService.store();

		Assert.assertNotNull(user);

	}

}
