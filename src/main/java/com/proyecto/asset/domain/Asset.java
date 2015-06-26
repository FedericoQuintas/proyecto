package com.proyecto.asset.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.proyecto.asset.domain.factory.TradingSessionFactory;
import com.proyecto.rest.resource.asset.dto.TradingSessionDTO;

import org.apache.commons.lang3.time.DateUtils;

public class Asset {

	private Long id;
	private String description;
	private List<TradingSession> tradingSessions;
	private Long lastTradingPrice;
	private String ticker;
	

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
	
	public Map<Date,Double> getPercentageOfChange(Date startDate,Date endDate){
		
		Map<Date,TradingSession> selectedTradingSessions = new HashMap<Date,TradingSession>();
		
		for(TradingSession tradingSession : this.getTradingSessions() ){
			if((tradingSession.getTradingDate().after(startDate) || tradingSession.getTradingDate().equals(startDate))
			&& (tradingSession.getTradingDate().before(endDate)|| tradingSession.getTradingDate().equals(startDate))){
				selectedTradingSessions.put(tradingSession.getTradingDate(), tradingSession);
			}
		}
		
		Double initialClosingPrice = selectedTradingSessions.get(startDate).getClosingPrice();
		Date currentDate = startDate;
		Map<Date,Double> resultantPercentagesOfChange = new HashMap<Date,Double>();
		resultantPercentagesOfChange.put(startDate, 0d);
		
		while(currentDate.getTime()<=endDate.getTime()){
			Double previousClosingPrice = selectedTradingSessions.get(currentDate).getClosingPrice();
			currentDate = DateUtils.addDays(currentDate, 1);
			Double currentClosingPrice =selectedTradingSessions.get(currentDate).getClosingPrice();
			resultantPercentagesOfChange.put(currentDate, ((currentClosingPrice - initialClosingPrice)/previousClosingPrice)*100);
		}
		
		return resultantPercentagesOfChange;
	}

}
