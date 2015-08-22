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
import com.proyecto.asset.domain.Bond;
import com.proyecto.common.exception.ObjectNotFoundException;

public class BondMongoDAOImpl implements AssetDAO{
	private DBCollection counter;
	private Jongo jongo;
	private DBCollection persistedBonds;
	private DB dbAccess;
	
	public BondMongoDAOImpl(DBCollection counter, Jongo jongo, DB dbAccess) {
		this.counter = counter;
		this.jongo = jongo;
		this.dbAccess = dbAccess;
		
		persistedBonds = dbAccess.getCollection("bonds");
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
		MongoCollection bonds = jongo.getCollection("bonds");

		bonds.remove("{ id: { $gt: 0 } }");

	}

	@Override
	public Asset store(Asset bond) throws JsonGenerationException, JsonMappingException, IOException {
		
		JacksonDBCollection<Bond, String> coll = JacksonDBCollection.wrap(
				persistedBonds, Bond.class, String.class);

		WriteResult<Bond, String> result = coll.insert((Bond)bond);
		bond = result.getSavedObject();
		return bond;
	}

	@Override
	public Asset findById(Long id) throws ObjectNotFoundException {
		
		MongoCollection bonds = jongo.getCollection("bonds");

		Bond bond = bonds.findOne("{id:" + id + " }").as(Bond.class);


		return bond;
	}

	@Override
	public List<Asset> getAll() {

		MongoCollection bonds = jongo.getCollection("bonds");

		MongoCursor<Bond> all = bonds.find().as(Bond.class);
		Iterator<Bond> iterator = all.iterator();

		List<Asset> result = new ArrayList<>();
		while (iterator.hasNext()) {
			Bond next = iterator.next();
			result.add(next);
		}

		return result;
	}

	@Override
	public void update(Asset bond) {
		Jongo jongo = new Jongo(dbAccess);

		MongoCollection bonds = jongo.getCollection("bonds");

		bonds.update("{id:" + bond.getId() + " }").with((Bond)bond);

	}

	@Override
	public Asset findByTicker(String description) throws ObjectNotFoundException {
		
		MongoCollection bonds = jongo.getCollection("bonds");

		Bond bond = bonds.findOne("{ticker:\"" + description + "\"}").as(
				Bond.class);

		if (bond == null) {
			throw new ObjectNotFoundException("Bond Asset " + description
					+ " not found");
		}

		return bond;
	}

}
