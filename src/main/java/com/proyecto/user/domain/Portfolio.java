package com.proyecto.user.domain;

public class Portfolio {

	private Long id;
	private Long marketValue;
	private String name;
	private Double performance;

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

}
