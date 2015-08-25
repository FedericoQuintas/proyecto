package com.proyecto.currency.service;

import com.proyecto.asset.exception.DBAccessException;
import com.proyecto.common.currency.InvertarCurrencyCode;
import com.proyecto.currency.exception.InvertarCurrencyNotFoundException;
import com.proyecto.rest.resource.currency.dto.InvertarCurrencyDTO;

public interface CurrencyService {

	InvertarCurrencyDTO findByCode(InvertarCurrencyCode us)
			throws InvertarCurrencyNotFoundException;

	InvertarCurrencyDTO store(String code) throws DBAccessException;

}
