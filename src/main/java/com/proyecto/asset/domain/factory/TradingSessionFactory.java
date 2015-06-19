package com.proyecto.asset.domain.factory;

import com.proyecto.asset.domain.TradingSession;
import com.proyecto.rest.resource.asset.dto.TradingSessionDTO;

public class TradingSessionFactory {

	public static TradingSession create(TradingSessionDTO tradingSessionDTO) {
		return new TradingSession();

	}

}
