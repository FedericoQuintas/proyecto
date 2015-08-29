package com.proyecto.asset.domain.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;

import com.proyecto.asset.domain.Asset;
import com.proyecto.asset.domain.Bond;
import com.proyecto.asset.domain.Stock;
import com.proyecto.asset.domain.TradingSession;
import com.proyecto.asset.exception.InvalidAssetTypeException;
import com.proyecto.rest.resource.asset.dto.AssetDTO;
import com.proyecto.rest.resource.asset.dto.TradingSessionDTO;

public class AssetDTOFactory {

	public static AssetDTO complete(AssetDTO assetDTO, Asset asset) {

		assetDTO.setId(asset.getId());
		assetDTO.setDescription(asset.getDescription());
		assetDTO.setName(asset.getName());
		assetDTO.setTicker(asset.getTicker());
		assetDTO.setTradingSessions(convertToDTOs(asset.getTradingSessions()));
		assetDTO.setLastTradingPrice(asset.getLastTradingPrice());
		assetDTO.setCurrency(asset.getCurrency());

		return assetDTO;
	}
	
	public static AssetDTO create(Asset asset) throws InvalidAssetTypeException{
		if(asset.getClass().equals(Stock.class)){
			return StockDTOFactory.create((Stock) asset);
		}
		else if(asset.getClass().equals(Bond.class)){
			return BondDTOFactory.create((Bond) asset);
		}
		throw new InvalidAssetTypeException();
	}

	private static List<TradingSessionDTO> convertToDTOs(
			NavigableMap<Long, TradingSession> tradingSessions) {

		List<TradingSessionDTO> tradingSessionsDTO = new ArrayList<TradingSessionDTO>();

		for (TradingSession tradingSession : tradingSessions.values()) {
			tradingSessionsDTO.add(convertToDTO(tradingSession));
		}
		return tradingSessionsDTO;
	}

	private static TradingSessionDTO convertToDTO(TradingSession tradingSession) {
		return TradingSessionDTOFactory.create(tradingSession);
	}

}
