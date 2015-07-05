package com.proyecto.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.proyecto.common.error.InvertarErrorCode;
import com.proyecto.common.exception.ApplicationServiceException;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Portfolio name already in use.")
public class PortfolioNameAlreadyInUseException extends ApplicationServiceException {
	
	private static final long serialVersionUID = 2205026363877823517L;

	public PortfolioNameAlreadyInUseException(Throwable throwable){
		super(throwable.getMessage(), InvertarErrorCode.INVALID_ARGUMENT);
	}
	
	public PortfolioNameAlreadyInUseException(){
		super("Portfolio name already in use.", InvertarErrorCode.INVALID_ARGUMENT);
	}

}
