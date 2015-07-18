package com.proyecto.rest.resource.user.dto;

import java.util.HashMap;
import java.util.Map;

public class TheoricalPortfolioDTO {

	private Map<String,Integer> assetTypeAndPercentage = new HashMap<String,Integer>();

	public Map<String, Integer> getAssetTypeAndPercentage() {
		return assetTypeAndPercentage;
	}
	
}
