package com.proyecto.asset.persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.mongojack.JacksonDBCollection;
import org.mongojack.WriteResult;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.proyecto.asset.domain.Asset;
import com.proyecto.asset.domain.AssetType;
import com.proyecto.asset.domain.CurrencyAsset;
import com.proyecto.common.exception.ObjectNotFoundException;

public class CurrencyAssetMongoDAOImpl implements AssetDAO{
	private DBCollection counter;
	private Jongo jongo;
	private DBCollection persistedCurrencies;
	private DB dbAccess;
	
	public CurrencyAssetMongoDAOImpl(DBCollection counter, Jongo jongo, DB dbAccess) {
		this.counter = counter;
		this.jongo = jongo;
		this.dbAccess = dbAccess;
		
		persistedCurrencies = dbAccess.getCollection("currencies");
	}

	@Override
	public Long nextID() {
		BasicDBObject searchQuery = new BasicDBObject("_id", "asset_id");
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
	public Asset store(Asset currency) throws JsonGenerationException, JsonMappingException, IOException {
		currency.setType(AssetType.CURRENCY.getType());
		
		JacksonDBCollection<CurrencyAsset, String> coll = JacksonDBCollection.wrap(
				persistedCurrencies, CurrencyAsset.class, String.class);

		WriteResult<CurrencyAsset, String> result = coll.insert((CurrencyAsset)currency);
		currency = result.getSavedObject();
		return currency;
	}

	@Override
	public Asset findById(Long id) throws ObjectNotFoundException {
		
		MongoCollection currencies = jongo.getCollection("currencies");

		CurrencyAsset currency = currencies.findOne("{id:" + id + " }").as(CurrencyAsset.class);


		return currency;
	}

	@Override
	public List<Asset> getAll() {

		MongoCollection currencies = jongo.getCollection("currencies");

		MongoCursor<CurrencyAsset> all = currencies.find().as(CurrencyAsset.class);
		Iterator<CurrencyAsset> iterator = all.iterator();

		List<Asset> result = new ArrayList<>();
		while (iterator.hasNext()) {
			CurrencyAsset next = iterator.next();
			result.add(next);
		}

		return result;
	}

	@Override
	public void update(Asset currency) {
		Jongo jongo = new Jongo(dbAccess);

		MongoCollection currencies = jongo.getCollection("currencies");

		currencies.update("{id:" + currency.getId() + " }").with((CurrencyAsset)currency);

	}

	@Override
	public Asset findByTicker(String description) throws ObjectNotFoundException {
		
		MongoCollection currencies = jongo.getCollection("currencies");

		CurrencyAsset currency = currencies.findOne("{ticker:\"" + description + "\"}").as(
				CurrencyAsset.class);

		return currency;
	}

}
