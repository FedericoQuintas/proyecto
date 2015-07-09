package com.proyecto.user.exception;

import com.proyecto.common.error.InvertarErrorCode;
import com.proyecto.common.exception.ApplicationServiceException;

public class InvalidPasswordException extends ApplicationServiceException {

	public InvalidPasswordException() {
		super("Invalid Password", InvertarErrorCode.INVALID_ARGUMENT);
	}

	private static final long serialVersionUID = -8303284266133311827L;

}
