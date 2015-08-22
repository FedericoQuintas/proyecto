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
import com.proyecto.asset.domain.Stock;
import com.proyecto.common.exception.ObjectNotFoundException;

public class StockMongoDAOImpl implements AssetDAO {
	private DBCollection counter;
	private Jongo jongo;
	private DBCollection persistedStocks;
	private DB dbAccess;
	
	public StockMongoDAOImpl(DBCollection counter, Jongo jongo, DB dbAccess) {
		this.counter = counter;
		this.jongo = jongo;
		this.dbAccess = dbAccess;
		
		persistedStocks = dbAccess.getCollection("stocks");
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
		MongoCollection stocks = jongo.getCollection("stocks");

		stocks.remove("{ id: { $gt: 0 } }");

	}

	@Override
	public Asset store(Asset stock) throws JsonGenerationException, JsonMappingException, IOException {
		
		JacksonDBCollection<Stock, String> coll = JacksonDBCollection.wrap(
				persistedStocks, Stock.class, String.class);

		WriteResult<Stock, String> result = coll.insert((Stock)stock);
		stock = result.getSavedObject();
		return stock;
	}

	@Override
	public Asset findById(Long id) throws ObjectNotFoundException {
		
		MongoCollection stocks = jongo.getCollection("stocks");

		Stock stock = stocks.findOne("{id:" + id + " }").as(Stock.class);

		return stock;
	}

	@Override
	public List<Asset> getAll() {

		MongoCollection stocks = jongo.getCollection("stocks");

		MongoCursor<Stock> all = stocks.find().as(Stock.class);
		Iterator<Stock> iterator = all.iterator();

		List<Asset> result = new ArrayList<>();
		while (iterator.hasNext()) {
			Stock next = iterator.next();
			result.add(next);
		}

		return result;
	}

	@Override
	public void update(Asset stock) {
		Jongo jongo = new Jongo(dbAccess);

		MongoCollection stocks = jongo.getCollection("stocks");

		stocks.update("{id:" + stock.getId() + " }").with((Stock)stock);

	}

	@Override
	public Asset findByTicker(String description) throws ObjectNotFoundException {
		
		MongoCollection stocks = jongo.getCollection("stocks");

		Stock stock = stocks.findOne("{ticker:\"" + description + "\"}").as(
				Stock.class);

		if (stock == null) {
			throw new ObjectNotFoundException("Stock Asset " + description
					+ " not found");
		}

		return stock;
	}

}
