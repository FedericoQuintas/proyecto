package com.proyecto.user.domain;

import java.util.Date;

public class Transaction {

	private Long id;
	private Float pricePaid;
	private Date tradingDate;
	private Long quantity;
	private Long assetId;
	private TransactionType type;

	public Transaction(Float price, Long quantity, Date tradingDate,
			Long assetId, TransactionType transactionType) {
		this.tradingDate = tradingDate;
		this.pricePaid = price;
		this.quantity = quantity;
		this.assetId = assetId;
		this.type = transactionType;
	}

	public TransactionType getType() {
		return type;
	}

	public Long getAssetId() {
		return assetId;
	}

	public Long getId() {
		return id;
	}

	public Float getPricePaid() {
		return pricePaid;
	}

	public Date getTradingDate() {
		return tradingDate;
	}

	public Long getQuantity() {
		return quantity;
	}

}
