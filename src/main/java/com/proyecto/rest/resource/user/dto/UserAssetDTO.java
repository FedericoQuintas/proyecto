package com.proyecto.rest.resource.user.dto;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.proyecto.asset.domain.Asset;

public class UserAssetDTO {

	@JsonProperty("id")
	private Long id;	
	@JsonProperty("asset")
	private Asset asset;
	@JsonProperty("transactions")
	private List<TransactionDTO> transactions;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Asset getAsset() {
		return asset;
	}
	
	public void setAsset(Asset asset) {
		this.asset = asset;
	}
	
	public List<TransactionDTO> getTransactions() {
		return transactions;
	}
	
	public void setTransactions(List<TransactionDTO> transactions) {
		this.transactions = transactions;
	}
	
}
