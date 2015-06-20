package com.proyecto.rest.resource.user.dto;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class UserAssetDTO {

	@JsonProperty("id")
	private Long id;
	@JsonProperty("asset")
	private long asset_id;
	@JsonProperty("cumulativePayments")
	private float cumulativePayments;
	@JsonProperty("transactions")
	private List<TransactionDTO> transactions;

	public float getCumulativePayments() {
		return cumulativePayments;
	}

	public void setCumulativePayments(float cumulativePayments) {
		this.cumulativePayments = cumulativePayments;
	}

	public long getAsset_id() {
		return asset_id;
	}

	public void setAsset_id(long asset_id) {
		this.asset_id = asset_id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<TransactionDTO> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TransactionDTO> transactions) {
		this.transactions = transactions;
	}

}
