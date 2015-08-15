package com.proyecto.rest.resource.user.dto;

import org.codehaus.jackson.annotate.JsonProperty;

import com.proyecto.user.domain.TransactionType;

public class TransactionDTO {

	@JsonProperty("price_paid")
	private Float pricePaid;
	@JsonProperty("trading_date")
	private Long tradingDate;
	@JsonProperty("quantity")
	private Long quantity;
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

	public Long getTradingDate() {
		return tradingDate;
	}

	public void setTradingDate(Long tradingDate) {
		this.tradingDate = tradingDate;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
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
