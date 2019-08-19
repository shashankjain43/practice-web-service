package com.snapdeal.cps.model.mongo;

import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class POGVendorListCollection {

	// _id (pogID), vendorList
	
	private static final String collectionName = "pogVendorList";
	
	public static ArrayList<String> getVendorList(DB db, Long pogID){
		DBCollection collection = db.getCollection(collectionName);
		BasicDBObject query = new BasicDBObject("_id",pogID);
		DBObject dbObject = collection.findOne(query);
		if(dbObject!=null){
			return (ArrayList<String>) dbObject.get("vendorList");
		}else {
			return null;
		}
	}

	public static void createNewDocument(DB db, Long pogID, String internalVendorCode){
		DBCollection collection = db.getCollection(collectionName);
		ArrayList<String> vendorList = new ArrayList<String>();
		vendorList.add(internalVendorCode);
		BasicDBObject dbObject = new BasicDBObject("_id", pogID)
								.append("vendorList", vendorList);
		
		collection.insert(dbObject);
		
	}
	
	public static void updateInsertDocument(DB db, Long pogID, String internalVendorCode){
		DBCollection collection = db.getCollection(collectionName);
		BasicDBObject query = new BasicDBObject().append("_id", pogID);
		DBObject dbObject = collection.findOne(query);
		if(dbObject==null){
			createNewDocument(db, pogID, internalVendorCode);
		}else{
			ArrayList<String> vendorList = (ArrayList<String>) dbObject.get("vendorList");
			if(!vendorList.contains(internalVendorCode)){
				vendorList.add(internalVendorCode);
				BasicDBObject update = new BasicDBObject("$set",new BasicDBObject("vendorList",vendorList));
				collection.update(query, update);
			}
			
		}
	}
}
