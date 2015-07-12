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
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoClient;
import com.proyecto.asset.domain.Asset;
import com.proyecto.common.exception.ObjectNotFoundException;

@Repository("assetMongoDAO")
public class AssetMongoDAOImpl implements AssetDAO {

	private static AssetDAO instance;
	private MongoClient mongoClient;
	private DB db;
	private DBCollection persistedAssets;
	private DBCollection counter;
	private Jongo jongo;

	@SuppressWarnings("deprecation")
	public AssetMongoDAOImpl() {
		mongoClient = new MongoClient();
		db = mongoClient.getDB("invertarDB");
		counter = db.getCollection("assets_sequence");
		jongo = new Jongo(db);
		persistedAssets = db.getCollection("assets");

		try {
			BasicDBObject document = new BasicDBObject();
			document.append("_id", "asset_id");
			document.append("seq", 0);
			counter.insert(document);
		} catch (DuplicateKeyException e) {
		}
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

		MongoCollection assets = jongo.getCollection("assets");

		assets.remove("{ id: { $gt: 0 } }");

	}

	@Override
	public Asset store(Asset asset) throws JsonGenerationException,
			JsonMappingException, IOException {

		JacksonDBCollection<Asset, String> coll = JacksonDBCollection.wrap(
				persistedAssets, Asset.class, String.class);

		WriteResult<Asset, String> result = coll.insert(asset);
		asset = result.getSavedObject();
		return asset;
	}

	@Override
	public Asset findById(Long id) throws ObjectNotFoundException {

		MongoCollection assets = jongo.getCollection("assets");

		Asset asset = assets.findOne("{id:" + id + " }").as(Asset.class);

		if (asset == null) {
			throw new ObjectNotFoundException("Asset " + id + " not found");
		}

		return asset;
	}

	@Override
	public List<Asset> getAll() {

		MongoCollection assets = jongo.getCollection("assets");

		MongoCursor<Asset> all = assets.find().as(Asset.class);
		Iterator<Asset> iterator = all.iterator();

		List<Asset> result = new ArrayList<>();
		while (iterator.hasNext()) {
			Asset next = iterator.next();
			result.add(next);
		}

		return result;
	}

	@Override
	public void udpate(Asset asset) {

		Jongo jongo = new Jongo(db);

		MongoCollection assets = jongo.getCollection("assets");

		assets.update("{id:" + asset.getId() + " }").with(asset);
	}

	@Override
	public Asset findByTicker(String description)
			throws ObjectNotFoundException {

		MongoCollection assets = jongo.getCollection("assets");

		Asset asset = assets.findOne("{ticker:\"" + description + "\"}").as(
				Asset.class);

		if (asset == null) {
			throw new ObjectNotFoundException("Asset " + description
					+ " not found");
		}

		return asset;
	}

	public static AssetDAO getInstance() {
		if (instance == null) {
			instance = new AssetMongoDAOImpl();
		}
		return instance;
	}
}
