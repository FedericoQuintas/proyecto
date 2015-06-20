package com.proyecto.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.proyecto.common.error.InvertarErrorCode;
import com.proyecto.common.exception.ApplicationServiceException;
import com.proyecto.common.exception.DomainException;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Portfolio Argument")
public class InvalidPortfolioArgumentException extends
		ApplicationServiceException {

	private static final long serialVersionUID = 6818915161867374744L;

	public InvalidPortfolioArgumentException(Throwable throwable) {
		super(throwable.getMessage(), InvertarErrorCode.INVALID_ARGUMENT);
	}

	public InvalidPortfolioArgumentException(DomainException domainException) {
		super(domainException.getMessage(), domainException.getErrorCode());
	}

}
