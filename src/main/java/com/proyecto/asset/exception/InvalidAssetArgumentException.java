package com.proyecto.asset.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.proyecto.common.error.InvertarErrorCode;
import com.proyecto.common.exception.ApplicationServiceException;
import com.proyecto.common.exception.DomainException;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Asset Argument")
public class InvalidAssetArgumentException extends ApplicationServiceException {

	private static final long serialVersionUID = 6818915161867374744L;

	public InvalidAssetArgumentException(Throwable throwable) {
		super(throwable.getMessage(), InvertarErrorCode.INVALID_ARGUMENT);
	}

	public InvalidAssetArgumentException(DomainException domainException) {
		super(domainException.getMessage(), domainException.getErrorCode());
	}

}
