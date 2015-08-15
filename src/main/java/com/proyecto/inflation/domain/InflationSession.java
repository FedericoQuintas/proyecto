package com.proyecto.inflation.domain;

import java.util.Date;

public class InflationSession {

	private Date date;
	private Double amount;

	public InflationSession() {

	}

	public InflationSession(Double averageValue, Date date) {
		this.setAmount(averageValue);
		this.setDate(date);

	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
