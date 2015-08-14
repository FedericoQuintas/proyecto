package com.proyecto.rest.resource.currency.dto;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class ExchangeSessionDTO {
	
	@JsonProperty("closingPrice")
	private Date date;
	@JsonProperty("openingPrice")
	private Double price;
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	
	
}
