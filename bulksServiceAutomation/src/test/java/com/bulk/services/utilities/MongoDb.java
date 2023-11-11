package com.bulk.services.utilities;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDb {

	private MongoClient mongoClient;
	private MongoDatabase database;
	private MongoCollection<Document> collection;

	public void MongoDBChecker(String mongoURI, String dbName, String collectionName) {
		MongoClientURI connectionString = new MongoClientURI(mongoURI);
		mongoClient = new MongoClient(connectionString);
		database = mongoClient.getDatabase(dbName);
		collection = database.getCollection(collectionName);

	}

	@SuppressWarnings("deprecation")
	public long countRecordsWithNotificationId() throws InterruptedException {
		Thread.sleep(2000);

		return collection.count();
	}
	
	@SuppressWarnings("deprecation")
	public long countRecordsWithNotificationId(String json) throws InterruptedException {
		Thread.sleep(2000);
		
		return collection.count(Document.parse(json));
		
	}

	public void close() {
		mongoClient.close();
	}

}
