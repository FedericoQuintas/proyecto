package com.proyecto.user.exception;

import com.proyecto.common.error.InvertarErrorCode;

public class UserNotFoundException extends ApplicationServiceException {

	public UserNotFoundException(Throwable throwable) {
		super(throwable.getMessage(), InvertarErrorCode.OBJECT_NOT_FOUND);
	}

	private static final long serialVersionUID = 842745658499162009L;

}
