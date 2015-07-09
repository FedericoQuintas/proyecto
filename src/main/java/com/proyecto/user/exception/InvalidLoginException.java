package com.proyecto.user.exception;

import com.proyecto.common.error.InvertarErrorCode;
import com.proyecto.common.exception.ApplicationServiceException;

public class InvalidLoginException extends ApplicationServiceException {

	public InvalidLoginException() {
		super("Invalid Login", InvertarErrorCode.INVALID_LOGIN);
	}

	private static final long serialVersionUID = 2208482510505224190L;

}
