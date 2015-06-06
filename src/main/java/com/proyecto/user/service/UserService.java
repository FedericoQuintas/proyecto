package com.proyecto.user.service;

import com.proyecto.rest.resource.user.dto.InvertarUserDTO;
import com.proyecto.user.exception.UserNotFoundException;

public interface UserService {

	/**
	 * Stores the UserDTO specified and retrieves it with new Id assigned.
	 * 
	 * @param userDTO
	 * @return InvertarUserDTO
	 */
	InvertarUserDTO store(InvertarUserDTO userDTO);

	/**
	 * Retrieves the InvertarUserDTO with specified ID
	 * 
	 * @param id
	 * @return InvertarUserDTO
	 * @throws UserNotFoundException
	 */
	InvertarUserDTO findById(Long id) throws UserNotFoundException;

}
