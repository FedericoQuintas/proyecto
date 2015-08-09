package currency;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import com.proyecto.asset.exception.DBAccessException;
import com.proyecto.common.SpringBaseTest;
import com.proyecto.common.currency.InvertarCurrencyCode;
import com.proyecto.currency.exception.InvertarCurrencyNotFoundException;
import com.proyecto.currency.service.CurrencyService;
import com.proyecto.rest.resource.currency.dto.InvertarCurrencyDTO;
import com.proyecto.unit.currency.helper.CurrencyHelper;
import com.proyecto.yahoofinance.service.YahooFinanceInformationService;

public class CurrencyServiceTest extends SpringBaseTest {

	private InvertarCurrencyDTO currencyDTO;

	@Resource
	private CurrencyService currencyService;

	@Resource
	private YahooFinanceInformationService yahooFinanceService;

	@Test
	public void whenAskForACurrencyThenCurrencyHasCode()
			throws InvertarCurrencyNotFoundException, DBAccessException {

		currencyDTO = CurrencyHelper.createCurrencyDTO(InvertarCurrencyCode.US);

		currencyDTO = currencyService.store(currencyDTO);

		InvertarCurrencyDTO currency = currencyService
				.findByCode(InvertarCurrencyCode.US);

		Assert.assertTrue(currency.getCode().equals(InvertarCurrencyCode.US));
	}

	@Test
	public void whenAskForACurrencyThenCurrencyHasExchangeSession()
			throws InvertarCurrencyNotFoundException, DBAccessException {

		currencyDTO = CurrencyHelper.createCurrencyDTO(InvertarCurrencyCode.US);

		currencyDTO = currencyService.store(currencyDTO);

		InvertarCurrencyDTO currency = currencyService
				.findByCode(InvertarCurrencyCode.US);

		Assert.assertNotNull(currency.getExchangeSessions());
	}

}
