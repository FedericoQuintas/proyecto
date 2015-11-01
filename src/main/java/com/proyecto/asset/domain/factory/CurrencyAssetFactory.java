package com.proyecto.asset.domain.factory;

import com.proyecto.asset.domain.CurrencyAsset;
import com.proyecto.asset.exception.InvalidAssetArgumentException;
import com.proyecto.asset.exception.InvalidTradingSessionArgumentException;
import com.proyecto.rest.resource.asset.dto.CurrencyAssetDTO;

public class CurrencyAssetFactory extends AssetFactory {
	public static CurrencyAsset create(CurrencyAssetDTO currencyDTO, Long id) throws InvalidAssetArgumentException, InvalidTradingSessionArgumentException {
		CurrencyAsset currency = new CurrencyAsset(id, currencyDTO.getDescription(),
				currencyDTO.getTicker(), currencyDTO.getCurrency(),
				currencyDTO.getName());
		complete(currency, currencyDTO);
		return currency;
	}
}
