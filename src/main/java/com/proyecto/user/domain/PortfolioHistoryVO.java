package com.proyecto.user.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.rest.resource.user.dto.TransactionDTO;

public class PortfolioHistoryVO {

	@JsonProperty("date")
	private Date date;
	@JsonProperty("selling_transactions")
	private List<TransactionDTO> sellingTransactions;
	@JsonProperty("purchasing_transactions")
	private List<TransactionDTO> purchasingTransactions;
	@JsonProperty("prices_by_asset")
	private Map<Long, Double> pricesByAsset;

	public void setDate(Date date) {
		this.date = date;
	}

	public void setSellingTransactions(List<TransactionDTO> sellingTransactions) {
		this.sellingTransactions = sellingTransactions;
	}

	public void setPurchasingTransactions(
			List<TransactionDTO> purchasingTransactions) {
		this.purchasingTransactions = purchasingTransactions;
	}

	public void setPricesByAsset(Map<Long, Double> pricesByAsset) {
		this.pricesByAsset = pricesByAsset;
	}

	public List<TransactionDTO> getSellingTransactions() {
		return sellingTransactions;
	}

	public List<TransactionDTO> getPurchasingTransactions() {
		return purchasingTransactions;
	}

	public Date getDate() {
		return date;
	}

	public Map<Long, Double> getPricesByAsset() {
		return pricesByAsset;
	}

}
