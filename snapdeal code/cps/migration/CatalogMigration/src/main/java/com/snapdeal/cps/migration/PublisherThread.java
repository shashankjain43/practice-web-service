package com.snapdeal.cps.migration;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;

import com.google.api.client.http.HttpResponse;
import com.mongodb.BasicDBList;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.DBRef;
import com.snapdeal.base.transport.serialization.service.impl.ProtostuffSerializationServiceImpl;
import com.snapdeal.base.transport.service.ITransportService.Protocol;
import com.snapdeal.base.transport.service.impl.TransportServiceImpl;
import com.snapdeal.base.utils.DateUtils;
import com.snapdeal.cps.google.api.GoogleContentAPI;
import com.snapdeal.cps.model.atom.Product;
import com.snapdeal.cps.model.mongo.BannedSubCategoryCollection;
import com.snapdeal.cps.model.mongo.ErrorPOGCollection;
import com.snapdeal.cps.model.mongo.GPCMappingCollection;
import com.snapdeal.cps.model.mongo.POGMigrationVO;
import com.snapdeal.cps.model.mongo.POGVendorListCollection;
import com.snapdeal.cps.model.mongo.ProductCategoryVO;
import com.snapdeal.cps.model.mongo.PublishedPOGCollection;
import com.snapdeal.cps.model.mongo.VendorMappingCollection;
import com.snapdeal.ipms.base.api.getMinInventoryPricing.v0.GetMinInventoryPricingRequest;
import com.snapdeal.ipms.base.api.getMinInventoryPricing.v0.GetMinInventoryPricingResponse;
import com.snapdeal.ipms.base.api.getMinInventoryPricing.v0.MinInventorySRO;
import com.snapdeal.ipms.client.service.impl.IPMSClientServiceImpl;

public class PublisherThread implements Callable<HashMap<String,Integer>>{
	
	private DB db;
	private int limit;
	private int bucketNumber;
	private ArrayList<Long> publishedPOGList;
	private Properties apiProperties;
	private boolean enableDryRun;
	private GoogleContentAPI apiCall;
	private List<Integer> booksCategory;
	
	public PublisherThread(DB db, int bucket, int limit, boolean enableDryRun, Properties apiProperties){
		System.out.println("Creating Thread: " + bucket);
		this.bucketNumber = bucket;
		this.limit = limit;
		this.db = db;
		this.publishedPOGList = new ArrayList<Long>();
		this.apiProperties = apiProperties;
		this.enableDryRun = enableDryRun;
		this.apiCall = new GoogleContentAPI(apiProperties);
		this.booksCategory = Arrays.asList(364, 365, 366, 367, 368, 370, 371, 372, 373, 374, 375, 376, 377, 378, 379, 380, 381, 382, 383, 384, 385, 386, 387, 388, 389, 390, 398, 614);
	}
	
	public void addToPublished(Long pog){
		this.publishedPOGList.add(pog);
	}
	
	public boolean publish(String googleVendorID, Product product){
		boolean status = true;
		
		try {
			HttpResponse response = this.apiCall.insertSingleProduct(googleVendorID, product, this.enableDryRun);
			String xmlString = response.parseAsString();
			if(xmlString.contains("error>")){
				throw(new Exception(xmlString));
			}
		} catch(Exception ex) {
			ErrorPOGCollection.createNewErrorDocument(db, ex.toString());
			status = false;
		}
		return status;
	}
	
	public HashMap<String, Integer> call() throws Exception {
		
		HashMap<String,Integer> resultMap = new HashMap<String, Integer>();
		resultMap.put("bucket", this.bucketNumber);
		resultMap.put("publishedCount", 0);
		resultMap.put("unpublishedCount", 0);
		resultMap.put("publishedErrorCount", 0);
		
		int publishedCount = 0;
		int unpublishedCount = 0;
		int publishedErrorCount = 0;
		
		// Create IPMS Client and register it with TransportService
		IPMSClientServiceImpl ipmsClient = new IPMSClientServiceImpl();
		TransportServiceImpl tsi = new TransportServiceImpl();
		ProtostuffSerializationServiceImpl pssi = new ProtostuffSerializationServiceImpl();
		
		Field protoField = tsi.getClass().getDeclaredField("protostuffSerializationService");
		protoField.setAccessible(true);
		protoField.set(tsi, pssi);
	
		tsi.init();
		
		tsi.registerService("/service/ipms/", "CPSIpmsClient-"+bucketNumber);
		Field transportServField = ipmsClient.getClass().getDeclaredField("transportService");
		transportServField.setAccessible(true);
		transportServField.set(ipmsClient, tsi);
		
		ipmsClient.setWebServiceBaseURL(apiProperties.getProperty("IPMS_CLIENT_URL"));
		
		
		// Read n records from mongo
		DBCursor pogCursor = POGMigrationVO.getDocuments(this.db, this.bucketNumber, this.limit);
		if(pogCursor.count()!=0){
			while(pogCursor.hasNext()){
				DBObject pogObject = pogCursor.next();
				HashMap<String, Object> pogMap = new HashMap<String, Object>();
				
				Long pogID = ((Number) pogObject.get("_id")).longValue();
				
				try {
					
					// Product Offers
					BasicDBList offers = (BasicDBList) pogObject.get("offers");
					DBObject firstLiveOffer = null;
					boolean foundFirstLiveOffer = false;
					
					// List of ordered SUPCs
					ArrayList<String> supcOrderedList = new ArrayList<String>();
					
					Date currentTime = DateUtils.getCurrentTime();
					
					// Expiration Date
					Date expirationDate = currentTime; 
					
					// Check if POG is live, create ordered supc list and calculate expiration date
					boolean isPOGLive = false;
					Iterator<Object> it = offers.iterator();
					while(it.hasNext()){
						boolean isOfferLive = false;
						DBObject offer = (DBObject) it.next();
						Date startTime = (Date) offer.get("startTime");
						Date endTime = (Date) offer.get("endTime");
						
						// Check if offer is live
						if(startTime.before(currentTime) && endTime.after(currentTime) && 
								(((String)offer.get("catalogStatus")).equals("live") || ((String)offer.get("catalogStatus")).equals("prebook"))){
							isOfferLive = true;
							isPOGLive = true;
						}
						if(isOfferLive){
							if(!foundFirstLiveOffer){
								firstLiveOffer = offer;
								foundFirstLiveOffer = true;
							}
							// Calculate expiration date
							if(endTime.after(expirationDate)){
								expirationDate = endTime;
							}
							
							// Create ordered SUPC list
							supcOrderedList.addAll((ArrayList<String>) offer.get("supcs"));
						}
					}
					
					if(!isPOGLive){
						unpublishedCount++;
						// Since POG is not live we can remove it
						System.out.println("POG is not live: " + pogID);
						this.addToPublished(pogID);
						continue;
					}
					
					pogMap.put("_id", pogID);
					pogMap.put("expirationDate", expirationDate);
					
					// Image URL
					String imageUrl = ((ArrayList<String>) firstLiveOffer.get("mainImages")).get(0);
					if(imageUrl.startsWith("imgs/a/")){
						imageUrl = apiProperties.getProperty("OLD_IMAGE_URL_SERVER") + imageUrl;
					}else{
						imageUrl = apiProperties.getProperty("NEW_IMAGE_URL_SERVER") + imageUrl;
					}
					
					pogMap.put("imageLink", imageUrl);
					
					// Brand
					DBRef brandRef = (DBRef) pogObject.get("brand");
					pogMap.put("brand", brandRef.fetch().get("name"));
					
					// Title
					String title = (String) pogObject.get("name");
					if(title.length()<=70){
						title = title.replaceAll("[^a-zA-Z0-9!\"%&'()*+,./:;<=>?@\\_ `{|}-]", "");
					}else {
						String subTitle = title.substring(0, 69);
						title = subTitle.substring(0, subTitle.lastIndexOf(' ')).replaceAll("[^a-zA-Z0-9!\"%&'()*+,./:;<=>?@\\_ `{|}-]", "");
					}
					pogMap.put("title", title);
					
					// Page URL
					String pageUrl = apiProperties.getProperty("SNAPDEAL_URL") + (String) pogObject.get("url");
					
					// Highlights/Description
					String description = "";
					ArrayList<String> highlights = (ArrayList<String>) pogObject.get("highlights");
					for(String text: highlights){
						description = description + " " + text;
					}
					
					description = description.replaceAll("[^a-zA-Z0-9!\"%&'()*+,./:;<=>?@\\_ `{|}-]", "");
					pogMap.put("description", description);
					
					// Get Sub Category ID
					int subCategoryID = -1;
					BasicDBList categories = (BasicDBList) pogObject.get("categories");
					Iterator<Object> ci = categories.iterator();
					while(ci.hasNext()){
						DBRef category = (DBRef) ci.next();
						int categoryID = ((Number) category.getId()).intValue();
						if(!ProductCategoryVO.isParentCategory(db, categoryID)){
							subCategoryID = categoryID;
							break;
						}
						subCategoryID = categoryID;
					}
					
					if(subCategoryID==-1){
						System.out.println("Product Category cannot be derived from DB: " + subCategoryID + " for POGID: " + pogID);
						unpublishedCount++;
						this.addToPublished(pogID);
						continue;
					}
					
					if(BannedSubCategoryCollection.isSubCategoryBanned(db, subCategoryID, "googleAdwords")){
						System.out.println("Product category: " + subCategoryID + " is banned. POG: " + pogID);
						unpublishedCount++;
						this.addToPublished(pogID);
						continue;
					}
					
					// Check for books
					if(booksCategory.contains(subCategoryID)){
						System.out.println("Books Category. Will not publish: " + pogID);
						unpublishedCount++;
						this.addToPublished(pogID);
						continue;
					}
					
					// Get Google Category Mapping and form the complete UTM URL for tracking
					DBObject categoryDetails = GPCMappingCollection.getCategoryDetails(db, subCategoryID);
					if(categoryDetails==null){
						System.out.println("Product Category not found: " + subCategoryID + " for POGID: " + pogID);
						unpublishedCount++;
						this.addToPublished(pogID);
						continue;
					}

					String googleProductCategory = (String) categoryDetails.get("googleProductCategory");
					String categoryID = ((Integer) (((Number) categoryDetails.get("categoryID")).intValue())).toString();
					pageUrl = pageUrl + "?utm_source=" + apiProperties.getProperty("UTM_SOURCE") + "&utm_campaign=" + categoryID + "_" + subCategoryID;
					
					pogMap.put("url",pageUrl);
					pogMap.put("subCategoryID",subCategoryID);
					pogMap.put("googleProductCategory",googleProductCategory);
					
					// Make API Calls to VIPMS system
					GetMinInventoryPricingRequest ipmsRequest = new GetMinInventoryPricingRequest();
					ipmsRequest.setRequestProtocol(Protocol.PROTOCOL_PROTOSTUFF);
					ipmsRequest.setResponseProtocol(Protocol.PROTOCOL_PROTOSTUFF);
					
					ipmsRequest.setOrderedSUPCList(supcOrderedList);
					
					GetMinInventoryPricingResponse ipmsResponse = new GetMinInventoryPricingResponse();
					ipmsResponse = ipmsClient.getMinInventoryPricing(ipmsRequest);
					
					if(ipmsResponse.isSuccessful()){
						
						if(ipmsResponse.getMinPricingSRO() != null){
							
							// Availability
							boolean isPOGAvailable = false;
							Collection<MinInventorySRO> minInventoryList = ipmsResponse.getSupcToMinInventoryMap().values();
							for(MinInventorySRO inventorySRO: minInventoryList){
								if(!inventorySRO.isSoldOut()){
									pogMap.put("availability", "in stock");
									isPOGAvailable = true;
									break;
								}
							}
							if(!isPOGAvailable){
								pogMap.put("availability", "out of stock");
							}
							
							// Price
							Integer price = null;
							if (ipmsResponse.getMinPricingSRO().getSellingPrice() != null)
								price = ipmsResponse.getMinPricingSRO().getSellingPrice();
							else{
								price = ipmsResponse.getMinPricingSRO().getInitialPrice();
							}
							
							// Adjust internal cashback
							if(ipmsResponse.getMinPricingSRO().isInternalCashbackApplicable() && price != null) {
								Float internalCashback = ipmsResponse.getMinPricingSRO().getTotalInternalCashbackAbsoluteValue();
								price = ((Float) (price.floatValue() - internalCashback)).intValue();
							}
							if(price==null){
								unpublishedCount++;
								continue;
							}
							pogMap.put("price", price);
							
							// Vendor
							String internalVendorCode = ipmsResponse.getMinPricingSRO().getVendorCode();
							String googleVendorID = VendorMappingCollection.getGoogleVendorID(db, internalVendorCode);
							if(googleVendorID==null){
								System.out.println("Google Vendor ID for " + internalVendorCode + " not obtained from DB. POG: " + pogID);
								unpublishedCount++;
								// Since the product has a new vendor the publishing will be taken care by the incremental job
								this.addToPublished(pogID);
								continue;
							}
							pogMap.put("internalVendorCode", internalVendorCode);
							pogMap.put("googleVendorID", googleVendorID);
							
							// Create single product entry object
							Product product = new Product(pogMap, false);
							
							System.out.println("Sending request for POG: " + pogID);
							
							// Publish corresponding to the primary vendor
							boolean status = publish(googleVendorID, product);
							publishedCount++;
							if(!status){
								publishedErrorCount++;
								continue;
							}else{
								PublishedPOGCollection.updateInsertPublishedPOG(db, product);
								POGVendorListCollection.updateInsertDocument(db, pogID, internalVendorCode);
								// Add to published
								this.addToPublished(pogID);
							}
							
							ArrayList<String> vendorList = POGVendorListCollection.getVendorList(db, pogID);
							for(String vendor: vendorList){
								if(!vendor.equals(internalVendorCode)){
									String googleID = VendorMappingCollection.getGoogleVendorID(db, vendor);
									pogMap.put("internalVendorCode", vendor);
									pogMap.put("googleVendorID", googleID);
									pogMap.put("availability", "out of stock");
									
									boolean published = publish(googleID, new Product(pogMap, false));
									publishedCount++;
									if(!published){
										publishedErrorCount++;
									}
								}
							}
						}else {
							System.out.println("IPMS Response is null for POGID: " + pogID);
							unpublishedCount++;
							// Since no information has been obtained from the IPMS
							this.addToPublished(pogID);
						}
					}else {
						System.out.println("IPMS Response unsuccessful for POGID: " + pogID);
						unpublishedCount++;
					}
				} catch(Exception ex){
					System.out.println("Could not publish for: " + pogID);
					ErrorPOGCollection.createNewErrorDocument(db, ex.toString());
					ex.printStackTrace();
				}
			}	
			POGMigrationVO.removePublished(this.db, this.publishedPOGList);
		}else {
			System.out.println("No more POG IDs ending with " + this.bucketNumber);
		}
		
		// Form result Map
		resultMap.put("publishedCount", publishedCount);
		resultMap.put("unpublishedCount", unpublishedCount);
		resultMap.put("publishedErrorCount", publishedErrorCount);
		
		return resultMap;
	}

}
