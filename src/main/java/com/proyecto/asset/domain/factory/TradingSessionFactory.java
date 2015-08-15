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
