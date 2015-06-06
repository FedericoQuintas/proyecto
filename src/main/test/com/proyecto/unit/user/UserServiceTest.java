package com.proyecto.unit.user;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.proyecto.common.SpringBaseTest;
import com.proyecto.common.error.InvertarErrorCode;
import com.proyecto.common.exception.ApplicationServiceException;
import com.proyecto.rest.resource.user.dto.InvertarUserDTO;
import com.proyecto.unit.user.helper.UserHelper;
import com.proyecto.user.exception.UserNotFoundException;
import com.proyecto.user.service.UserService;

public class UserServiceTest extends SpringBaseTest {

	@Resource
	private UserService userService;
	private InvertarUserDTO userDTO;

	@Before
	public void before() {
		storeUser();
	}

	@Test
	public void whenCreatesUserThenUserIsCreatedWithId() {

		Assert.assertNotNull(userDTO.getId());
	}

	@Test
	public void whenSearchAnUserByIdThenUserIsRetrieved()
			throws UserNotFoundException {

		InvertarUserDTO storedUserDTO = userService.findById(userDTO.getId());

		Assert.assertTrue(userDTO.getId().equals(storedUserDTO.getId()));

	}

	@Test
	public void whenSearchAnUserByIdAndUserDoesNotExistThenUserExceptionIsThrown()
			throws UserNotFoundException {

		Long NOT_EXISTING_USER_ID = new Long(1000);

		try {
			userService.findById(NOT_EXISTING_USER_ID);
		} catch (ApplicationServiceException e) {
			Assert.assertTrue(e.getErrorCode().equals(
					InvertarErrorCode.OBJECT_NOT_FOUND));
		}

	}

	private void storeUser() {

		userDTO = UserHelper.createDefaultUserDTO();

		userDTO = userService.store(userDTO);
	}

}
