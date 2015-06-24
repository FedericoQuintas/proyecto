package com.proyecto.rest.resource.user.dto;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

import com.proyecto.common.currency.InvertarCurrency;

public class TransactionDTO {

	@JsonProperty("pricePaid")
	private float pricePaid;
	@JsonProperty("tradingDate")
	private Date tradingDate;
	@JsonProperty("quantity")
	private Integer quantity;
	@JsonProperty("currency")
	private InvertarCurrency currency;
	@JsonProperty("asset_id")
	private Long assetId;

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

	public void setCurrency(InvertarCurrency currency) {
		this.currency = currency;

	}

	public InvertarCurrency getCurrency() {
		return currency;
	}

	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}

	public Long getAssetId() {
		return assetId;
	}

}
