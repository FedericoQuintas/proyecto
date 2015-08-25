package com.proyecto.currency.domain.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;

import com.proyecto.common.currency.InvertarCurrencyCode;
import com.proyecto.currency.domain.ExchangeSession;
import com.proyecto.currency.domain.InvertarCurrency;
import com.proyecto.rest.resource.currency.dto.ExchangeSessionDTO;
import com.proyecto.rest.resource.currency.dto.InvertarCurrencyDTO;

public class InvertarCurrencyDTOFactory {

	public static InvertarCurrencyDTO create(InvertarCurrency currency) {
		InvertarCurrencyDTO invertarCurrencyDTO = new InvertarCurrencyDTO();
		invertarCurrencyDTO.setCode(InvertarCurrencyCode.valueOf(currency
				.getCode()));
		invertarCurrencyDTO.setExchangeSessions(convertToDTOs(currency.getExchangeSessions()));
		return invertarCurrencyDTO;
	}
	
	private static List<ExchangeSessionDTO> convertToDTOs(
			NavigableMap<Long, ExchangeSession> exchangeSessions) {

		List<ExchangeSessionDTO> exchangeSessionsDTO = new ArrayList<ExchangeSessionDTO>();

		for (ExchangeSession exchangeSession : exchangeSessions.values()) {
			exchangeSessionsDTO.add(convertToDTO(exchangeSession));
		}
		return exchangeSessionsDTO;
	}
	
	private static ExchangeSessionDTO convertToDTO(ExchangeSession exchangeSession) {
		return ExchangeSessionDTOFactory.create(exchangeSession);
	}
	
}
