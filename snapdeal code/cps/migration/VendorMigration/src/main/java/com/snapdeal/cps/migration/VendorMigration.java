package com.snapdeal.cps.migration;


import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.google.api.client.http.HttpResponse;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.snapdeal.cps.google.api.GoogleContentAPI;
import com.snapdeal.cps.model.mongo.ErrorPOGCollection;
import com.snapdeal.cps.model.mongo.VendorMappingCollection;
import com.snapdeal.cps.model.mongo.VendorMigrationCollection;

public class VendorMigration {

	/**
	 * @param args
	 */
	
	public static int MAX_SUBACCOUNT_CALLS = 9950;
	
	public static void main(String[] args) {
		
		// Define a binary semaphore file
		File lockFile = new File("./appdata/run.lock");
		try {
			
			// Check if lock already exists
			if(lockFile.exists()){
				System.out.println("A publisher is already running. Exiting ..");
				System.exit(1);
			}
			
			// Create lock and continue
			lockFile.createNewFile();
			
			// Load Properties
			Properties props = new Properties();
			props.load(VendorMigration.class.getClassLoader().getResourceAsStream("migration.properties"));
			
			// Create Mongo Connection
			MongoClient mongoClient = new MongoClient(
										props.getProperty("MONGO_HOSTNAME"), 
										Integer.parseInt(props.getProperty("MONGO_PORT"))
									);
			
			DB mongoDatabase = mongoClient.getDB(props.getProperty("MONGO_DATABASE"));
			
			int i = 0;
			while(i<MAX_SUBACCOUNT_CALLS){
				
				System.out.println("Number of API calls: " + i);
				ArrayList<Object> publishedVendorList = new ArrayList<Object>();
				DBCursor dbCursor = VendorMigrationCollection.getDocuments(mongoDatabase);
				if(dbCursor.count()==0){
					break;
				}
				
				while(dbCursor.hasNext()){

					DBObject dbObject = dbCursor.next();
					
					Object vendorCode = dbObject.get("vendorCode");
					String vendorName = (String) dbObject.get("vendorName");

					if(vendorCode==null || vendorName==null || vendorName.length()==0){
						publishedVendorList.add(vendorCode);
						continue;
					}

					// Check if vendor is already created
					String googleVendorID = VendorMappingCollection.getGoogleVendorID(mongoDatabase, vendorCode.toString());
					
					if(googleVendorID==null){
						GoogleContentAPI apiRequest = new GoogleContentAPI(props);
						System.out.println("Creating vendor account: " + vendorName);
						
						try{
							HttpResponse httpResponse = apiRequest.createSubAccount(vendorName);
							
							// A request has been sent
							i = i + 1;
							
							String xmlString = httpResponse.parseAsString();
							if(xmlString.contains("error")){
								System.out.println("Could not create vendor account for vendorCode: " + vendorCode);
								ErrorPOGCollection.createNewErrorDocument(mongoDatabase, xmlString);
							}

							DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
							DocumentBuilder db = dbf.newDocumentBuilder();
							Document doc = db.parse(new InputSource(new StringReader(xmlString)));

							NodeList nodes = doc.getElementsByTagName("id");
							String vendorURI = nodes.item(0).getFirstChild().getTextContent();
							System.out.println(vendorURI);
							String[] splitedString = vendorURI.split("/");
							googleVendorID = splitedString[splitedString.length-1];

							// Insert in mongoDB
							VendorMappingCollection.createNewDocument(mongoDatabase, vendorCode.toString(), vendorName, googleVendorID);
						}catch(Exception ex){
							System.out.println(ex);
							ex.printStackTrace();
							ErrorPOGCollection.createNewErrorDocument(mongoDatabase, ex.toString());
							continue;
						}
					}
					publishedVendorList.add(vendorCode);
				}
				VendorMigrationCollection.updatePublishedStatus(mongoDatabase, publishedVendorList);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			lockFile.delete();
		}
	}
}
