package com.snapdeal.cps.model.mongo;

import java.util.Calendar;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

public class ErrorPOGCollection {

	// _id, errorDescription, errorTs
	
	private static final String collectionName = "errorPOG";
	
	public static void createNewErrorDocument(DB db, String responseXML){
		
		DBCollection collection = db.getCollection(collectionName);
		collection.insert(new BasicDBObject("errorDescription", responseXML)
					.append("errorTs", Calendar.getInstance().getTime()));
	}
}
