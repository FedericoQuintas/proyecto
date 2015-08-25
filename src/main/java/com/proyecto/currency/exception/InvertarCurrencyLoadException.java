package com.proyecto.currency.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.proyecto.common.error.InvertarErrorCode;
import com.proyecto.common.exception.ApplicationServiceException;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Currency does not exist.")
public class InvertarCurrencyLoadException extends ApplicationServiceException {

	private static final long serialVersionUID = -6533258923371351146L;

	public InvertarCurrencyLoadException(Throwable throwable) {
		super(throwable.getMessage(), InvertarErrorCode.CURRENCY_LOADING_ERROR);
	}

}
