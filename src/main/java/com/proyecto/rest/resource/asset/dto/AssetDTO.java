package com.proyecto.rest.resource.asset.dto;

import org.codehaus.jackson.annotate.JsonProperty;

public class AssetDTO {

	@JsonProperty("id")
	private Long id;

	@JsonProperty("description")
	private String description;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;

	}

}
