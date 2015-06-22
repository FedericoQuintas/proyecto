package com.proyecto.unit.asset.helper;

import com.proyecto.rest.resource.asset.dto.AssetDTO;
import com.proyecto.rest.resource.asset.dto.TradingSessionDTO;

public class AssetHelper {

	public static final String DEFAULT_DESCRIPTION = "Asset default description";
	public static final String DEFAULT_TICKER = "INTC";
	public static final String DEFAULT_INDUSTRY = "Automotriz";

	public static AssetDTO createDefaultAssetDTO() {

		AssetDTO assetDTO = new AssetDTO();

		assetDTO.setDescription(DEFAULT_DESCRIPTION);
		assetDTO.setTicker(DEFAULT_TICKER);
		assetDTO.setIndustry(DEFAULT_INDUSTRY);

		return assetDTO;
	}

	public static TradingSessionDTO createDefaultTradingSession() {
		return new TradingSessionDTO();
	}

}
