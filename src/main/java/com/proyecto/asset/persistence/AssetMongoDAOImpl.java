package com.proyecto.asset.persistence;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
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

	private DBCollection counter;

	@SuppressWarnings("deprecation")
	public AssetMongoDAOImpl() {
		mongoClient = new MongoClient();
		db = mongoClient.getDB("invertarDB");
		counter = db.getCollection("assets_sequence");

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

	}

	@Override
	public Asset store(Asset asset) throws JsonGenerationException,
			JsonMappingException, IOException {

		DBCollection persistedAssets = db.getCollection("assets");

		JacksonDBCollection<Asset, String> coll = JacksonDBCollection.wrap(
				persistedAssets, Asset.class, String.class);

		WriteResult<Asset, String> result = coll.insert(asset);
		asset = result.getSavedObject();
		return asset;
	}

	@Override
	public Asset findById(Long id) throws ObjectNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Asset> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void udpate(Asset asset) {
		// TODO Auto-generated method stub

	}

	@Override
	public Asset findByTicker(String description)
			throws ObjectNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	public static AssetDAO getInstance() {
		if (instance == null) {
			instance = new AssetMongoDAOImpl();
		}
		return instance;
	}
}
