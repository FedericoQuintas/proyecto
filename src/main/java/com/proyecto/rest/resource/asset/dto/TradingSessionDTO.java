package com.proyecto.rest.resource.asset.dto;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class TradingSessionDTO {

	@JsonProperty("closingPrice")
	private Long closingPrice;
	@JsonProperty("openingPrice")
	private Long openingPrice;
	@JsonProperty("maxPrice")
	private Long maxPrice;
	@JsonProperty("minPrice")
	private Long minPrice;
	@JsonProperty("tradingDate")
	private Date tradingDate;
	@JsonProperty("volume")
	private Integer volume;
	public Long getClosingPrice() {
		return closingPrice;
	}
	public void setClosingPrice(Long closingPrice) {
		this.closingPrice = closingPrice;
	}
	public Long getOpeningPrice() {
		return openingPrice;
	}
	public void setOpeningPrice(Long openingPrice) {
		this.openingPrice = openingPrice;
	}
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
