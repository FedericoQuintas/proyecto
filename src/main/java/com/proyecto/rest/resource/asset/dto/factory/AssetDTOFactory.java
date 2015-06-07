package com.proyecto.rest.resource.asset.dto.factory;

import java.util.ArrayList;
import java.util.List;

import com.proyecto.asset.domain.Asset;
import com.proyecto.rest.resource.asset.dto.AssetDTO;
import com.proyecto.rest.resource.asset.dto.TradingSessionDTO;
import com.proyecto.userasset.domain.TradingSession;

public class AssetDTOFactory {

	public static AssetDTO create(Asset asset) {

		AssetDTO assetDTO = new AssetDTO();

		assetDTO.setId(asset.getId());
		assetDTO.setDescription(asset.getDescription());
		assetDTO.setTicker(asset.getTicker());
		assetDTO.setTradingSessions(convertToDTOs(asset.getTradingSessions()));
		assetDTO.setLastTradingPrice(asset.getLastTradingPrice());

		return assetDTO;
	}

	private static List<TradingSessionDTO> convertToDTOs(
			List<TradingSession> tradingSessions) {

		List<TradingSessionDTO> tradingSessionsDTO = new ArrayList<TradingSessionDTO>();

		for (TradingSession tradingSession : tradingSessions) {
			tradingSessionsDTO.add(convertToDTO(tradingSession));
		}
		return tradingSessionsDTO;
	}

	private static TradingSessionDTO convertToDTO(TradingSession tradingSession) {
		return TradingSessionDTOFactory.create(tradingSession);
	}

}
