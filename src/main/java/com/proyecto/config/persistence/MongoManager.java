package com.proyecto.config.persistence;

import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

@Component
public class MongoManager {

	private static MongoDatabase database;

	@SuppressWarnings("resource")
	public MongoManager() {

		MongoClient mongoClient = new MongoClient("localhost");

		database = mongoClient.getDatabase("invertarDB");
	}

	public static MongoDatabase getDBAccess() {
		return database;
	}
}
