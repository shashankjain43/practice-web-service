package com.snapdeal.cps.model.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;


public class GPCMappingCollection {

	// _id (subCategoryID), subCategoryName, googleProductCategory
	private static final String collectionName = "gpcMapping";
	
	public static String getGoogleProductCategory(DB db, int subCategoryID){
		
		DBCollection collection = db.getCollection(collectionName);
		DBObject dbObject = collection.findOne(new BasicDBObject("_id", subCategoryID));
		if(dbObject!=null){
			return (String) dbObject.get("googleProductCategory");
		} else {
			return null;
		}
		
	}
	
	public static DBObject getCategoryDetails(DB db, int subCategoryID){
		DBCollection collection = db.getCollection(collectionName);
		DBObject dbObject = collection.findOne(new BasicDBObject("_id", subCategoryID));
		return dbObject;
	}
}
