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
import com.proyecto.asset.domain.MutualFund;
import com.proyecto.common.exception.ObjectNotFoundException;

public class MutualFundMongoDAOImpl implements AssetDAO {
	private DBCollection counter;
	private Jongo jongo;
	private DBCollection persistedMutualFunds;
	private DB dbAccess;
	
	public MutualFundMongoDAOImpl(DBCollection counter, Jongo jongo, DB dbAccess) {
		this.counter = counter;
		this.jongo = jongo;
		this.dbAccess = dbAccess;
		
		persistedMutualFunds = dbAccess.getCollection("mutualFunds");		
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
		MongoCollection mutualFunds = jongo.getCollection("mutualFunds");

		mutualFunds.remove("{ id: { $gt: 0 } }");	}

	@Override
	public Asset store(Asset mutualFund) throws JsonGenerationException, JsonMappingException, IOException {
		mutualFund.setType(AssetType.MUTUALFUND.getType());
		
		JacksonDBCollection<MutualFund, String> coll = JacksonDBCollection.wrap(
				persistedMutualFunds, MutualFund.class, String.class);

		WriteResult<MutualFund, String> result = coll.insert((MutualFund)mutualFund);
		mutualFund = result.getSavedObject();
		return mutualFund;
	}

	@Override
	public Asset findById(Long id) throws ObjectNotFoundException {
		MongoCollection mutualFunds = jongo.getCollection("mutualFunds");

		MutualFund mutualFund = mutualFunds.findOne("{id:" + id + " }").as(MutualFund.class);

		return mutualFund;
	}

	@Override
	public List<Asset> getAll() {
		MongoCollection mutualFunds = jongo.getCollection("mutualFunds");

		MongoCursor<MutualFund> all = mutualFunds.find().as(MutualFund.class);
		Iterator<MutualFund> iterator = all.iterator();

		List<Asset> result = new ArrayList<>();
		while (iterator.hasNext()) {
			MutualFund next = iterator.next();
			result.add(next);
		}

		return result;
	}

	@Override
	public void update(Asset mutualFund) {
		Jongo jongo = new Jongo(dbAccess);

		MongoCollection mutualFunds = jongo.getCollection("stocks");

		mutualFunds.update("{id:" + mutualFund.getId() + " }").with((MutualFund)mutualFund);
	}

	@Override
	public Asset findByTicker(String description) throws ObjectNotFoundException {
		MongoCollection mutualFunds = jongo.getCollection("mutualFunds");

		MutualFund mutualFund = mutualFunds.findOne("{ticker:\"" + description + "\"}").as(
				MutualFund.class);

		return mutualFund;
	}
	

}
