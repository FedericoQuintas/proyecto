package com.proyecto.user.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.proyecto.rest.resource.user.dto.UserDTO;
import com.proyecto.user.domain.InvertarUser;
import com.proyecto.user.domain.factory.InvertarUserFactory;
import com.proyecto.user.persistence.UserDAOImpl;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserDAOImpl userDAO;

	
	@Override
	public InvertarUser store(UserDTO userDTO) {
		
		InvertarUser user = InvertarUserFactory.create(userDTO);
		
		return userDAO.store(user);
	}

}
