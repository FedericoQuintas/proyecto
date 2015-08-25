package com.proyecto.currency.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.proyecto.asset.exception.DBAccessException;
import com.proyecto.common.currency.InvertarCurrencyCode;
import com.proyecto.common.exception.ObjectNotFoundException;
import com.proyecto.currency.domain.ExchangeSession;
import com.proyecto.currency.domain.InvertarCurrency;
import com.proyecto.currency.domain.factory.InvertarCurrencyDTOFactory;
import com.proyecto.currency.domain.factory.InvertarCurrencyFactory;
import com.proyecto.currency.exception.InvertarCurrencyLoadException;
import com.proyecto.currency.exception.InvertarCurrencyNotFoundException;
import com.proyecto.currency.persistence.CurrencyDAO;
import com.proyecto.rest.resource.currency.dto.InvertarCurrencyDTO;

@Service("currencyService")
public class CurrencyServiceImpl implements CurrencyService {

	private static final String BLUE_EURO_API_CODE = "blue_euro";

	private static final String EURO_API_CODE = "oficial_euro";

	private static final String BLUE_API_CODE = "blue";

	private static final String OFICIAL_API_CODE = "oficial";

	private static final String VALUE_AVG = "value_avg";

	private static final String API_BLUELYTICS = "http://api.bluelytics.com.ar/v2/latest";

	private Map<InvertarCurrencyCode, String> apiCodes = new HashMap<InvertarCurrencyCode, String>();

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
	public InvertarCurrencyDTO store(String code) throws DBAccessException {
		InvertarCurrency currency = InvertarCurrencyFactory.create(code,
				currencyDAO.nextID());

		InvertarCurrency storedCurrency;
		try {
			storedCurrency = currencyDAO.store(currency);
			return InvertarCurrencyDTOFactory.create(storedCurrency);
		} catch (IOException e) {
			throw new DBAccessException(e.getMessage());
		}
	}

	@Scheduled(cron = "*/59 * * * * ?")
	public void update() throws InvertarCurrencyNotFoundException,
			InvertarCurrencyLoadException {

		try {
			JsonObject json = obtainCurrenciesInformation();

			for (InvertarCurrencyCode currencyCode : apiCodes.keySet()) {

				InvertarCurrency currency = currencyDAO
						.findByCode(currencyCode);

				JsonObject oficialJson = (JsonObject) json.get(apiCodes
						.get(currencyCode));

				JsonElement averageValueJson = oficialJson.get(VALUE_AVG);
				Double averageValue = averageValueJson.getAsDouble();

				currency.addExchangeSession(new ExchangeSession(averageValue,
						new Date()));
				currencyDAO.update(currency);
			}

		} catch (ObjectNotFoundException | IOException e) {
			throw new InvertarCurrencyLoadException(e);
		}
	}

	@PostConstruct
	public void post() throws DBAccessException {
		loadApiCodes();

		loadCurrencies();

	}

	private void loadCurrencies() throws DBAccessException {
		for (InvertarCurrencyCode currencyCode : InvertarCurrencyCode.values()) {
			store(currencyCode.getCode());
		}
	}

	private void loadApiCodes() {
		apiCodes.put(InvertarCurrencyCode.US, OFICIAL_API_CODE);
		apiCodes.put(InvertarCurrencyCode.USBL, BLUE_API_CODE);
		apiCodes.put(InvertarCurrencyCode.EU, EURO_API_CODE);
		apiCodes.put(InvertarCurrencyCode.EUBL, BLUE_EURO_API_CODE);
	}

	private JsonObject obtainCurrenciesInformation()
			throws MalformedURLException, IOException {
		String spec = API_BLUELYTICS;
		URL url = new URL(spec);
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.connect();

		JsonParser jp = new JsonParser();
		JsonElement root = jp.parse(new InputStreamReader((InputStream) request
				.getContent()));
		JsonObject json = root.getAsJsonObject();
		request.disconnect();
		return json;
	}

}
