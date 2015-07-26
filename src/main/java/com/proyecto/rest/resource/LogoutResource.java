package com.proyecto.rest.resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proyecto.common.exception.ApplicationServiceException;

@Controller("logoutResource")
@RequestMapping("/logout")
public class LogoutResource {

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public void logout() throws ApplicationServiceException {
	}

}
