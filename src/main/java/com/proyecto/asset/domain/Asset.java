package com.proyecto.asset.domain;

public class Asset {

	private Long id;
	private String description;

	public Asset(Long id, String description) {
		this.id = id;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return this.id;
	}

}
