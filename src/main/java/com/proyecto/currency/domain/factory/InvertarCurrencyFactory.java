package com.proyecto.currency.domain.factory;

import com.proyecto.currency.domain.InvertarCurrency;
import com.proyecto.rest.resource.currency.dto.InvertarCurrencyDTO;

public class InvertarCurrencyFactory {

	public static InvertarCurrency create(InvertarCurrencyDTO currencyDTO,
			Long nextID) {
		InvertarCurrency invertarCurrency = new InvertarCurrency(nextID,
				currencyDTO.getCode());
		return invertarCurrency;
	}

}
