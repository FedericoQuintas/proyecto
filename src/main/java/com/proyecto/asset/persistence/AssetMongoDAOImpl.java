package com.proyecto.asset.persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.proyecto.asset.domain.MutualFund;
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
	private AssetDAO mutualFundDAO;
	private Map<Class, AssetDAO> assetDAOsMap = new HashMap<Class, AssetDAO>();

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
			mutualFundDAO = new MutualFundMongoDAOImpl(counter, jongo, dbAccess);
			
			assetDAOsMap.put(Stock.class, stockDAO);
			assetDAOsMap.put(Bond.class, bondDAO);
			assetDAOsMap.put(MutualFund.class, mutualFundDAO);
			
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
		for(AssetDAO assetDAO : assetDAOsMap.values()){
			assetDAO.flush();
		}

	}

	@Override
	public Asset store(Asset asset) throws JsonGenerationException,
			JsonMappingException, IOException {
		
		AssetDAO assetDAO = assetDAOsMap.get(asset.getClass());
		asset = assetDAO.store(asset);

		return asset;
	}

	@Override
	public Asset findById(Long id) throws ObjectNotFoundException {

		Asset asset;
		
		for(AssetDAO assetDAO : assetDAOsMap.values()){
			asset = assetDAO.findById(id);
			if (asset != null){
				return asset;
			}
		}
		
		//By this point if we couldn't find an asset, we don't have it
		throw new ObjectNotFoundException("Asset " + id + " not found");

	}

	@Override
	public List<Asset> getAll() {

		List<Asset> result = new ArrayList<Asset>();
		
		for(AssetDAO assetDAO : assetDAOsMap.values()){
			result.addAll(assetDAO.getAll());
		}

		return result;
	}

	@Override
	public void update(Asset asset) {
		AssetDAO assetDAO = assetDAOsMap.get(asset.getClass());
		assetDAO.update(asset);
	}

	@Override
	public Asset findByTicker(String description)
			throws ObjectNotFoundException {

		Asset asset;
		for(AssetDAO assetDAO : assetDAOsMap.values()){
			asset = assetDAO.findByTicker(description);
			if (asset != null){
				return asset;
			}
		}
		
		throw new ObjectNotFoundException("Asset " + description
				+ " not found");

	}

}
