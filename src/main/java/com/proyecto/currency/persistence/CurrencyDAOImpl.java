package com.proyecto.currency.persistence;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.mongojack.JacksonDBCollection;
import org.mongojack.WriteResult;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.DuplicateKeyException;
import com.proyecto.common.currency.InvertarCurrencyCode;
import com.proyecto.common.exception.ObjectNotFoundException;
import com.proyecto.config.persistence.MongoAccessConfiguration;
import com.proyecto.currency.domain.InvertarCurrency;

@Repository("currencyDAO")
public class CurrencyDAOImpl implements CurrencyDAO {

	private DB dbAccess;
	private DBCollection persistedCurrencies;
	private Jongo jongo;
	private DBCollection counter;
	private String dbName = "invertarDB";

	@Resource
	private MongoAccessConfiguration mongoAccessConfiguration;

	@PostConstruct
	public void post() {

		configureDBAccess();
		persistedCurrencies = dbAccess.getCollection("currency");

		try {
			BasicDBObject document = new BasicDBObject();
			document.append("_id", "currency_id");
			document.append("seq", 0);
			counter.insert(document);
		} catch (DuplicateKeyException e) {
		}
	}

	@SuppressWarnings("deprecation")
	private void configureDBAccess() {
		dbAccess = mongoAccessConfiguration.getMongoClient().getDB(dbName);

		counter = dbAccess.getCollection("currency_sequence");
		jongo = new Jongo(dbAccess);
	}

	@Override
	public Long nextID() {

		BasicDBObject searchQuery = new BasicDBObject("_id", "currency_id");
		BasicDBObject increase = new BasicDBObject("seq", 1);
		BasicDBObject updateQuery = new BasicDBObject("$inc", increase);
		DBObject result = counter.findAndModify(searchQuery, null, null, false,
				updateQuery, true, false);

		return Long.valueOf(result.get("seq").toString());

	}

	@Override
	public void flush() {

		MongoCollection currencies = jongo.getCollection("currencies");

		currencies.remove("{ id: { $gt: 0 } }");

	}

	@Override
	public InvertarCurrency findByCode(InvertarCurrencyCode code)
			throws ObjectNotFoundException {
		MongoCollection currencies = jongo.getCollection("currency");

		InvertarCurrency currency = currencies.findOne(
				"{code:\"" + code + "\"}").as(InvertarCurrency.class);

		if (currency == null) {
			throw new ObjectNotFoundException("Currency " + code + " not found");
		}

		return currency;

	}

	@Override
	public InvertarCurrency store(InvertarCurrency currency)
			throws JsonGenerationException, JsonMappingException, IOException {

		JacksonDBCollection<InvertarCurrency, String> coll = JacksonDBCollection
				.wrap(persistedCurrencies, InvertarCurrency.class, String.class);

		WriteResult<InvertarCurrency, String> result = coll.insert(currency);
		currency = result.getSavedObject();
		return currency;
	}

}
