package com.proyecto.rest.resource.asset.dto;

import org.codehaus.jackson.annotate.JsonProperty;

public class BondDTO extends AssetDTO {
	@JsonProperty("isPesified")
	private boolean isPesified;

	public boolean isPesified() {
		return isPesified;
	}

	public void setPesified(boolean isPesified) {
		this.isPesified = isPesified;
	}
}
