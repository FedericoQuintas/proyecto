package com.proyecto.user.exception;

import com.proyecto.common.error.InvertarErrorCode;
import com.proyecto.common.exception.ApplicationServiceException;

public class UserMailAlreadyExistsException extends ApplicationServiceException {

	public UserMailAlreadyExistsException(String message, InvertarErrorCode code) {
		super(message, code);
	}

	public UserMailAlreadyExistsException() {
		super("User mail already exists", InvertarErrorCode.INVALID_ARGUMENT);
	}

	private static final long serialVersionUID = -1944442521712948890L;

}
