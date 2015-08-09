package com.proyecto.currency.domain;

import java.util.ArrayList;
import java.util.List;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.mongojack.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.common.currency.InvertarCurrencyCode;

public class InvertarCurrency {

	@MongoId
	@MongoObjectId
	public String objectId;

	private Long id;

	private InvertarCurrencyCode code;

	private List<ExchangeSession> exchangeSessions;

	public List<ExchangeSession> getExchangeSessions() {
		return exchangeSessions;
	}

	public InvertarCurrency() {
	}

	public Long getId() {
		return id;
	}

	public InvertarCurrency(Long id, InvertarCurrencyCode code) {
		this.id = id;
		this.code = code;
		this.exchangeSessions = new ArrayList<ExchangeSession>();
	}

	public InvertarCurrencyCode getCode() {
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

}