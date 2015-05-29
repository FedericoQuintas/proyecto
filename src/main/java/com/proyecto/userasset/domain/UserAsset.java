package com.proyecto.userasset.domain;

import java.util.Date;
import java.util.List;

public class UserAsset {
	protected String description;
	protected int quantity;
	protected Long pricePaid;
	protected Date tradingDate;
	protected Long cumulativeYieldsOrDividends;
	protected Long lastTradingPrice;
	protected Currency priceCurrency, dividendOrYieldCurrency;
	
	protected List<TradingSession> historicalTradingSessions;
	
	/**
	 * Return the asset's total revenue(market value + cumulative dividends or yields) in ARS
	 * @return
	 */
	public Long getTotalRevenue(){
		return dividendOrYieldCurrency.convertToARS(cumulativeYieldsOrDividends) 
				+ getMarketValue();
	}
	
	/**
	 * Return the asset's market value in ARS
	 * @return
	 */
	public Long getMarketValue(){
		return priceCurrency.convertToARS(lastTradingPrice - pricePaid);
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Long getPricePaid() {
		return pricePaid;
	}

	public void setPricePaid(Long pricePaid) {
		this.pricePaid = pricePaid;
	}

	public Date getTradingDate() {
		return tradingDate;
	}

	public void setTradingDate(Date tradingDate) {
		this.tradingDate = tradingDate;
	}

	public Long getCumulativeYieldsOrDividends() {
		return cumulativeYieldsOrDividends;
	}

	public void setCumulativeYieldsOrDividends(Long cumulativeYieldsOrDividends) {
		this.cumulativeYieldsOrDividends = cumulativeYieldsOrDividends;
	}

	public Long getLastTradingPrice() {
		return lastTradingPrice;
	}

	public void setLastTradingPrice(Long lastTradingPrice) {
		this.lastTradingPrice = lastTradingPrice;
	}

	public List<TradingSession> getHistoricalTradingSessions() {
		return historicalTradingSessions;
	}

	public void setHistoricalTradingSessions(
			List<TradingSession> historicalTradingSessions) {
		this.historicalTradingSessions = historicalTradingSessions;
	}
	
	
}
