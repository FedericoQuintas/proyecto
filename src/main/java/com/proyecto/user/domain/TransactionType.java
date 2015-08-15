package com.proyecto.user.domain;

public enum TransactionType {
	PURCHASE("PURCHASE"), SELL("SELL");
	
	private String description;

	public String getDescription() {
		return description;
	}

	private TransactionType(String code) {
		this.description = code;
	}


}
