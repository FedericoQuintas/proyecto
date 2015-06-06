package com.proyecto.common.exception;

import com.proyecto.common.error.InvertarErrorCode;

public class InvalidArgumentException extends DomainException {

	private static final long serialVersionUID = 9196190449392880354L;
	private static final String INVALID_ARGUMENT = "Invalid Argument";

	public InvalidArgumentException(String field) {
		super(INVALID_ARGUMENT, InvertarErrorCode.INVALID_ARGUMENT);
	}

}
