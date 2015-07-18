package com.proyecto.asset.domain.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;

import com.proyecto.asset.domain.Asset;
import com.proyecto.asset.domain.TradingSession;
import com.proyecto.rest.resource.asset.dto.AssetDTO;
import com.proyecto.rest.resource.asset.dto.TradingSessionDTO;

public class AssetDTOFactory {

	public static AssetDTO create(Asset asset) {

		AssetDTO assetDTO = new AssetDTO();

		assetDTO.setId(asset.getId());
		assetDTO.setDescription(asset.getDescription());
		assetDTO.setName(asset.getName());
		assetDTO.setTicker(asset.getTicker());
		assetDTO.setLeader(asset.isLeader());
		assetDTO.setTradingSessions(convertToDTOs(asset.getTradingSessions()));
		assetDTO.setLastTradingPrice(asset.getLastTradingPrice());
		assetDTO.setIndustry(asset.getIndustry());
		assetDTO.setCurrency(asset.getCurrency());

		return assetDTO;
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
