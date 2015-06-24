package com.proyecto.asset.domain;

import java.util.Date;

public class TradingSession {
	
	private Long closingPrice;
	private Long openingPrice;
	private Long maxPrice;
	private Long minPrice;
	private Date tradingDate;
	private Integer volume;
	
	public Long getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(Long maxPrice) {
		this.maxPrice = maxPrice;
	}
	public Long getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(Long minPrice) {
		this.minPrice = minPrice;
	}	
	public Long getOpeningPrice() {
		return openingPrice;
	}
	public void setOpeningPrice(Long openingPrice) {
		this.openingPrice = openingPrice;
	}
	public Long getClosingPrice() {
		return closingPrice;
	}
	public void setClosingPrice(Long closingPrice) {
		this.closingPrice = closingPrice;
	}	
	public Date getTradingDate() {
		return tradingDate;
	}
	public void setTradingDate(Date tradingDate) {
		this.tradingDate = tradingDate;
	}
	public Integer getVolume() {
		return volume;
	}
	public void setVolume(Integer volume) {
		this.volume = volume;
	}
}
