package com.proyecto.asset.domain.factory;

import com.proyecto.asset.domain.Asset;
import com.proyecto.rest.resource.asset.dto.AssetDTO;

public class AssetFactory {

	public static Asset create(AssetDTO assetDTO, Long id) {

		return new Asset(id);
	}

}
