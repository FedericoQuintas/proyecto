package com.proyecto.rest.resource.portfolio.dto;

import java.util.List;

import com.proyecto.rest.resource.usserasset.dto.UserAssetDTO;

public class PortfolioDTO {

	private Long id;
	private List<UserAssetDTO> currentAssets;
	private String name;

	public String getName() {
		return name;
	}

	public Long getId() {
		return this.id;
	}

	public List<UserAssetDTO> getCurrentAssets() {
		return this.currentAssets;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCurrentAssets(List<UserAssetDTO> currentAssets) {
		this.currentAssets = currentAssets;
	}

}
