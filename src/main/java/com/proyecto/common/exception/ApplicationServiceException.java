package com.proyecto.common.exception;

import com.proyecto.common.error.InvertarErrorCode;

public class ApplicationServiceException extends Exception {

	private static final long serialVersionUID = -4798594325368390354L;

	private InvertarErrorCode errorCode;

	public ApplicationServiceException(String message, InvertarErrorCode code) {
		super(message);
		this.errorCode = code;
	}

	public InvertarErrorCode getErrorCode() {
		return errorCode;
	}
}
