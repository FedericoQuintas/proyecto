package com.proyecto.common.currency;

public enum InvertarCurrencyCode {
	US("U$S", new Integer(1)), ARS("ARS$", new Integer(2)), USBL("U$S",
			new Integer(3)), EU("€", new Integer(4)), EUBL("€", new Integer(5));

	private Integer code;

	public Integer getCode() {
		return code;
	}

	private String description;

	public String getDescription() {
		return description;
	}

	private InvertarCurrencyCode(String description, Integer code) {
		this.description = description;
		this.code = code;
	}

}
