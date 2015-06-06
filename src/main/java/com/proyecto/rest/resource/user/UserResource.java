package com.proyecto.rest.resource.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proyecto.rest.resource.user.dto.InvertarUserDTO;
import com.proyecto.user.exception.ApplicationServiceException;
import com.proyecto.user.service.UserService;

@Controller("userResource")
@RequestMapping("/users")
public class UserResource {

	@Resource
	private UserService userService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public InvertarUserDTO store(InvertarUserDTO userDTO) {
		return userService.store(userDTO);
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public InvertarUserDTO findUserById(@PathVariable("userId") Long userId)
			throws ApplicationServiceException {
		return userService.findById(userId);
	}
}
