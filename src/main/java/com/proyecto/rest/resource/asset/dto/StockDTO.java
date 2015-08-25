package com.proyecto.rest.resource.asset.dto;

import org.codehaus.jackson.annotate.JsonProperty;

public class StockDTO extends AssetDTO {

	@JsonProperty("industry")
	private String industry;
	
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

}
