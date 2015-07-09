package com.proyecto.asset.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.proyecto.asset.domain.Asset;
import com.proyecto.common.exception.ObjectNotFoundException;

@Repository("assetMongoDAO")
public class AssetMongoDAOImpl implements AssetDAO {

	private static AssetDAO instance;

	@Override
	public Long nextID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("resource")
	@Override
	public Asset store(Asset asset) {
		MongoClient mongoClient = new MongoClient();
		DB db = mongoClient.getDB("invertarDB");
		DBCollection persistedAssets = db.getCollection("assets");

		BasicDBObject document = new BasicDBObject();
		persistedAssets.insert(document);
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
			instance = new AssetDAOImpl();
		}
		return instance;
	}
}
