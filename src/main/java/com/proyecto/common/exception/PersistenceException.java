package com.proyecto.common.exception;

import com.proyecto.common.error.InvertarErrorCode;

public class PersistenceException extends Exception {

	private static final long serialVersionUID = -693389899921329953L;
	
	private InvertarErrorCode errorCode;
	
	public PersistenceException(String message,
			InvertarErrorCode code) {
		super(message);
		this.errorCode = code;
	}

	public InvertarErrorCode getErrorCode() {
		return errorCode;
	}

}
