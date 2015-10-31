package com.proyecto.asset.domain;

public enum AssetType {
	BOND("BOND"), STOCK("STOCK"), MUTUALFUND("MUTUAL_FUND");

	private String type;

	public String getType() {
		return type;
	}

	private AssetType(String type) {
		this.type = type;
	}
}
