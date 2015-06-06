package com.proyecto.rest.resource.asset.dto.factory;

import com.proyecto.asset.domain.Asset;
import com.proyecto.rest.resource.asset.dto.AssetDTO;

public class AssetDTOFactory {

	public static AssetDTO create(Asset asset) {

		AssetDTO assetDTO = new AssetDTO();

		assetDTO.setId(asset.getId());

		return assetDTO;
	}

}
