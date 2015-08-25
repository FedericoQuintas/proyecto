package com.proyecto.common.currency;

public enum InvertarCurrencyCode {
	US("US"), ARS("ARS"), USBL("USBL"), EU("EU"), EUBL("EUBL");

	private String code;

	public String getCode() {
		return code;
	}

	private InvertarCurrencyCode(String code) {
		this.code = code;
	}

}
