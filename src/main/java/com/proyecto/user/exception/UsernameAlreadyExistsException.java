package com.proyecto.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.proyecto.common.error.InvertarErrorCode;
import com.proyecto.common.exception.ApplicationServiceException;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Username.")
public class UsernameAlreadyExistsException extends ApplicationServiceException {

	public UsernameAlreadyExistsException(String message, InvertarErrorCode code) {
		super(message, code);
	}

	public UsernameAlreadyExistsException() {
		super("Username already exists", InvertarErrorCode.INVALID_ARGUMENT);
	}

	private static final long serialVersionUID = 8856457653536720124L;

}
