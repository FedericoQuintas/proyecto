package com.proyecto.asset.domain;

import java.util.ArrayList;
import java.util.List;

import com.proyecto.asset.domain.factory.TradingSessionFactory;
import com.proyecto.common.currency.InvertarCurrency;
import com.proyecto.rest.resource.asset.dto.TradingSessionDTO;

public class Asset {

	private Long id;
	private String description;
	private List<TradingSession> tradingSessions;
	private Float lastTradingPrice;
	private String ticker;
	private String industry;
	private InvertarCurrency currency;

	public Asset(Long id, String description, String ticker, InvertarCurrency invertarCurrency) {
		this.id = id;
		this.description = description;
		this.ticker = ticker;
		this.currency = invertarCurrency;
		tradingSessions = new ArrayList<TradingSession>();
	}

	public InvertarCurrency getCurrency() {
		return currency;
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

	public void setLastTradingPrice(Float lastTradingPrice) {
		this.lastTradingPrice = lastTradingPrice;

	}

	public Float getLastTradingPrice() {
		return lastTradingPrice;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

}
