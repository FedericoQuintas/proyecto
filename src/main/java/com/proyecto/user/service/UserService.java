package com.proyecto.user.service;

import com.proyecto.rest.resource.user.dto.UserDTO;
import com.proyecto.user.domain.InvertarUser;
import com.proyecto.user.exception.UserNotFoundException;

public interface UserService {

	/**
	 * Stores the User specified and retrieves it with new Id assigned.
	 * 
	 * @param userDTO
	 * @return InvertarUser
	 */
	InvertarUser store(UserDTO userDTO);

	/**
	 * Retrieves the User with specified ID
	 * 
	 * @param id
	 * @return InvertarUser
	 * @throws UserNotFoundException
	 */
	InvertarUser findById(Long id) throws UserNotFoundException;

}
