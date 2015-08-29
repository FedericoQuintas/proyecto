package com.proyecto.asset.persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.jongo.Jongo;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.DuplicateKeyException;
import com.proyecto.asset.domain.Asset;
import com.proyecto.asset.domain.AssetType;
import com.proyecto.asset.domain.Bond;
import com.proyecto.asset.domain.Stock;
import com.proyecto.common.exception.ObjectNotFoundException;
import com.proyecto.config.persistence.MongoAccessConfiguration;

@Repository("assetMongoDAO")
public class AssetMongoDAOImpl implements AssetDAO {

	private DB dbAccess;
	private Jongo jongo;
	private DBCollection counter;
	private String dbName = "invertarDB";
	private AssetDAO stockDAO;
	private AssetDAO bondDAO;

	@Resource
	private MongoAccessConfiguration mongoAccessConfiguration;

	@PostConstruct
	public void post() {

		configureDBAccess();

		try {
			BasicDBObject document = new BasicDBObject();
			document.append("_id", "asset_id");
			document.append("seq", 0);
			counter.insert(document);
			stockDAO = new StockMongoDAOImpl(counter, jongo, dbAccess);
			bondDAO = new BondMongoDAOImpl(counter, jongo, dbAccess);
		} catch (DuplicateKeyException e) {
		}
	}

	@SuppressWarnings("deprecation")
	private void configureDBAccess() {
		dbAccess = mongoAccessConfiguration.getMongoClient().getDB(dbName);

		counter = dbAccess.getCollection("assets_sequence");
		jongo = new Jongo(dbAccess);
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
		stockDAO.flush();
		bondDAO.flush();

	}

	@Override
	public Asset store(Asset asset) throws JsonGenerationException,
			JsonMappingException, IOException {

		if (asset.getClass().equals(Stock.class)) {
			asset.setType(AssetType.STOCK.getType());
			asset = stockDAO.store(asset);
		} else if (asset.getClass().equals(Bond.class)) {
			asset.setType(AssetType.BOND.getType());
			asset = bondDAO.store(asset);
		}

		return asset;
	}

	@Override
	public Asset findById(Long id) throws ObjectNotFoundException {

		Asset asset;
		asset = stockDAO.findById(id);

		if (asset == null) {
			asset = bondDAO.findById(id);
		}
		// MongoCollection assets = jongo.getCollection("assets");
		//
		// Asset asset = assets.findOne("{id:" + id + " }").as(Asset.class);

		if (asset == null) {
			throw new ObjectNotFoundException("Asset " + id + " not found");
		}

		return asset;
	}

	@Override
	public List<Asset> getAll() {

		List<Asset> result = new ArrayList<Asset>();

		result.addAll(stockDAO.getAll());
		result.addAll(bondDAO.getAll());

		return result;
	}

	@Override
	public void update(Asset asset) {

		if (asset.getClass().equals(Stock.class)) {
			stockDAO.update(asset);
		} else if (asset.getClass().equals(Bond.class)) {
			bondDAO.update(asset);
		}
	}

	@Override
	public Asset findByTicker(String description)
			throws ObjectNotFoundException {

		Asset asset;
		asset = stockDAO.findByTicker(description);

		if (asset == null) {
			asset = bondDAO.findByTicker(description);
		}

		if (asset == null) {
			throw new ObjectNotFoundException("Asset " + description
					+ " not found");
		}

		return asset;
	}

}
