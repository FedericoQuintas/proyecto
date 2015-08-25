package com.proyecto.asset.domain;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.mongojack.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.common.currency.InvertarCurrencyCode;

public class Stock extends Asset {
	@MongoId
	@MongoObjectId
	public String objectId;
	
	private String industry;
	private boolean isLeader = false;
	
	public Stock(){
		
	}
	
	public Stock(Long id, String description, String ticker,
			InvertarCurrencyCode invertarCurrency) {
		super(id, description, ticker, invertarCurrency);
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

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}
}