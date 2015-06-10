package com.proyecto.portfolio.domain;

import java.util.ArrayList;
import java.util.List;

import com.proyecto.userasset.domain.UserAsset;

public class Portfolio {

	private Long id;
	private String portfolioName;
	private Long marketValue;
	private List<UserAsset> currentAssets;
	private List<UserAsset> historicalAssets;
	private String name;

	public String getName() {
		return name;
	}

	public Long getMarketValue() {
		return marketValue;
	}

	public Portfolio(Long portfolioID, String name) {
		this.id = portfolioID;
		this.name = name;
		this.currentAssets = new ArrayList<>();
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
