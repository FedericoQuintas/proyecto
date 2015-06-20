package com.proyecto.user.domain;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {

	private Long id;
	private Long marketValue;
	private String name;
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
	}

	public Long getId() {
		return this.id;
	}

	public List<UserAsset> getUserAssets() {
		return userAssets;
	}

	public void setUserAssets(List<UserAsset> userAssets) {
		this.userAssets = userAssets;
	}

}
