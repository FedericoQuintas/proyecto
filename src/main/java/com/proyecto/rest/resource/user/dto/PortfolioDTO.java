package com.proyecto.rest.resource.user.dto;

import org.codehaus.jackson.annotate.JsonProperty;

public class PortfolioDTO {

	@JsonProperty("id")
	private Long id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("market_value")
	private Long marketValue;
	@JsonProperty("performance")
	private Double performance;

	public void setPerformance(Double performance) {
		this.performance = performance;
	}

	public void setMarketValue(Long marketValue) {
		this.marketValue = marketValue;
	}

	public String getName() {
		return name;
	}

	public Long getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMarketValue() {
		return this.marketValue;
	}

	public Double getPerformance() {
		return this.performance;
	}

}
