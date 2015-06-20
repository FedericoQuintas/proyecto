package com.proyecto.user.domain;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {

	private Long id;
	private Long marketValue;
	private String name;
	private Double performance;
	private Double lastSessionPerformance;
	private List<UserAsset> userAssets = new ArrayList<UserAsset>();

	public String getName() {
		return name;
	}

	public Long getMarketValue() {
		return marketValue;
	}

	public Portfolio(Long portfolioID, String name) {
		this.id = portfolioID;
		this.name = name;
		this.marketValue = new Long(0);
		this.performance = new Double(0);
		this.lastSessionPerformance = new Double(0);
	}

	public Double getLastSessionPerformance() {
		return lastSessionPerformance;
	}

	public Long getId() {
		return this.id;
	}

	public Double getPerformance() {
		return this.performance;
	}

	public void setPerformance(Double performance) {
		this.performance = performance;
	}

	public List<UserAsset> getUserAssets() {
		return userAssets;
	}

	public void setUserAssets(List<UserAsset> userAssets) {
		this.userAssets = userAssets;
	}

}
