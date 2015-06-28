package com.proyecto.asset.domain.factory;

import com.proyecto.asset.domain.Asset;
import com.proyecto.asset.exception.InvalidAssetArgumentException;
import com.proyecto.asset.exception.InvalidTradingSessionArgumentException;
import com.proyecto.common.exception.InvalidArgumentException;
import com.proyecto.common.validator.FieldValidator;
import com.proyecto.rest.resource.asset.dto.AssetDTO;
import com.proyecto.rest.resource.asset.dto.TradingSessionDTO;

public class AssetFactory {

	public static Asset create(AssetDTO assetDTO, Long id)
			throws InvalidAssetArgumentException,
			InvalidTradingSessionArgumentException {

		validateMandatoryFields(assetDTO);

		Asset asset = new Asset(id, assetDTO.getDescription(),
				assetDTO.getTicker(), assetDTO.getCurrency());

		asset.setLastTradingPrice(assetDTO.getLastTradingPrice());
		asset.setIndustry(assetDTO.getIndustry());

		for (TradingSessionDTO tradingSession : assetDTO.getTradingSessions()) {
			asset.addTradingSession(tradingSession);
		}

		return asset;
	}

	private static void validateMandatoryFields(AssetDTO assetDTO)
			throws InvalidAssetArgumentException {

		try {
			FieldValidator.validateEmptyOrNull(assetDTO.getDescription());
			FieldValidator.validateEmptyOrNull(assetDTO.getTicker());
		} catch (InvalidArgumentException e) {
			throw new InvalidAssetArgumentException(e);
		}

	}
}
