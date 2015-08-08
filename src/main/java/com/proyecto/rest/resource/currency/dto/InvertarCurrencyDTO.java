package com.proyecto.rest.resource.currency.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.common.currency.InvertarCurrencyCode;

public class InvertarCurrencyDTO {

	@JsonProperty("code")
	private InvertarCurrencyCode code;

	public InvertarCurrencyCode getCode() {
		return code;
	}

	public void setCode(InvertarCurrencyCode code) {
		this.code = code;

	}

}
