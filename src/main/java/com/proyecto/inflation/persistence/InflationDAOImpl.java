package com.proyecto.inflation.persistence;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.mongojack.JacksonDBCollection;
import org.mongojack.WriteResult;
import org.springframework.stereotype.Repository;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.proyecto.config.persistence.MongoAccessConfiguration;
import com.proyecto.inflation.domain.Inflation;

@Repository("inflationDAO")
public class InflationDAOImpl implements InflationDAO {

	private DB dbAccess;
	private DBCollection persistedInflationSessions;
	private Jongo jongo;
	private String dbName = "invertarDB";

	@Resource
	private MongoAccessConfiguration mongoAccessConfiguration;

	@PostConstruct
	public void post() {

		configureDBAccess();
		persistedInflationSessions = dbAccess.getCollection("inflation");

	}

	@SuppressWarnings("deprecation")
	private void configureDBAccess() {
		dbAccess = mongoAccessConfiguration.getMongoClient().getDB(dbName);

		jongo = new Jongo(dbAccess);
	}

	@Override
	public void flush() {

		MongoCollection inflation = jongo.getCollection("inflation");

		inflation.drop();

	}

	@Override
	public Inflation store(Inflation inflation) {

		JacksonDBCollection<Inflation, String> coll = JacksonDBCollection.wrap(
				persistedInflationSessions, Inflation.class, String.class);

		WriteResult<Inflation, String> result = coll.insert(inflation);
		inflation = result.getSavedObject();
		return inflation;
	}

}
