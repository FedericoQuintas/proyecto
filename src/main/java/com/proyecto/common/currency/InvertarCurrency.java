package com.proyecto.common.currency;

public enum InvertarCurrency {
	US("U$S", new Integer(1));

	private Integer code;

	public Integer getCode() {
		return code;
	}

	private String description;

	public String getDescription() {
		return description;
	}

	private InvertarCurrency(String description, Integer code) {
		this.description = description;
		this.code = code;
	}

}
