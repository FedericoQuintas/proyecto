package com.proyecto.asset.domain.factory;

import com.proyecto.asset.domain.CurrencyAsset;
import com.proyecto.rest.resource.asset.dto.CurrencyAssetDTO;

public class CurrencyAssetDTOFactory extends AssetDTOFactory {
	public static CurrencyAssetDTO create(CurrencyAsset currency) {
		CurrencyAssetDTO currencyDTO = new CurrencyAssetDTO();
		complete(currencyDTO, currency);
		return currencyDTO;
	}
}
