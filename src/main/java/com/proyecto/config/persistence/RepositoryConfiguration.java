package com.proyecto.config.persistence;

import java.net.UnknownHostException;

import org.springframework.stereotype.Service;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

@Service
public class RepositoryConfiguration {

	private String mongoDbAddress = "localhost";
	private MongoClient mongoClient;

	public RepositoryConfiguration() throws UnknownHostException {
		getMongo();
	}

	public MongoClient getMongo() throws UnknownHostException {
		MongoClientOptions options = MongoClientOptions.builder()
				.connectionsPerHost(150).writeConcern(WriteConcern.NORMAL)
				.build();

		ServerAddress severAddress = new ServerAddress(mongoDbAddress);
		mongoClient = new MongoClient(severAddress, options);
		return mongoClient;
	}

	public MongoClient getMongoClient() {
		return mongoClient;
	}

}
