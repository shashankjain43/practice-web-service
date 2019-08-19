package com.snapdeal.cps.model.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class ProductCategoryVO {

	// _id, parentCategoryId
	
	private static final String collectionName = "productCategoryVO";

	public static boolean isParentCategory(DB db,int categoryID){
		DBCollection collection = db.getCollection(collectionName);
		BasicDBObject query = new BasicDBObject("_id", categoryID);
		DBObject dbObject = collection.findOne(query);
		if(dbObject!=null && dbObject.containsField("parentCategoryId")){
			return false;
		}else{
			return true;
		}
		
		
	}
}
