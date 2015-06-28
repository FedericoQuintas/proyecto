package com.proyecto.asset.domain.factory;

import com.proyecto.asset.domain.TradingSession;
import com.proyecto.rest.resource.asset.dto.TradingSessionDTO;

public class TradingSessionDTOFactory {

	public static TradingSessionDTO create(TradingSession tradingSession) {
		TradingSessionDTO tradingSessionDTO = new TradingSessionDTO();

		tradingSessionDTO.setClosingPrice(tradingSession.getClosingPrice());
		tradingSessionDTO.setMaxPrice(tradingSession.getMaxPrice());
		tradingSessionDTO.setMinPrice(tradingSession.getMinPrice());
		tradingSessionDTO.setOpeningPrice(tradingSession.getOpeningPrice());
		tradingSessionDTO.setTradingDate(tradingSession.getTradingDate());
		tradingSessionDTO.setVolume(tradingSession.getVolume());
		
		return tradingSessionDTO;
	}

}
