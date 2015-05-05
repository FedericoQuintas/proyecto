package com.proyecto.common.error;

public enum InvertarErrorCode {

	OBJECT_NOT_FOUND(404);

	private Integer code;

	InvertarErrorCode(Integer code) {
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

}
