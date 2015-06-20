package com.proyecto.user.domain;

import java.util.Date;

import com.proyecto.common.currency.InvertarCurrency;

public class Transaction {

	private Long id;
	private Float pricePaid;
	private Date tradingDate;
	private Integer quantity;
	private InvertarCurrency currency;

	public Transaction(InvertarCurrency currency, Float price, Integer quantity) {
		this.currency = currency;
		this.tradingDate = new Date();
		this.pricePaid = price;
		this.quantity = quantity;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getPricePaid() {
		return pricePaid;
	}

	public void setPricePaid(float pricePaid) {
		this.pricePaid = pricePaid;
	}

	public Date getTradingDate() {
		return tradingDate;
	}

	public void setTradingDate(Date tradingDate) {
		this.tradingDate = tradingDate;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public InvertarCurrency getCurrency() {
		return this.currency;
	}

}
