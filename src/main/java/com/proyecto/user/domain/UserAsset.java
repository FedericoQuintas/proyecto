package com.proyecto.user.domain;

import java.util.ArrayList;
import java.util.List;

import com.proyecto.asset.domain.Asset;

public class UserAsset {
	
	private Long id;
	private Asset asset;
	private List<Transaction> transactions = new ArrayList<Transaction>();
	
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
	
	public List<Transaction> getTransactions() {
		return transactions;
	}
	
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	
}
