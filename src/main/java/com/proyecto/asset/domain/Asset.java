package com.proyecto.asset.domain;

import java.util.ArrayList;
import java.util.List;

import com.proyecto.asset.domain.factory.TradingSessionFactory;
import com.proyecto.rest.resource.asset.dto.TradingSessionDTO;
import com.proyecto.userasset.domain.TradingSession;

public class Asset {

	private Long id;
	private String description;
	private List<TradingSession> tradingSessions;

	public Asset(Long id, String description) {
		this.id = id;
		this.description = description;
		tradingSessions = new ArrayList<TradingSession>();
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

}
