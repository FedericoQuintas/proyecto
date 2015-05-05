package com.proyecto.common.exception;

import com.proyecto.common.error.InvertarErrorCode;

public class ObjectNotFoundException extends PersistenceException {

	private static final long serialVersionUID = -441125545884006958L;


	public ObjectNotFoundException(String message) {
		super(message, InvertarErrorCode.OBJECT_NOT_FOUND);
	}


}
