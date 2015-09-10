package com.proyecto.rest.resource.asset.dto;

import org.codehaus.jackson.annotate.JsonProperty;

public class BondDTO extends AssetDTO {
	@JsonProperty("dollarLinked")
	private boolean dollarLinked;

	public boolean isDollarLinked() {
		return dollarLinked;
	}

	public void setDollarLinked(boolean dollarLinked) {
		this.dollarLinked = dollarLinked;
	}
}
