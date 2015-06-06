package com.proyecto.asset.domain.factory;

import com.proyecto.rest.resource.asset.dto.TradingSessionDTO;
import com.proyecto.userasset.domain.TradingSession;

public class TradingSessionFactory {

	public static TradingSession create(TradingSessionDTO tradingSessionDTO) {
		return new TradingSession();

	}

}
