package com.snapdeal.cps.model.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class BannedSubCategoryCollection {

	// _id (subCategoryID), platformName
	
	private static final String collectionName = "bannedSubCategory";
	
	public static boolean isSubCategoryBanned(DB db, int subCategoryID, String platformName){
		DBCollection collection = db.getCollection(collectionName);
		DBObject dbObject = collection.findOne(new BasicDBObject("_id", subCategoryID).append("platformName", platformName));
		if(dbObject!=null){
			return true;
		}else{
			return false;
		}
	}
}
