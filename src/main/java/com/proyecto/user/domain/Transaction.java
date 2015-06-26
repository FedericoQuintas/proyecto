package com.proyecto.user.domain;

import java.util.Date;

import com.proyecto.common.currency.InvertarCurrency;

public class Transaction {

	private Long id;
	private Float pricePaid;
	private Date tradingDate;
	private Integer quantity;
	private InvertarCurrency currency;
	private Long assetId;
	private TransactionType type;

	public Transaction(InvertarCurrency currency, Float price,
			Integer quantity, Date tradingDate, Long assetId,
			TransactionType transactionType) {
		this.currency = currency;
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

	public float getPricePaid() {
		return pricePaid;
	}

	public Date getTradingDate() {
		return tradingDate;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public InvertarCurrency getCurrency() {
		return this.currency;
	}

}
