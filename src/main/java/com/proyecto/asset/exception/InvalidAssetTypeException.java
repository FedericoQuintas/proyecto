package com.proyecto.asset.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.proyecto.common.error.InvertarErrorCode;
import com.proyecto.common.exception.ApplicationServiceException;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Asset Type")
public class InvalidAssetTypeException extends ApplicationServiceException {

	private static final long serialVersionUID = -5803190024277746452L;
	private static final String INVALID_ASSET_TYPE = "Invalid Asset type";

	public InvalidAssetTypeException() {
		super(INVALID_ASSET_TYPE, InvertarErrorCode.INVALID_ARGUMENT);
	}


}
