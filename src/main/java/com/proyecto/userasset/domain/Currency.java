package com.proyecto.userasset.domain;

public class Currency {
	private String currencyCode;//identificador univoco. Ej: ARS, USD, EUR
	private Long exchangeRateToARS; // tasa de cambio a pesos
	
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public Long getExchangeRateToARS() {
		return exchangeRateToARS;
	}
	public void setExchangeRateToARS(Long exchangeRateToARS) {
		this.exchangeRateToARS = exchangeRateToARS;
	}
	
	public Long convertToARS(Long amount){
		return amount*exchangeRateToARS;
	}
	
}
