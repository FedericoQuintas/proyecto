package com.proyecto.currency.domain;

import java.util.Date;

public class ExchangeSession {

	private Date date;
	private Double price;

	public ExchangeSession() {

	}

	public ExchangeSession(Double averageValue, Date date) {
		this.setPrice(averageValue);
		this.setDate(date);

	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
