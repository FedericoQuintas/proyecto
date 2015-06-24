package com.proyecto.rest.resource.user.dto;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class UserAssetDTO {
	@JsonProperty("asset")
	private Long asset_id;
	@JsonProperty("cumulativePayments")
	private Float cumulativePayments;
	@JsonProperty("transactions")
	private List<TransactionDTO> transactions;

	public Float getCumulativePayments() {
		return cumulativePayments;
	}

	public void setCumulativePayments(Float cumulativePayments) {
		this.cumulativePayments = cumulativePayments;
	}

	public Long getAssetId() {
		return asset_id;
	}

	public void setAssetId(Long asset_id) {
		this.asset_id = asset_id;
	}

	public List<TransactionDTO> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TransactionDTO> transactions) {
		this.transactions = transactions;
	}

}
