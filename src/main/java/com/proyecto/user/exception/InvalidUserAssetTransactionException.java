package com.proyecto.user.exception;

import com.proyecto.common.error.InvertarErrorCode;
import com.proyecto.common.exception.DomainException;

public class InvalidUserAssetTransactionException extends DomainException {

	private static final long serialVersionUID = 1394641350367845248L;

	public InvalidUserAssetTransactionException() {
		super("User asset cannot reach negative values",
				InvertarErrorCode.INVALID_ARGUMENT);
	}

}
