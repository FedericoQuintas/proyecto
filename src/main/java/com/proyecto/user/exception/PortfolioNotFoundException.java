package com.proyecto.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.proyecto.common.error.InvertarErrorCode;
import com.proyecto.common.exception.ApplicationServiceException;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Portfolio does not exist.")
public class PortfolioNotFoundException extends ApplicationServiceException {

	private static final long serialVersionUID = 4397268189361845092L;

	public PortfolioNotFoundException(Throwable throwable) {
		super(throwable.getMessage(), InvertarErrorCode.OBJECT_NOT_FOUND);
	}

	public PortfolioNotFoundException() {
		super("Portfolio does not exist.", InvertarErrorCode.OBJECT_NOT_FOUND);
	}

}
