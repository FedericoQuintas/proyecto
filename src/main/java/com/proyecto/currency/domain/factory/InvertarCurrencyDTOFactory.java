package com.proyecto.currency.domain.factory;

import com.proyecto.currency.domain.InvertarCurrency;
import com.proyecto.rest.resource.currency.dto.InvertarCurrencyDTO;

public class InvertarCurrencyDTOFactory {

	public static InvertarCurrencyDTO create(InvertarCurrency currency) {
		InvertarCurrencyDTO invertarCurrencyDTO = new InvertarCurrencyDTO();
		invertarCurrencyDTO.setCode(currency.getCode());
		invertarCurrencyDTO.setExchangeSessions(currency.getExchangeSessions());
		return invertarCurrencyDTO;
	}
}
