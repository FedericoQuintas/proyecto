package com.proyecto.user;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;

import com.proyecto.common.SpringBaseTest;
import com.proyecto.rest.resource.user.dto.UserDTO;
import com.proyecto.user.domain.InvertarUser;
import com.proyecto.user.helper.UserHelper;
import com.proyecto.user.service.UserService;

public class UserServiceTest extends SpringBaseTest{

	@Resource
	private UserService userService;

	@Test
	public void whenCreatesUserThenUserIsCreated() {

		UserDTO userDTO = UserHelper.createDefaultUserDTO();
		
		InvertarUser user = userService.store(userDTO);

		Assert.assertNotNull(user);

	}

}
