package com.proyecto.asset.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.proyecto.common.error.InvertarErrorCode;
import com.proyecto.common.exception.ApplicationServiceException;
import com.proyecto.common.exception.DomainException;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Trading Session Argument")
public class InvalidTradingSessionArgumentException extends ApplicationServiceException{

	private static final long serialVersionUID = -673605780976260005L;

	public InvalidTradingSessionArgumentException(Throwable throwable) {
		super(throwable.getMessage(), InvertarErrorCode.INVALID_ARGUMENT);
	}

	public InvalidTradingSessionArgumentException(DomainException domainException) {
		super(domainException.getMessage(), domainException.getErrorCode());
	}
}
