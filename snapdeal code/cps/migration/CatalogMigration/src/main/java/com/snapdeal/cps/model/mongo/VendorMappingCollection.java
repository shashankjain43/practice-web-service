package com.snapdeal.cps.model.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class VendorMappingCollection {
	
	//_id (internal vendor Code), vendorName, googleVendorID

	private static final String collectionName = "vendorMapping";
	
	public static String getGoogleVendorID(DB db, String vendorCode){
		DBCollection collection = db.getCollection(collectionName);
		DBObject dbObject = collection.findOne(new BasicDBObject("_id",vendorCode));
		if(dbObject != null){
			return (String) dbObject.get("googleVendorID");
		}else {
			return null;
		}
	}
	
	public static void createNewDocument(DB db, String vendorCode, String vendorName, String googleVendorID){
		DBCollection collection = db.getCollection(collectionName);
		collection.insert(new BasicDBObject("_id", vendorCode)
							.append("vendorName", vendorName)
							.append("googleVendorID", googleVendorID));
						 
	}
	
	
}
