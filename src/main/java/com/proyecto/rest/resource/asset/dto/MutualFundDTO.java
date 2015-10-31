package com.proyecto.rest.resource.asset.dto;

import org.codehaus.jackson.annotate.JsonProperty;

public class MutualFundDTO extends AssetDTO {

	@JsonProperty("fundType")
	private String fundType;

	public String getFundType() {
		return fundType;
	}

	public void setFundType(String fundType) {
		this.fundType = fundType;
	}

}
