package com.proyecto.user.domain;


public class Portfolio {

	private Long id;
	private String portfolioName;
	private Long marketValue;
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
		this.marketValue = new Long(0);
	}

	public Long getId() {
		return this.id;
	}

	public String getPortfolioName() {
		return portfolioName;
	}

}
