package com.proyecto.currency.domain.factory;

import com.proyecto.currency.domain.InvertarCurrency;

public class InvertarCurrencyFactory {

	public static InvertarCurrency create(String code, Long nextID) {
		InvertarCurrency invertarCurrency = new InvertarCurrency(nextID, code);
		return invertarCurrency;
	}

}
