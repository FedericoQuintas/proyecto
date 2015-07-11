package com.proyecto.asset.exception;

import com.proyecto.common.error.InvertarErrorCode;
import com.proyecto.common.exception.ApplicationServiceException;

public class DBAccessException extends ApplicationServiceException {

	private static final long serialVersionUID = -1930304112674094041L;

	public DBAccessException(String message) {
		super(message, InvertarErrorCode.DATABASE_ERROR);
	}

}
