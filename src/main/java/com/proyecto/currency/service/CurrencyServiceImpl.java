package com.proyecto.currency.service;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.proyecto.asset.exception.DBAccessException;
import com.proyecto.common.currency.InvertarCurrencyCode;
import com.proyecto.common.exception.ObjectNotFoundException;
import com.proyecto.currency.domain.InvertarCurrency;
import com.proyecto.currency.domain.factory.InvertarCurrencyDTOFactory;
import com.proyecto.currency.domain.factory.InvertarCurrencyFactory;
import com.proyecto.currency.exception.InvertarCurrencyNotFoundException;
import com.proyecto.currency.persistence.CurrencyDAO;
import com.proyecto.rest.resource.currency.dto.InvertarCurrencyDTO;

@Service("currencyService")
public class CurrencyServiceImpl implements CurrencyService {

	@Resource(name = "currencyDAO")
	private CurrencyDAO currencyDAO;

	@Override
	public InvertarCurrencyDTO findByCode(InvertarCurrencyCode code)
			throws InvertarCurrencyNotFoundException {
		try {
			InvertarCurrency currency = currencyDAO.findByCode(code);

			return InvertarCurrencyDTOFactory.create(currency);
		} catch (ObjectNotFoundException e) {
			throw new InvertarCurrencyNotFoundException(e);
		}
	}

	@Override
	public InvertarCurrencyDTO store(InvertarCurrencyDTO currencyDTO)
			throws DBAccessException {
		InvertarCurrency currency = InvertarCurrencyFactory.create(currencyDTO,
				currencyDAO.nextID());

		InvertarCurrency storedCurrency;
		try {
			storedCurrency = currencyDAO.store(currency);
			return InvertarCurrencyDTOFactory.create(storedCurrency);
		} catch (IOException e) {
			throw new DBAccessException(e.getMessage());
		}
	}

}
