package com.proyecto.portfolio.domain;

import java.util.List;

import com.proyecto.userasset.domain.UserAsset;

public class Portfolio {

	private Long id;
	private String portfolioName;
	private Long marketValue;
	private List<UserAsset> currentAssets;
	private List<UserAsset> historicalAssets;
	
	public Long getMarketValue(){
		return marketValue;
	}

	public Portfolio(Long portfolioID) {
		this.id = portfolioID;
	}

	public Long getId() {
		return this.id;
	}

	public String getPortfolioName() {
		return portfolioName;
	}

	public List<UserAsset> getCurrentAssets() {
		return currentAssets;
	}

	public List<UserAsset> getHistoricalAssets() {
		return historicalAssets;
	}

}
