package com.proyecto.common.exception;

import com.proyecto.common.error.InvertarErrorCode;

public class ObjectNotFoundException extends Exception {

	private static final long serialVersionUID = -441125545884006958L;
	private String message;
	private InvertarErrorCode errorCode;

	public ObjectNotFoundException(String string) {
		this.message = string;
		this.errorCode = InvertarErrorCode.OBJECT_NOT_FOUND;
	}

	public String getMessage() {
		return message;
	}

	public InvertarErrorCode getErrorCode() {
		return errorCode;
	}

}
