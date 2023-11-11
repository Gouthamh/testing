package com.db;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBChecker {

	private MongoClient mongoClient;
	private MongoDatabase database;
	private MongoCollection<Document> collection;

	public MongoDBChecker(String mongoURI, String dbName, String collectionName) {
		MongoClientURI connectionString = new MongoClientURI(mongoURI);
		mongoClient = new MongoClient(connectionString);
		database = mongoClient.getDatabase(dbName);
		collection = database.getCollection(collectionName);
	}

	public long countRecordsWithNotificationId() {

		return collection.count();
	}
	
	public long countRecordsWithNotificationId(String Json) {

		return collection.count(Document.parse(Json));
	}

	public void close() {
		mongoClient.close();
	}

	public static void main(String[] args) {
		String mongoURI = "mongodb://DAP_IAT:DAP_IAT@172.20.21.212:27017,172.20.21.216:27017/DAP_IAT?replicaSet=repl";
		String dbName = "DAP_IAT";
		String collectionName = "NOTIFICATION_EMAIL_HISTORY";
		int targetNotificationId = 2094;
		String json = "{\"NOTIFICATION_ID\" : 2094}";
		
		System.out.println(json);

		MongoDBChecker mongoDBChecker = new MongoDBChecker(mongoURI, dbName, collectionName);
		long recordCount = mongoDBChecker.countRecordsWithNotificationId(json);

		if (recordCount > 0) {
			System.out
					.println("Records with NOTIFICATION_ID " + targetNotificationId + " exist. Count: " + recordCount);
		} else {
			System.out.println("No records found with NOTIFICATION_ID " + targetNotificationId + ".");
		}
		mongoDBChecker.close();

	}
}
