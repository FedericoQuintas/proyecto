package com.proyecto.rest.resource.asset.dto;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class AssetDTO {

	@JsonProperty("id")
	private Long id;

	@JsonProperty("description")
	private String description;

	@JsonProperty("trading_sessions")
	private List<TradingSessionDTO> tradingSessions;

	@JsonProperty("last_trading_price")
	private Long lastTradingPrice;

	public List<TradingSessionDTO> getTradingSessions() {
		return tradingSessions;
	}

	public void setTradingSessions(List<TradingSessionDTO> tradingSessions) {
		this.tradingSessions = tradingSessions;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;

	}

	public Long getLastTradingPrice() {
		return this.lastTradingPrice;
	}

	public void setLastTradingPrice(Long lastTradingPrice) {
		this.lastTradingPrice = lastTradingPrice;

	}

}