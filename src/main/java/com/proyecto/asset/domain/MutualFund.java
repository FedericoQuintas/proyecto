package com.proyecto.asset.domain;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.mongojack.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.common.currency.InvertarCurrencyCode;

public class MutualFund extends Asset{
	@MongoId
	@MongoObjectId
	public String objectId;
	
	private String fundType;
	
	public MutualFund() {
	}
	
	public MutualFund(Long id, String description, String ticker,
			InvertarCurrencyCode invertarCurrency, String name) {
		super(id, description, ticker, invertarCurrency, name);
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

	public String getFundType() {
		return fundType;
	}

	public void setFundType(String fundType) {
		this.fundType = fundType;
	}
	

}
