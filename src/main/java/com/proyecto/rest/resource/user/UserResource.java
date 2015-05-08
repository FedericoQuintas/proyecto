package com.proyecto.rest.resource.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proyecto.rest.resource.user.dto.UserDTO;
import com.proyecto.user.domain.InvertarUser;
import com.proyecto.user.service.UserService;

@Controller("userResource")
public class UserResource {

	@Resource
	private UserService userService;

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	@ResponseBody
	public InvertarUser store(UserDTO userDTO) {
		return userService.store(userDTO);
	}

}
