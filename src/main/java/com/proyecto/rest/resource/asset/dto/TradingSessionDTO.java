package com.proyecto.rest.resource.asset.dto;

import org.codehaus.jackson.annotate.JsonProperty;

public class TradingSessionDTO {

	@JsonProperty("closingPrice")
	private Double closingPrice;
	@JsonProperty("openingPrice")
	private Double openingPrice;
	@JsonProperty("maxPrice")
	private Double maxPrice;
	@JsonProperty("minPrice")
	private Double minPrice;
	@JsonProperty("tradingDate")
	private Long tradingDate;
	@JsonProperty("volume")
	private Integer volume;

	public Double getClosingPrice() {
		return closingPrice;
	}

	public void setClosingPrice(Double closingPrice) {
		this.closingPrice = closingPrice;
	}

	public Double getOpeningPrice() {
		return openingPrice;
	}

	public void setOpeningPrice(Double openingPrice) {
		this.openingPrice = openingPrice;
	}

	public Double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public Double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	public Long getTradingDate() {
		return tradingDate;
	}

	public void setTradingDate(Long tradingDate) {
		this.tradingDate = tradingDate;
	}

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

}
