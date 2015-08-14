package com.proyecto.currency.domain.factory;

import com.proyecto.currency.domain.ExchangeSession;
import com.proyecto.rest.resource.currency.dto.ExchangeSessionDTO;

public class ExchangeSessionDTOFactory {

	public static ExchangeSessionDTO create(ExchangeSession exchangeSession) {
		
		ExchangeSessionDTO exchangeSessionDTO = new ExchangeSessionDTO();

		exchangeSessionDTO.setPrice(exchangeSession.getPrice());
		exchangeSessionDTO.setDate(exchangeSession.getDate());
		
		return exchangeSessionDTO;
		
	}
	
	
}
