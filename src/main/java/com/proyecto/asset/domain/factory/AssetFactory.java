package com.proyecto.asset.domain.factory;

import com.proyecto.asset.domain.Asset;
import com.proyecto.asset.exception.InvalidAssetArgumentException;
import com.proyecto.asset.exception.InvalidAssetTypeException;
import com.proyecto.asset.exception.InvalidTradingSessionArgumentException;
import com.proyecto.common.exception.InvalidArgumentException;
import com.proyecto.common.validator.FieldValidator;
import com.proyecto.rest.resource.asset.dto.AssetDTO;
import com.proyecto.rest.resource.asset.dto.BondDTO;
import com.proyecto.rest.resource.asset.dto.MutualFundDTO;
import com.proyecto.rest.resource.asset.dto.StockDTO;
import com.proyecto.rest.resource.asset.dto.TradingSessionDTO;

public class AssetFactory {

	protected static Asset complete(Asset container, AssetDTO assetDTO)
			throws InvalidAssetArgumentException,
			InvalidTradingSessionArgumentException {

		validateMandatoryFields(assetDTO);

		container.setLastTradingPrice(assetDTO.getLastTradingPrice());

		for (TradingSessionDTO tradingSession : assetDTO.getTradingSessions()) {
			container.addTradingSession(tradingSession);
		}

		return container;
	}
	
	public static Asset create(AssetDTO assetDTO, Long id) 
			throws InvalidAssetArgumentException, InvalidTradingSessionArgumentException, InvalidAssetTypeException{
		if(assetDTO.getClass().equals(StockDTO.class)){
			Asset asset = StockFactory.create((StockDTO) assetDTO, id);
			asset.resolveTradingSessionsVariations();
			return asset;
		}
		else if(assetDTO.getClass().equals(BondDTO.class)){
			Asset asset = BondFactory.create((BondDTO) assetDTO, id);
			asset.resolveTradingSessionsVariations();
			return asset;
		}
		else if(assetDTO.getClass().equals(MutualFundDTO.class)){
			return MutualFundFactory.create((MutualFundDTO)assetDTO, id);
		}
		throw new InvalidAssetTypeException();
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
