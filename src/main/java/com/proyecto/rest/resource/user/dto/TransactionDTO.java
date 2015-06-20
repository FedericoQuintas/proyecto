package com.proyecto.rest.resource.user.dto;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class TransactionDTO {

	@JsonProperty("id")
	private Long id;
	@JsonProperty("priceSold")
	private float priceSold;
	@JsonProperty("pricePaid")
	private float pricePaid;
	@JsonProperty("tradingDate")
	private Date tradingDate;
	@JsonProperty("quantity")
	private Integer quantity;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public float getPriceSold() {
		return priceSold;
	}
	
	public void setPriceSold(float priceSold) {
		this.priceSold = priceSold;
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
	
}
