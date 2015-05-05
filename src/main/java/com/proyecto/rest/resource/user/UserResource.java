package com.proyecto.rest.resource.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.proyecto.rest.resource.user.dto.UserDTO;
import com.proyecto.user.domain.InvertarUser;
import com.proyecto.user.service.UserService;

@Controller("userResource")
public class UserResource {
	
	@Resource
	private UserService userService;

	public InvertarUser store(UserDTO userDTO) {
		return userService.store(userDTO);
	}

}
