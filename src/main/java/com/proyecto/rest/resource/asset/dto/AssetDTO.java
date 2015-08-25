package com.proyecto.rest.resource.asset.dto;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.proyecto.common.currency.InvertarCurrencyCode;

public abstract class AssetDTO {

	@JsonProperty("id")
	private Long id;

	@JsonProperty("description")
	private String description;

	@JsonProperty("name")
	private String name;
	
	@JsonProperty("isLeader")
	private boolean isLeader = false;

	@JsonProperty("trading_sessions")
	private List<TradingSessionDTO> tradingSessions = new ArrayList<TradingSessionDTO>();

	@JsonProperty("last_trading_price")
	private Float lastTradingPrice;

	@JsonProperty("ticker")
	private String ticker;

	@JsonProperty("currency")
	private InvertarCurrencyCode currency;

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

	public Float getLastTradingPrice() {
		return this.lastTradingPrice;
	}

	public void setLastTradingPrice(Float lastTradingPrice) {
		this.lastTradingPrice = lastTradingPrice;

	}

	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public void setCurrency(InvertarCurrencyCode currency) {
		this.currency = currency;

	}

	public InvertarCurrencyCode getCurrency() {
		return currency;
	}

	public boolean isLeader() {
		return isLeader;
	}

	public void setLeader(boolean isLeader) {
		this.isLeader = isLeader;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
		

}
