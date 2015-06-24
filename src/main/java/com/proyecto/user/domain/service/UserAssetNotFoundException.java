package com.proyecto.user.domain.service;

import com.proyecto.common.error.InvertarErrorCode;
import com.proyecto.common.exception.DomainException;

public class UserAssetNotFoundException extends DomainException {

	private static final String USER_ASSET_NOT_FOUND = "User Asset Not Found";

	public UserAssetNotFoundException(String message, InvertarErrorCode code) {
		super(USER_ASSET_NOT_FOUND, InvertarErrorCode.OBJECT_NOT_FOUND);
	}

	public UserAssetNotFoundException() {
		super(USER_ASSET_NOT_FOUND, InvertarErrorCode.OBJECT_NOT_FOUND);
	}

	private static final long serialVersionUID = -8919618751619139503L;

}
