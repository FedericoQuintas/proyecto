package com.proyecto.unit.currency.helper;

import com.proyecto.common.currency.InvertarCurrencyCode;
import com.proyecto.rest.resource.currency.dto.InvertarCurrencyDTO;

public class CurrencyHelper {

	public static InvertarCurrencyDTO createCurrencyDTO(
			InvertarCurrencyCode code) {
		InvertarCurrencyDTO currency = new InvertarCurrencyDTO();
		currency.setCode(code);

		return currency;
	}

}
