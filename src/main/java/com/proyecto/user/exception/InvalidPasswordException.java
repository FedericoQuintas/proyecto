package com.proyecto.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.proyecto.common.error.InvertarErrorCode;
import com.proyecto.common.exception.ApplicationServiceException;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Password.")
public class InvalidPasswordException extends ApplicationServiceException {

	public InvalidPasswordException() {
		super("Invalid Password", InvertarErrorCode.INVALID_ARGUMENT);
	}

	private static final long serialVersionUID = -8303284266133311827L;

}
