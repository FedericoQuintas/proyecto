package com.proyecto.user.exception;

import com.proyecto.common.error.InvertarErrorCode;
import com.proyecto.common.exception.ApplicationServiceException;

public class UsernameAlreadyExistsException extends ApplicationServiceException {

	public UsernameAlreadyExistsException(String message, InvertarErrorCode code) {
		super(message, code);
	}

	public UsernameAlreadyExistsException() {
		super("Username already exists", InvertarErrorCode.INVALID_ARGUMENT);
	}

	private static final long serialVersionUID = 8856457653536720124L;

}
