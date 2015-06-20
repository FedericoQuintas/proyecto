package com.proyecto.user.domain;

import java.util.Date;

public class Transaction {

	private Long id;
	private float priceSold;
	private float pricePaid;
	private Date tradingDate;
	private Integer quantity;	
	
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

	public float getPriceSold() {
		return priceSold;
	}

	public void setPriceSold(float priceSold) {
		this.priceSold = priceSold;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
}
