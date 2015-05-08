package com.proyecto.user;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Before;
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
	private InvertarUser user;

	@Before
	public void before() {
		storeUser();
	}

	@Test
	public void whenCreatesUserThenUserIsCreatedWithId() {

		Assert.assertNotNull(user.getId());
	}

	@Test
	public void whenSearchAnUserByIdThenUserIsRetrieved()
			throws UserNotFoundException {

		InvertarUser storedUser = userService.findById(user.getId());

		Assert.assertTrue(user.getId().equals(storedUser.getId()));

	}

	@Test(expected = ApplicationServiceException.class)
	public void whenSearchAnUserByIdAndUserDoesNotExistThenUserExceptionIsThrown()
			throws UserNotFoundException {

		Long NOT_EXISTING_USER_ID = new Long(1000);

		InvertarUser storedUser = userService.findById(NOT_EXISTING_USER_ID);

		Assert.assertTrue(user.getId().equals(storedUser.getId()));

	}

	@Test
	public void whenUserIsCreatedThenUserHasPortfolios() {

		Assert.assertNotNull(user.getPortfolios());

	}

	private void storeUser() {

		UserDTO userDTO = UserHelper.createDefaultUserDTO();

		user = userService.store(userDTO);
	}

}
