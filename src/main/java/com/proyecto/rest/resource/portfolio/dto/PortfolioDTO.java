package com.proyecto.rest.resource.portfolio.dto;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.proyecto.rest.resource.usserasset.dto.UserAssetDTO;

public class PortfolioDTO {

	@JsonProperty("id")
	private Long id;
	@JsonProperty("currentAssets")
	private List<UserAssetDTO> currentAssets;
	@JsonProperty("name")
	private String name;
	@JsonProperty("market_value")
	private Long marketValue;

	public void setMarketValue(Long marketValue) {
		this.marketValue = marketValue;
	}

	public String getName() {
		return name;
	}

	public Long getId() {
		return this.id;
	}

	public List<UserAssetDTO> getCurrentAssets() {
		return this.currentAssets;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCurrentAssets(List<UserAssetDTO> currentAssets) {
		this.currentAssets = currentAssets;
	}

	public Long getMarketValue() {
		return this.marketValue;
	}

}
