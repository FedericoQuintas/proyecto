package com.proyecto.unit.asset.helper;

import com.proyecto.rest.resource.asset.dto.AssetDTO;

public class AssetHelper {

	private static final String DEFAULT_DESCRIPTION = "Asset default description";

	public static AssetDTO createDefaultAssetDTO() {

		AssetDTO assetDTO = new AssetDTO();

		assetDTO.setDescription(DEFAULT_DESCRIPTION);

		return assetDTO;
	}

}
