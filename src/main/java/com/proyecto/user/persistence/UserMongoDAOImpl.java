package com.proyecto.user.persistence;

import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.mongojack.JacksonDBCollection;
import org.mongojack.WriteResult;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoClient;
import com.proyecto.common.exception.ObjectNotFoundException;
import com.proyecto.user.domain.InvertarUser;

@Repository("userMongoDAO")
public class UserMongoDAOImpl implements UserDAO {

	private MongoClient mongoClient;
	private DB db;
	private DBCollection persistedUsers;
	private DBCollection userCounter;
	private DBCollection portfolioCounter;
	private Jongo jongo;

	@SuppressWarnings("deprecation")
	public UserMongoDAOImpl() {

		mongoClient = new MongoClient();
		db = mongoClient.getDB("invertarDB");
		userCounter = db.getCollection("users_sequence");
		portfolioCounter = db.getCollection("portfolios_sequence");
		persistedUsers = db.getCollection("users");
		jongo = new Jongo(db);

		try {
			generateUserCounter();
		} catch (DuplicateKeyException e) {
		}
		try {
			generatePortfolioCounter();
		} catch (DuplicateKeyException e) {
		}
	}

	private void generatePortfolioCounter() {
		BasicDBObject document = new BasicDBObject();
		document.append("_id", "portfolio_id");
		document.append("seq", 0);
		portfolioCounter.insert(document);
	}

	private void generateUserCounter() {
		BasicDBObject document = new BasicDBObject();
		document.append("_id", "user_id");
		document.append("seq", 0);
		userCounter.insert(document);
	}

	@Override
	public Long nextID() {
		BasicDBObject searchQuery = new BasicDBObject("_id", "user_id");
		BasicDBObject increase = new BasicDBObject("seq", 1);
		BasicDBObject updateQuery = new BasicDBObject("$inc", increase);
		DBObject result = userCounter.findAndModify(searchQuery, null, null,
				false, updateQuery, true, false);

		return Long.valueOf(result.get("seq").toString());
	}

	@Override
	public void flush() {

		MongoCollection users = jongo.getCollection("users");

		users.remove("{ id: { $gt: 0 } }");

	}

	@Override
	public InvertarUser store(InvertarUser user) {
		JacksonDBCollection<InvertarUser, String> coll = JacksonDBCollection
				.wrap(persistedUsers, InvertarUser.class, String.class);

		WriteResult<InvertarUser, String> result = coll.insert(user);
		user = result.getSavedObject();
		return user;
	}

	@Override
	public InvertarUser findById(Long id) throws ObjectNotFoundException {

		MongoCollection users = jongo.getCollection("users");

		InvertarUser user = users.findOne("{id:" + id + " }").as(
				InvertarUser.class);

		if (user == null) {
			throw new ObjectNotFoundException("User " + id + " not found");
		}

		return user;
	}

	@Override
	public void update(InvertarUser invertarUser) {

		MongoCollection users = jongo.getCollection("users");

		users.update("{id:" + invertarUser.getId() + " }").with(invertarUser);
	}

	@Override
	public Long nextPortfolioID() {
		BasicDBObject searchQuery = new BasicDBObject("_id", "portfolio_id");
		BasicDBObject increase = new BasicDBObject("seq", 1);
		BasicDBObject updateQuery = new BasicDBObject("$inc", increase);
		DBObject result = portfolioCounter.findAndModify(searchQuery, null,
				null, false, updateQuery, true, false);

		return Long.valueOf(result.get("seq").toString());

	}

	@Override
	public InvertarUser findByMail(String mail) throws ObjectNotFoundException {

		MongoCollection users = jongo.getCollection("users");

		InvertarUser user = users.findOne("{mail:\"" + mail + "\"}").as(
				InvertarUser.class);

		if (user == null) {
			throw new ObjectNotFoundException("User " + mail + " not found");
		}

		return user;

	}

	@Override
	public InvertarUser findByUsername(String username)
			throws ObjectNotFoundException {

		MongoCollection users = jongo.getCollection("users");

		InvertarUser user = users.findOne("{username:\"" + username + "\"}")
				.as(InvertarUser.class);

		if (user == null) {
			throw new ObjectNotFoundException("User " + username + " not found");
		}

		return user;

	}

	@Override
	public Boolean existsUserWithMail(String mail) {

		MongoCollection users = jongo.getCollection("users");

		InvertarUser user = users.findOne("{mail:\"" + mail + "\"}").as(
				InvertarUser.class);

		return user != null;
	}

	@Override
	public Boolean existsUserWithUsername(String username) {
		MongoCollection users = jongo.getCollection("users");

		InvertarUser user = users.findOne("{username:\"" + username + "\"}")
				.as(InvertarUser.class);

		return user != null;
	}

}
