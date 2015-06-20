package com.proyecto.user.exception;

import com.proyecto.common.error.InvertarErrorCode;
import com.proyecto.common.exception.DomainException;

public class NoTransactionsFoundException extends DomainException {

	public NoTransactionsFoundException(String message, InvertarErrorCode code) {
		super(message, InvertarErrorCode.NO_TRANSACTIONS_FOUND);
	}

	public NoTransactionsFoundException() {
		super("No Transactions Found", InvertarErrorCode.NO_TRANSACTIONS_FOUND);
	}

	private static final long serialVersionUID = 3698592568198549202L;

}
