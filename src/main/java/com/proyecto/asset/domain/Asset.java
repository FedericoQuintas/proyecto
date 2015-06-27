package com.proyecto.asset.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import com.proyecto.asset.domain.factory.TradingSessionFactory;
import com.proyecto.asset.exception.InvalidTradingSessionArgumentException;
import com.proyecto.rest.resource.asset.dto.TradingSessionDTO;

public class Asset {

	private Long id;
	private String description;
	private NavigableMap<Long, TradingSession> tradingSessions;
	private Long lastTradingPrice;
	private String ticker;
	private String industry;
	private static int SCALE = 10;

	public Asset(Long id, String description, String ticker) {
		this.id = id;
		this.description = description;
		this.ticker = ticker;
		tradingSessions = new TreeMap<Long, TradingSession>();
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

	public void addTradingSession(TradingSessionDTO tradingSessionDTO) 
			throws InvalidTradingSessionArgumentException {

		TradingSession tradingSession = TradingSessionFactory
				.create(tradingSessionDTO);

		this.tradingSessions.put(tradingSession.getTradingDate().getTime(), tradingSession);

	}

	public NavigableMap<Long, TradingSession> getTradingSessions() {
		return this.tradingSessions;
	}

	public void setLastTradingPrice(Long lastTradingPrice) {
		this.lastTradingPrice = lastTradingPrice;

	}

	public Long getLastTradingPrice() {
		return lastTradingPrice;
	}
	
	public Map<Long,Double> getPercentageOfChange(Date startDate,Date endDate){
		
		NavigableMap<Long,TradingSession> selectedTradingSessions = 
				this.getTradingSessions().subMap(startDate.getTime(), false, endDate.getTime(), true);
		
		BigDecimal initialClosingPrice = BigDecimal.valueOf(
				this.getTradingSessions().get(startDate.getTime()).getClosingPrice()).setScale(SCALE);

		Map<Long,Double> resultantPercentagesOfChange = new HashMap<Long,Double>();
		resultantPercentagesOfChange.put(startDate.getTime(), 0d);
		
		for(Long currentDate : selectedTradingSessions.navigableKeySet()){
			BigDecimal currentClosingPrice = BigDecimal.valueOf(
					selectedTradingSessions.get(currentDate).getClosingPrice()).setScale(SCALE);
			resultantPercentagesOfChange.put(
					currentDate, 
					currentClosingPrice
						.subtract(initialClosingPrice)
						.divide(initialClosingPrice, SCALE, RoundingMode.DOWN)
						.multiply(BigDecimal.valueOf(100L))
						.setScale(2, RoundingMode.DOWN)
						.doubleValue());
		}
		
		return resultantPercentagesOfChange;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

}
