package com.snapdeal.cps.model.mongo;

import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class POGMigrationVO {

	// _id, url, name, highlights, offers, categories, brand, bucket
	
	private static final String collectionName = "pogMigrationVO";
	
	public static DBCursor getDocuments(DB db, int bucket, int limit){
		DBCollection collection = db.getCollection(collectionName);
		BasicDBObject query = new BasicDBObject("bucket", bucket);
		DBCursor dbCursor = collection.find(query).limit(limit);
		return dbCursor;
	}
	
	public static void removePublished(DB db, ArrayList<Long> pogList){
		DBCollection collection = db.getCollection(collectionName);
		BasicDBObject query = new BasicDBObject("_id", new BasicDBObject("$in", pogList));
		collection.remove(query);
	}
}
