package com.snapdeal.cps.model.mongo;


import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.snapdeal.cps.model.atom.Product;


public class PublishedPOGCollection {
	
	// _id (pogID), imageLink, brand, url, description, title, expirationDate, 
	// internalVendorCode, googleVendorID
	
	private static final String CollectionName = "publishedPOG";

	public static void createNewDocument(DB db,Product product){
		DBCollection collection = db.getCollection(CollectionName);
		BasicDBObject newDocument = new BasicDBObject("_id",product.pogID)
								.append("imageLink", product.imageLink)
								.append("brand", product.brand)
								.append("title",product.title)
								.append("url",product.links.get(0).href.split("\\?")[0]) 		// Store URL without params
								.append("description", product.content.description)
								.append("expirationDate", product.expirationDate)
								.append("internalVendorCode", product.internalVendorCode)
								.append("googleVendorID",product.googleVendorID);
		
		collection.insert(newDocument);
			
	}
	
	public static DBObject getPOGDetails(DB db, Long pogID){
		
		BasicDBObject query = new BasicDBObject("_id",pogID);
		DBCollection dbCollection = db.getCollection(CollectionName);
		DBCursor cursor = dbCollection.find(query);
		if(cursor.hasNext()){
			return cursor.next();
		}else{
			return null;
		}
		
	}
	
	public static void updateInsertPublishedPOG(DB db,Product product){
		
		DBCollection collection = db.getCollection(CollectionName);
		BasicDBObject query = new BasicDBObject("_id",product.pogID);
		DBObject dbObject = collection.findOne(query);
		if(dbObject==null){
			createNewDocument(db, product);
		} else {
			BasicDBObject update = new BasicDBObject("$set",new BasicDBObject("internalVendorCode",product.internalVendorCode)
													.append("googleVendorID",product.googleVendorID)
													.append("expirationDate", product.expirationDate)
													.append("imageLink", product.imageLink)
													.append("description", product.content.description)
													.append("title", product.title));
			collection.update(query, update);
		}
	}
	
}
