package com.proyecto.asset.domain;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.mongojack.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.common.currency.InvertarCurrencyCode;

public class Bond extends Asset {
	@MongoId
	@MongoObjectId
	public String objectId;

	private boolean dollarLinked = true;

	public Bond(Long id, String description, String ticker,
			InvertarCurrencyCode invertarCurrency, String name) {
		super(id, description, ticker, invertarCurrency, name);
	}

	public Bond() {

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

	public boolean isDollarLinked() {
		return dollarLinked;
	}

	public void setDollarLinked(boolean isPesified) {
		this.dollarLinked = isPesified;
	}
}
