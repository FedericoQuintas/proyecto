package com.proyecto.user.domain.valueobject;

import org.codehaus.jackson.annotate.JsonProperty;

import com.proyecto.common.currency.InvertarCurrency;

public class MarketValueVO {

	@JsonProperty("currency")
	private InvertarCurrency currency;
	@JsonProperty("value")
	private Float value;

	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}

	public MarketValueVO(InvertarCurrency currency, Float lastTradingPrice,
			Long ownedQuantity) {
		this.currency = currency;
		this.value = lastTradingPrice * ownedQuantity;
	}

	public void setCurrency(InvertarCurrency currency) {
		this.currency = currency;
	}

	public InvertarCurrency getCurrency() {
		return this.currency;
	}

	public void calculate(Float lastTradingPrice, Long ownedQuantity) {
		this.value = this.value + lastTradingPrice * ownedQuantity;

	}

}
