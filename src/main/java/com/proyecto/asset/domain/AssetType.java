package com.proyecto.asset.domain;

public enum AssetType {
	BOND("BOND"), STOCK("STOCK");

	private String type;

	public String getType() {
		return type;
	}

	private AssetType(String type) {
		this.type = type;
	}
}
