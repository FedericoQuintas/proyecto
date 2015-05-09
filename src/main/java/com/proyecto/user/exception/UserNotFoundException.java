package com.proyecto.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.proyecto.common.error.InvertarErrorCode;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User does not exist.")
public class UserNotFoundException extends ApplicationServiceException {

	public UserNotFoundException(Throwable throwable) {
		super(throwable.getMessage(), InvertarErrorCode.OBJECT_NOT_FOUND);
	}

	private static final long serialVersionUID = 842745658499162009L;

}
