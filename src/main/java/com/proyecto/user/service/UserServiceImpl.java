package com.proyecto.user.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.proyecto.common.exception.ObjectNotFoundException;
import com.proyecto.rest.resource.user.dto.InvertarUserDTO;
import com.proyecto.rest.resource.user.dto.factory.InvertarUserDTOFactory;
import com.proyecto.user.domain.InvertarUser;
import com.proyecto.user.domain.factory.InvertarUserFactory;
import com.proyecto.user.exception.UserNotFoundException;
import com.proyecto.user.persistence.UserDAO;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserDAO userDAO;

	@Override
	public InvertarUserDTO store(InvertarUserDTO userDTO) {

		InvertarUser user = InvertarUserFactory.create(userDTO,
				userDAO.nextID());

		InvertarUser storedUser = userDAO.store(user);

		return InvertarUserDTOFactory.create(storedUser);

	}

	@Override
	public InvertarUserDTO findById(Long id) throws UserNotFoundException {
		try {
			InvertarUser user = userDAO.findById(id);

			return InvertarUserDTOFactory.create(user);
		} catch (ObjectNotFoundException e) {
			throw new UserNotFoundException(e);
		}
	}

}
