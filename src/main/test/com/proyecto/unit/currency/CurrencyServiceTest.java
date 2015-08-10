package com.proyecto.unit.currency;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import com.proyecto.asset.exception.DBAccessException;
import com.proyecto.common.SpringBaseTest;
import com.proyecto.common.currency.InvertarCurrencyCode;
import com.proyecto.currency.exception.InvertarCurrencyNotFoundException;
import com.proyecto.currency.service.CurrencyService;
import com.proyecto.rest.resource.currency.dto.InvertarCurrencyDTO;
import com.proyecto.yahoofinance.service.YahooFinanceInformationService;

public class CurrencyServiceTest extends SpringBaseTest {

	@Resource
	private CurrencyService currencyService;

	@Resource
	private YahooFinanceInformationService yahooFinanceService;

	@Test
	public void whenAskForACurrencyThenCurrencyHasCode()
			throws InvertarCurrencyNotFoundException, DBAccessException {

		currencyService.store(InvertarCurrencyCode.US.getCode());

		InvertarCurrencyDTO currency = currencyService
				.findByCode(InvertarCurrencyCode.US);

		Assert.assertTrue(currency.getCode().equals(InvertarCurrencyCode.US));
	}

	@Test
	public void whenAskForACurrencyThenCurrencyHasExchangeSession()
			throws InvertarCurrencyNotFoundException, DBAccessException {

		currencyService.store(InvertarCurrencyCode.US.getCode());

		InvertarCurrencyDTO currency = currencyService
				.findByCode(InvertarCurrencyCode.US);

		Assert.assertNotNull(currency.getExchangeSessions());
	}

}
