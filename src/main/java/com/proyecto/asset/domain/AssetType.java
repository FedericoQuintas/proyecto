package com.proyecto.asset.domain;

public enum AssetType {
	BOND("BOND"), STOCK("STOCK"), MUTUALFUND("MUTUAL_FUND"), CURRENCY("CURRENCY");

	private String type;

	public String getType() {
		return type;
	}

	private AssetType(String type) {
		this.type = type;
	}
}
