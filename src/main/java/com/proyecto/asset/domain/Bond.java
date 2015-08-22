package com.proyecto.asset.domain;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.mongojack.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.common.currency.InvertarCurrency;

public class Bond extends Asset {
	@MongoId
	@MongoObjectId
	public String objectId;
	
	private boolean isPesified = true;
	
	public Bond(){
		
	}
	public Bond(Long id, String description, String ticker,
			InvertarCurrency invertarCurrency) {
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
}
