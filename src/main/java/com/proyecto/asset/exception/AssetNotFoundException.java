package com.proyecto.asset.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.proyecto.common.error.InvertarErrorCode;
import com.proyecto.common.exception.ApplicationServiceException;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Asset does not exist.")
public class AssetNotFoundException extends ApplicationServiceException {

	private static final long serialVersionUID = -317532730919321167L;

	public AssetNotFoundException(Throwable throwable) {
		super(throwable.getMessage(), InvertarErrorCode.OBJECT_NOT_FOUND);
	}

}