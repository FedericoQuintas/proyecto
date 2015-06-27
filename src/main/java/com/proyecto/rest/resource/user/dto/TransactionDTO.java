package com.proyecto.rest.resource.user.dto;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

import com.proyecto.user.domain.TransactionType;

public class TransactionDTO {

	@JsonProperty("pricePaid")
	private Float pricePaid;
	@JsonProperty("tradingDate")
	private Date tradingDate;
	@JsonProperty("quantity")
	private Integer quantity;
	@JsonProperty("asset_id")
	private Long assetId;
	@JsonProperty("type")
	private TransactionType type;

	public void setType(TransactionType type) {
		this.type = type;
	}

	public Float getPricePaid() {
		return pricePaid;
	}

	public void setPricePaid(Float pricePaid) {
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

	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}

	public Long getAssetId() {
		return assetId;
	}

	public TransactionType getType() {
		return this.type;
	}

}
