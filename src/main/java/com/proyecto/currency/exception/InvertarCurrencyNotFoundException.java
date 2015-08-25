package com.proyecto.currency.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.proyecto.common.error.InvertarErrorCode;
import com.proyecto.common.exception.ApplicationServiceException;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Currency does not exist.")
public class InvertarCurrencyNotFoundException extends
		ApplicationServiceException {

	private static final long serialVersionUID = -317532730919321167L;

	public InvertarCurrencyNotFoundException(Throwable throwable) {
		super(throwable.getMessage(), InvertarErrorCode.OBJECT_NOT_FOUND);
	}

}
