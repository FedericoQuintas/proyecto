package com.proyecto.common.validator;

import org.apache.commons.lang3.StringUtils;

import com.proyecto.common.exception.InvalidArgumentException;

public class FieldValidator {

	public static void validateEmptyOrNull(String field)
			throws InvalidArgumentException {
		if (StringUtils.isBlank(field)) {
			throw new InvalidArgumentException(field);
		}

	}
	
	public static <T> void validateNull(T field) throws InvalidArgumentException{
		if(field == null){
			throw new InvalidArgumentException();
		}
	}

}
