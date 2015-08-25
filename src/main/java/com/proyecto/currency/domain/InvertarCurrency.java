package com.proyecto.currency.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.mongojack.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InvertarCurrency {

	@MongoId
	@MongoObjectId
	public String objectId;

	private static int SCALE = 10;

	private Long id;

	private String code;

	private NavigableMap<Long, ExchangeSession> exchangeSessions;

	public NavigableMap<Long, ExchangeSession> getExchangeSessions() {
		return exchangeSessions;
	}

	public InvertarCurrency() {
	}

	public Long getId() {
		return id;
	}

	public InvertarCurrency(Long id, String code) {
		this.id = id;
		this.code = code;
		this.exchangeSessions = new TreeMap<Long, ExchangeSession>();
	}

	public String getCode() {
		return code;
	}

	@ObjectId
	@JsonProperty("_id")
	public String getObjectId() {
		return this.objectId;
	}

	@ObjectId
	@JsonProperty("_id")
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public void addExchangeSession(ExchangeSession exchangeSession) {
		this.exchangeSessions.put(exchangeSession.getDate().getTime(),
				exchangeSession);
	}

	public Map<Long, Double> getPercentageOfChange(Date startDate, Date endDate) {

		NavigableMap<Long, ExchangeSession> selectedExchangeSessions = this
				.getExchangeSessions().subMap(startDate.getTime(), false,
						endDate.getTime(), true);

		BigDecimal initialClosingPrice = BigDecimal.valueOf(
				this.getExchangeSessions().ceilingEntry(startDate.getTime())
						.getValue().getPrice()).setScale(SCALE);

		Map<Long, Double> resultantPercentagesOfChange = new HashMap<Long, Double>();
		resultantPercentagesOfChange.put(startDate.getTime(), 0d);

		for (Long currentDate : selectedExchangeSessions.navigableKeySet()) {
			BigDecimal currentClosingPrice = BigDecimal.valueOf(
					selectedExchangeSessions.get(currentDate).getPrice())
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

	public Collection<ExchangeSession> getRangeOfTradingSessions(
			Date startDate, Date endDate) {
		return this.getExchangeSessions()
				.subMap(startDate.getTime(), true, endDate.getTime(), true)
				.values();
	}

}