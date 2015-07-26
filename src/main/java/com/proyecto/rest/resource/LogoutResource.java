package com.proyecto.rest.resource;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
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
	public HttpStatus logout(HttpSession session)
			throws ApplicationServiceException {

		Long attribute = (Long) session.getAttribute("MEMBER");
		if (attribute == null) {
			return HttpStatus.FORBIDDEN;
		} else {
			session.removeAttribute("JSESSIONID");
			session.removeAttribute("MEMBER");
			session.invalidate();
			return HttpStatus.ACCEPTED;
		}
	}

}
