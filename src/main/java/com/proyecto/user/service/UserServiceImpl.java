package com.proyecto.user.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.proyecto.common.exception.ObjectNotFoundException;
import com.proyecto.rest.resource.user.dto.UserDTO;
import com.proyecto.user.domain.InvertarUser;
import com.proyecto.user.domain.factory.InvertarUserFactory;
import com.proyecto.user.exception.UserNotFoundException;
import com.proyecto.user.persistence.UserDAO;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserDAO userDAO;

	@Override
	public InvertarUser store(UserDTO userDTO) {

		InvertarUser user = InvertarUserFactory.create(userDTO, userDAO.nextID());
		
		return userDAO.store(user);
	}

	@Override
	public InvertarUser findById(Long id) throws UserNotFoundException {
		try {
			return userDAO.findById(id);
		} catch (ObjectNotFoundException e) {
			throw new UserNotFoundException();
		}
	}

}
