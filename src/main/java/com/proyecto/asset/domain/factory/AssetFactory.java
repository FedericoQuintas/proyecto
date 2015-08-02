package com.proyecto.asset.domain.factory;

import com.proyecto.asset.domain.Asset;
import com.proyecto.asset.exception.InvalidAssetArgumentException;
import com.proyecto.asset.exception.InvalidTradingSessionArgumentException;
import com.proyecto.common.exception.InvalidArgumentException;
import com.proyecto.common.validator.FieldValidator;
import com.proyecto.rest.resource.asset.dto.AssetDTO;
import com.proyecto.rest.resource.asset.dto.BondDTO;
import com.proyecto.rest.resource.asset.dto.StockDTO;
import com.proyecto.rest.resource.asset.dto.TradingSessionDTO;

public class AssetFactory {

	private static Asset complete(Asset container, AssetDTO assetDTO)
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
			throws InvalidAssetArgumentException, InvalidTradingSessionArgumentException{
		if(assetDTO.getClass().equals(StockDTO.class)){
			return create((StockDTO) assetDTO, id);
		}
		else if(assetDTO.getClass().equals(BondDTO.class)){
			return create((BondDTO) assetDTO, id);
		}
		return null;
	}
	
	public static Stock create(StockDTO stockDTO, Long id) 
			throws InvalidAssetArgumentException, InvalidTradingSessionArgumentException {
		Stock stock = new Stock(id, stockDTO.getDescription(),
				stockDTO.getTicker(), stockDTO.getCurrency());
		complete(stock, stockDTO);
		//TODO: completar los campos especificos de stock
		return stock;
	}
	
	public static Bond create(BondDTO bondDTO, Long id) 
			throws InvalidAssetArgumentException, InvalidTradingSessionArgumentException {
		Bond bond = new Bond(id, bondDTO.getDescription(),
				bondDTO.getTicker(), bondDTO.getCurrency());
		complete(bond, bondDTO);
		//TODO: completar los campos especificos de stock
		return bond;
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
