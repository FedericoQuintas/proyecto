package com.proyecto.user;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;

import com.proyecto.common.SpringBaseTest;
import com.proyecto.rest.resource.user.dto.UserDTO;
import com.proyecto.user.domain.InvertarUser;
import com.proyecto.user.exception.ApplicationServiceException;
import com.proyecto.user.exception.UserNotFoundException;
import com.proyecto.user.helper.UserHelper;
import com.proyecto.user.service.UserService;

public class UserServiceTest extends SpringBaseTest {

	@Resource
	private UserService userService;

	@Test
	public void whenCreatesUserThenUserIsCreatedWithId() {

		UserDTO userDTO = UserHelper.createDefaultUserDTO();

		InvertarUser user = userService.store(userDTO);

		Assert.assertNotNull(user.getId());
	}

	@Test
	public void whenSearchAnUserByIdThenUserIsRetrieved()
			throws UserNotFoundException {

		UserDTO userDTO = UserHelper.createDefaultUserDTO();

		InvertarUser user = userService.store(userDTO);

		InvertarUser storedUser = userService.findById(user.getId());

		Assert.assertTrue(user.getId().equals(storedUser.getId()));

	}

	@Test(expected = ApplicationServiceException.class)
	public void whenSearchAnUserByIdAndUserDoesNotExistThenUserExceptionIsThrown()
			throws UserNotFoundException {

		Long NOT_EXISTING_USER_ID = new Long(1000);

		UserDTO userDTO = UserHelper.createDefaultUserDTO();

		InvertarUser user = userService.store(userDTO);

		InvertarUser storedUser = userService.findById(NOT_EXISTING_USER_ID);

		Assert.assertTrue(user.getId().equals(storedUser.getId()));

	}

}
