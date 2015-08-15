package com.proyecto.inflation.domain;

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

public class Inflation {

	@MongoId
	@MongoObjectId
	public String objectId;

	private static int SCALE = 10;

	private Long id;

	private NavigableMap<Long, InflationSession> inflationSessions;

	public NavigableMap<Long, InflationSession> getInflationSessions() {
		return inflationSessions;
	}

	public Inflation() {
	}

	public Long getId() {
		return id;
	}

	public Inflation(Long id) {
		this.id = id;
		this.inflationSessions = new TreeMap<Long, InflationSession>();
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

	public void addExchangeSession(InflationSession exchangeSession) {
		this.inflationSessions.put(exchangeSession.getDate().getTime(),
				exchangeSession);
	}

	public Map<Long, Double> getPercentageOfChange(Date startDate, Date endDate) {

		NavigableMap<Long, InflationSession> selectedExchangeSessions = this
				.getInflationSessions().subMap(startDate.getTime(), false,
						endDate.getTime(), true);

		BigDecimal initialClosingPrice = BigDecimal.valueOf(
				this.getInflationSessions().ceilingEntry(startDate.getTime())
						.getValue().getAmount()).setScale(SCALE);

		Map<Long, Double> resultantPercentagesOfChange = new HashMap<Long, Double>();
		resultantPercentagesOfChange.put(startDate.getTime(), 0d);

		for (Long currentDate : selectedExchangeSessions.navigableKeySet()) {
			BigDecimal currentClosingPrice = BigDecimal.valueOf(
					selectedExchangeSessions.get(currentDate).getAmount())
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

	public Collection<InflationSession> getRangeOfInflationSessions(
			Date startDate, Date endDate) {
		return this.getInflationSessions()
				.subMap(startDate.getTime(), true, endDate.getTime(), true)
				.values();
	}

}