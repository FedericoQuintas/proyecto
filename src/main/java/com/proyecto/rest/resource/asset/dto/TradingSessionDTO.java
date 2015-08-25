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
	@JsonProperty("payment")
	private Double payment;
	
	@JsonProperty("sma_7")
	private Double sma_7;
	@JsonProperty("sma_21")
	private Double sma_21;
	@JsonProperty("sma_50")
	private Double sma_50;
	@JsonProperty("sma_200")
	private Double sma_200;

	@JsonProperty("ema_7")
	private Double ema_7;
	@JsonProperty("ema_21")
	private Double ema_21;
	@JsonProperty("ema_50")
	private Double ema_50;
	@JsonProperty("ema_200")
	private Double ema_200;

	@JsonProperty("momentum_7")
	private Double momentum_7;
	@JsonProperty("momentum_21")
	private Double momentum_21;
	@JsonProperty("momentum_50")
	private Double momentum_50;
	@JsonProperty("momentum_200")
	private Double momentum_200;

	@JsonProperty("rsi_7")
	private Double rsi_7;
	@JsonProperty("rsi_21")
	private Double rsi_21;
	@JsonProperty("rsi_50")
	private Double rsi_50;
	@JsonProperty("rsi_200")
	private Double rsi_200;

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

	public Double getPayment() {
		return payment;
	}
	public void setPayment(Double payment) {
		this.payment = payment;
	}

	public Double getSma_7() {
		return sma_7;
	}

	public void setSma_7(Double sma_7) {
		this.sma_7 = sma_7;
	}

	public Double getSma_21() {
		return sma_21;
	}

	public void setSma_21(Double sma_21) {
		this.sma_21 = sma_21;
	}

	public Double getSma_50() {
		return sma_50;
	}

	public void setSma_50(Double sma_50) {
		this.sma_50 = sma_50;
	}

	public Double getSma_200() {
		return sma_200;
	}

	public void setSma_200(Double sma_200) {
		this.sma_200 = sma_200;
	}

	public Double getEma_7() {
		return ema_7;
	}

	public void setEma_7(Double ema_7) {
		this.ema_7 = ema_7;
	}

	public Double getEma_21() {
		return ema_21;
	}

	public void setEma_21(Double ema_21) {
		this.ema_21 = ema_21;
	}

	public Double getEma_50() {
		return ema_50;
	}

	public void setEma_50(Double ema_50) {
		this.ema_50 = ema_50;
	}

	public Double getEma_200() {
		return ema_200;
	}

	public void setEma_200(Double ema_200) {
		this.ema_200 = ema_200;
	}

	public Double getMomentum_7() {
		return momentum_7;
	}

	public void setMomentum_7(Double momentum_7) {
		this.momentum_7 = momentum_7;
	}

	public Double getMomentum_21() {
		return momentum_21;
	}

	public void setMomentum_21(Double momentum_21) {
		this.momentum_21 = momentum_21;
	}

	public Double getMomentum_50() {
		return momentum_50;
	}

	public void setMomentum_50(Double momentum_50) {
		this.momentum_50 = momentum_50;
	}

	public Double getMomentum_200() {
		return momentum_200;
	}

	public void setMomentum_200(Double momentum_200) {
		this.momentum_200 = momentum_200;
	}

	public Double getRsi_7() {
		return rsi_7;
	}

	public void setRsi_7(Double rsi_7) {
		this.rsi_7 = rsi_7;
	}

	public Double getRsi_21() {
		return rsi_21;
	}

	public void setRsi_21(Double rsi_21) {
		this.rsi_21 = rsi_21;
	}

	public Double getRsi_50() {
		return rsi_50;
	}

	public void setRsi_50(Double rsi_50) {
		this.rsi_50 = rsi_50;
	}

	public Double getRsi_200() {
		return rsi_200;
	}

	public void setRsi_200(Double rsi_200) {
		this.rsi_200 = rsi_200;
	}
}
