package com.proyecto.asset.domain.factory;

import java.util.Date;

import com.proyecto.asset.domain.TradingSession;
import com.proyecto.asset.exception.InvalidTradingSessionArgumentException;
import com.proyecto.common.exception.InvalidArgumentException;
import com.proyecto.common.validator.FieldValidator;
import com.proyecto.rest.resource.asset.dto.TradingSessionDTO;

public class TradingSessionFactory {

	public static TradingSession create(TradingSessionDTO tradingSessionDTO)
			throws InvalidTradingSessionArgumentException {

		validateMandatoryFields(tradingSessionDTO);

		TradingSession tradingSession = new TradingSession(new Date(
				tradingSessionDTO.getTradingDate()));
		tradingSession.setClosingPrice(tradingSessionDTO.getClosingPrice());
		tradingSession.setMaxPrice(tradingSessionDTO.getMaxPrice());
		tradingSession.setMinPrice(tradingSessionDTO.getMinPrice());
		tradingSession.setOpeningPrice(tradingSessionDTO.getOpeningPrice());
		tradingSession.setVolume(tradingSessionDTO.getVolume());
		tradingSession.setSma_7(tradingSessionDTO.getSma_7());
		tradingSession.setSma_21(tradingSessionDTO.getSma_21());
		tradingSession.setSma_50(tradingSessionDTO.getSma_50());
		tradingSession.setSma_200(tradingSessionDTO.getSma_200());

		tradingSession.setEma_7(tradingSessionDTO.getEma_7());
		tradingSession.setEma_21(tradingSessionDTO.getEma_21());
		tradingSession.setEma_50(tradingSessionDTO.getEma_50());
		tradingSession.setEma_200(tradingSessionDTO.getEma_200());

		tradingSession.setRsi_7(tradingSessionDTO.getRsi_7());
		tradingSession.setRsi_21(tradingSessionDTO.getRsi_21());
		tradingSession.setRsi_50(tradingSessionDTO.getRsi_50());
		tradingSession.setRsi_200(tradingSessionDTO.getRsi_200());

		tradingSession.setMomentum_7(tradingSessionDTO.getMomentum_7());
		tradingSession.setMomentum_21(tradingSessionDTO.getMomentum_21());
		tradingSession.setMomentum_50(tradingSessionDTO.getMomentum_50());
		tradingSession.setMomentum_200(tradingSessionDTO.getMomentum_200());

		return tradingSession;

	}

	private static void validateMandatoryFields(
			TradingSessionDTO tradingSessionDTO)
			throws InvalidTradingSessionArgumentException {
		try {
			FieldValidator.validateNull(tradingSessionDTO.getTradingDate());
		} catch (InvalidArgumentException e) {
			throw new InvalidTradingSessionArgumentException(e);
		}

	}

}
