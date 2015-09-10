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
		tradingSessionDTO.setTradingDate(tradingSession.getTradingDate()
				.getTime());
		tradingSessionDTO.setVolume(tradingSession.getVolume());
		
		tradingSessionDTO.setSma_7(tradingSession.getSma_7());
		tradingSessionDTO.setSma_21(tradingSession.getSma_21());
		tradingSessionDTO.setSma_50(tradingSession.getSma_50());
		tradingSessionDTO.setSma_200(tradingSession.getSma_200());

		tradingSessionDTO.setEma_7(tradingSession.getEma_7());
		tradingSessionDTO.setEma_21(tradingSession.getEma_21());
		tradingSessionDTO.setEma_50(tradingSession.getEma_50());
		tradingSessionDTO.setEma_200(tradingSession.getEma_200());

		tradingSessionDTO.setRsi_7(tradingSession.getRsi_7());
		tradingSessionDTO.setRsi_21(tradingSession.getRsi_21());
		tradingSessionDTO.setRsi_50(tradingSession.getRsi_50());
		tradingSessionDTO.setRsi_200(tradingSession.getRsi_200());

		tradingSessionDTO.setMomentum_7(tradingSession.getMomentum_7());
		tradingSessionDTO.setMomentum_21(tradingSession.getMomentum_21());
		tradingSessionDTO.setMomentum_50(tradingSession.getMomentum_50());
		tradingSessionDTO.setMomentum_200(tradingSession.getMomentum_200());
		
		tradingSessionDTO.setAdjClosingPrice(tradingSession.getAdjClosingPrice());
		tradingSessionDTO.setMacd_histogram(tradingSession.getMacd_histogram());
		tradingSessionDTO.setMacd_macd_line(tradingSession.getMacd_macd_line());
		tradingSessionDTO.setMacd_signal_line(tradingSession.getMacd_signal_line());

		return tradingSessionDTO;
	}

}
