package com.proyecto.currency.persistence;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.proyecto.common.currency.InvertarCurrencyCode;
import com.proyecto.common.exception.ObjectNotFoundException;
import com.proyecto.currency.domain.InvertarCurrency;

public interface CurrencyDAO {

	InvertarCurrency findByCode(InvertarCurrencyCode code)
			throws ObjectNotFoundException;

	void flush();

	Long nextID();

	InvertarCurrency store(InvertarCurrency currency)
			throws JsonGenerationException, JsonMappingException, IOException;

}
