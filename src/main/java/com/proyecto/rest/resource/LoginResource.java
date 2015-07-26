package com.proyecto.rest.resource;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proyecto.rest.resource.user.dto.InvertarUserDTO;
import com.proyecto.rest.resource.user.dto.InvertarUserLoginDTO;
import com.proyecto.user.service.UserService;

@Controller("loginResource")
@RequestMapping("/login")
public class LoginResource {

	@Resource
	private UserService userService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public InvertarUserDTO login(HttpSession session,
			@RequestBody InvertarUserLoginDTO loginDTO) throws Exception {
		InvertarUserDTO login = userService.login(loginDTO);
		if (login != null) {
			session.setAttribute("MEMBER", login.getId());
		} else {
			throw new Exception("Invalid username or password");
		}
		return login;
	}

}
