package com.proyecto.asset.domain;

import java.util.ArrayList;
import java.util.List;

import com.proyecto.asset.domain.factory.TradingSessionFactory;
import com.proyecto.rest.resource.asset.dto.TradingSessionDTO;

public class Asset {

	private Long id;
	private String description;
	private List<TradingSession> tradingSessions;
	private Long lastTradingPrice;
	private String ticker;
	private String industry;

	public Asset(Long id, String description, String ticker) {
		this.id = id;
		this.description = description;
		this.ticker = ticker;
		tradingSessions = new ArrayList<TradingSession>();
	}

	public String getTicker() {
		return ticker;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return this.id;
	}

	public void addTradingSession(TradingSessionDTO tradingSessionDTO) {

		TradingSession tradingSession = TradingSessionFactory
				.create(tradingSessionDTO);

		this.tradingSessions.add(tradingSession);

	}

	public List<TradingSession> getTradingSessions() {
		return this.tradingSessions;
	}

	public void setLastTradingPrice(Long lastTradingPrice) {
		this.lastTradingPrice = lastTradingPrice;

	}

	public Long getLastTradingPrice() {
		return lastTradingPrice;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

}
