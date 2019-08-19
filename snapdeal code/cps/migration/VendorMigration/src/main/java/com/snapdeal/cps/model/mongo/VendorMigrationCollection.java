package com.snapdeal.cps.model.mongo;

import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class VendorMigrationCollection {

	// _id, vendorName, vendorCode, isPublished
	
	private static final String collectionName = "vendorMigration";
	
	public static DBCursor getDocuments(DB db){
		DBCollection collection = db.getCollection(collectionName);
		BasicDBObject query = new BasicDBObject("isPublished","N");
		DBCursor dbCursor = collection.find(query).limit(100);
		return dbCursor;
	}
	
	public static void updatePublishedStatus(DB db, ArrayList<Object> vendorList){
		DBCollection collection = db.getCollection(collectionName);
		BasicDBObject query = new BasicDBObject("vendorCode", new BasicDBObject("$in",vendorList));
		BasicDBObject update = new BasicDBObject("$set",new BasicDBObject("isPublished","Y"));
		
		collection.update(query, update, false, true);
	}
}
