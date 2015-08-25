package com.proyecto.user.domain.valueobject;

import org.codehaus.jackson.annotate.JsonProperty;

import com.proyecto.common.currency.InvertarCurrencyCode;

public class MarketValueVO {

	@JsonProperty("currency")
	private InvertarCurrencyCode currency;
	@JsonProperty("value")
	private Float value;

	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}

	public MarketValueVO(InvertarCurrencyCode currency, Float lastTradingPrice,
			Long ownedQuantity) {
		this.currency = currency;
		this.value = lastTradingPrice * ownedQuantity;
	}

	public void setCurrency(InvertarCurrencyCode currency) {
		this.currency = currency;
	}

	public InvertarCurrencyCode getCurrency() {
		return this.currency;
	}

	public void calculate(Float lastTradingPrice, Long ownedQuantity) {
		this.value = this.value + lastTradingPrice * ownedQuantity;

	}

}
