package com.proyecto.user.domain.valueobject;

import com.proyecto.common.currency.InvertarCurrency;

public class MarketValueVO {

	private InvertarCurrency currency;
	private Long value;

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public MarketValueVO(InvertarCurrency currency, Long initialValue) {
		this.currency = currency;
		this.value = initialValue;
	}

	public void setCurrency(InvertarCurrency currency) {
		this.currency = currency;
	}

	public InvertarCurrency getCurrency() {
		return this.currency;
	}

	public void calculate(Long lastTradingPrice) {
		this.value = +lastTradingPrice;

	}

}
