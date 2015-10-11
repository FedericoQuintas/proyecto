package com.proyecto.asset.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import com.proyecto.asset.domain.factory.TradingSessionFactory;
import com.proyecto.asset.exception.InvalidTradingSessionArgumentException;
import com.proyecto.common.currency.InvertarCurrencyCode;
import com.proyecto.rest.resource.asset.dto.TradingSessionDTO;

public abstract class Asset {

	private Long id;
	private String description;
	private Float lastTradingPrice;
	private String ticker;
	private String name;
	private InvertarCurrencyCode currency;
	private NavigableMap<Long, TradingSession> tradingSessions;
	private String type;
	public String getType() {
		return type;
	}

	private static int SCALE = 10;

	// TODO: Agregar campo de suma de pagos o dividendos una vez que las
	// TradingSessions tengan la logica necesaria

	public Asset(Long id, String description, String ticker,
			InvertarCurrencyCode invertarCurrency, String name) {
		this.id = id;
		this.description = description;
		this.ticker = ticker;
		this.currency = invertarCurrency;
		this.name = name;
		tradingSessions = new TreeMap<Long, TradingSession>();
	}

	public Asset() {
	}

	public InvertarCurrencyCode getCurrency() {
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

	public void addTradingSession(TradingSessionDTO tradingSessionDTO)
			throws InvalidTradingSessionArgumentException {

		TradingSession tradingSession = TradingSessionFactory
				.create(tradingSessionDTO);

		this.tradingSessions.put(tradingSession.getTradingDate().getTime(),
				tradingSession);

	}

	public NavigableMap<Long, TradingSession> getTradingSessions() {
		return this.tradingSessions;
	}

	public void setLastTradingPrice(Float lastTradingPrice) {
		this.lastTradingPrice = lastTradingPrice;

	}

	public Float getLastTradingPrice() {
		return lastTradingPrice;
	}

	public Map<Long, Double> getPercentageOfChange(Date startDate, Date endDate) {

		NavigableMap<Long, TradingSession> selectedTradingSessions = this
				.getTradingSessions().subMap(startDate.getTime(), false,
						endDate.getTime(), true);

		BigDecimal initialClosingPrice = BigDecimal.valueOf(
				this.getTradingSessions().ceilingEntry(startDate.getTime())
						.getValue().getClosingPrice()).setScale(SCALE);

		Map<Long, Double> resultantPercentagesOfChange = new HashMap<Long, Double>();
		resultantPercentagesOfChange.put(startDate.getTime(), 0d);

		for (Long currentDate : selectedTradingSessions.navigableKeySet()) {
			BigDecimal currentClosingPrice = BigDecimal.valueOf(
					selectedTradingSessions.get(currentDate).getClosingPrice())
					.setScale(SCALE);
			resultantPercentagesOfChange.put(
					currentDate,
					currentClosingPrice
							.subtract(initialClosingPrice)
							.divide(initialClosingPrice, SCALE,
									RoundingMode.DOWN)
							.multiply(BigDecimal.valueOf(100L))
							.setScale(2, RoundingMode.DOWN).doubleValue());
		}

		return resultantPercentagesOfChange;
	}

	public Collection<TradingSession> getRangeOfTradingSessions(Date startDate,
			Date endDate) {
		return this.getTradingSessions()
				.subMap(startDate.getTime(), true, endDate.getTime(), true)
				.values();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Double calculateLastTradingSessionVariation() {
		//la ultima entrada en la lista de trading sessions es la mas reciente(por su timestamp)
		if (this.tradingSessions.isEmpty()) {
			return null;
		}
		return this.tradingSessions.get(this.tradingSessions.lastKey()).getTradingSessionVariation();
	}
	
	public void resolveTradingSessionsVariations() {
		Double previousClosingPrice = null;
		for (Long key: this.tradingSessions.keySet()) {
			TradingSession tradingSession = this.tradingSessions.get(key);
			tradingSession.setTradingSessionVariation(previousClosingPrice);
			previousClosingPrice = tradingSession.getClosingPrice();
		}
	}
}
