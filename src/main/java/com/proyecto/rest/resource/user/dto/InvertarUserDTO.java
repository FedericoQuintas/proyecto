package com.proyecto.rest.resource.user.dto;

import org.codehaus.jackson.annotate.JsonProperty;

public class InvertarUserDTO {

	@JsonProperty("id")
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;

	}

}
