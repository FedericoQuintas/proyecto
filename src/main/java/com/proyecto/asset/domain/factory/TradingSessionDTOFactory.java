package com.proyecto.asset.domain.factory;

import com.proyecto.rest.resource.asset.dto.TradingSessionDTO;
import com.proyecto.userasset.domain.TradingSession;

public class TradingSessionDTOFactory {

	public static TradingSessionDTO create(TradingSession tradingSession) {
		TradingSessionDTO tradingSessionDTO = new TradingSessionDTO();

		return tradingSessionDTO;
	}

}
