/**
 *  Copyright 2014 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.snapdeal.cps.google;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.google.api.client.http.HttpResponse;
import com.snapdeal.cps.common.dto.GoogleProductDTO;
import com.snapdeal.cps.common.dto.GoogleSellerDTO;
import com.snapdeal.cps.common.mongo.ProductInfo;
import com.snapdeal.cps.common.service.GoogleShoppingDataService;
import com.snapdeal.cps.common.service.ProcessUtilityService;
import com.snapdeal.cps.google.api.ErrorCodes;
import com.snapdeal.cps.google.api.GoogleContentAPI;
import com.snapdeal.cps.google.atom.GoogleShoppingItem;
import com.snapdeal.cps.google.atom.ProductFeed;

/**
 *  @version   1.0, Sep 10, 2014
 *  @author 	Sushant Taneja <sushant.taneja@snapdeal.com>
 */

@Service("googlePublisherService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "prototype")
public class GooglePublisherService {
    
    // Instance variables
    private GoogleContentAPI gmcApiClient;
    
    // Not a good idea !!
    // Declared here to make it available across all functions
    // Is reset on every run
    private boolean hasSubaccountLimitReached;
    
    @Autowired
    private GoogleShoppingDataService googleShoppingDataService;

    @Autowired
    private ProcessUtilityService processUtilityService;

    @PostConstruct
    public void init(){
        this.gmcApiClient = new GoogleContentAPI();
    }

    private String createSubaccountOnGoogle(String sellerName, Logger log) throws Exception {
        log.info("Creating seller account: " + sellerName);
        HttpResponse response = gmcApiClient.createSubAccount(sellerName);
        String xmlString = response.parseAsString();
        if (xmlString.contains("<error>")) {
            throw new Exception(xmlString);
        }
        
        // Parse response to get subaccount ID
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new InputSource(new StringReader(xmlString)));
        NodeList nodes = doc.getElementsByTagName("id");
        String subaccountURI = nodes.item(0).getFirstChild().getTextContent();
        log.info(subaccountURI);
        String[] splitedString = subaccountURI.split("/");
        return splitedString[splitedString.length - 1];
    }

    private void insertInRequestMap(List<GoogleShoppingItem> shoppingFeedItems, Map<String, List<GoogleShoppingItem>> requestMap) {
        for(GoogleShoppingItem item: shoppingFeedItems){
            String subaccountId = item.getSubaccountId();
            if(!requestMap.containsKey(subaccountId)){
                requestMap.put(subaccountId, new ArrayList<GoogleShoppingItem>());
            }
            requestMap.get(subaccountId).add(item);
        }
    }

    private List<GoogleShoppingItem> getGoogleShoppingFeedItems(GoogleProductDTO product, Logger log){
        
        List<GoogleShoppingItem> feedItems = new ArrayList<GoogleShoppingItem>();
        
        for(Entry<String, GoogleSellerDTO> sellerMap: product.getSellers().entrySet()){
            
            String sellerCode = sellerMap.getKey();
            String gsId = sellerMap.getValue().getSubaccountId();
            
            try{
                // Check if new seller
                if(gsId == null || gsId.isEmpty()){
                    gsId = googleShoppingDataService.getGoogleSubaccountIdBySellerCode(sellerCode);
                    if(gsId == null || gsId.isEmpty()){
                        // Create new subaccount
                        if(!this.hasSubaccountLimitReached){
                            gsId = createSubaccountOnGoogle(sellerMap.getValue().getSellerName(), log);
                            googleShoppingDataService.createSubaccount(sellerCode, gsId);
                        }else{
                            return null;
                        }
                    }
                }
            }catch(Exception ex){
                log.error("Unable to create google sub account for the seller: " + sellerCode);
                log.error(ExceptionUtils.getFullStackTrace(ex));
                processUtilityService.logError("seller", sellerCode, "Unable to create seller account");

                // Check for too_many_subaccounts error
                if(ex.getMessage().contains(ErrorCodes.TOO_MANY_SUBACCOUNTS.getMsg())){
                    this.hasSubaccountLimitReached = true;
                }
                return null;
            }
            
            // Add title and keywords to description
            String description = product.getTitle()+ ". " + product.getDescription();
            String keywords = product.getKeywords();
            if(keywords != null && !keywords.isEmpty()){
                description = description + ". Other Details: " + keywords;
            }

            // Append params to PDP URL
            UtmParams utmParams = new UtmParams();
            utmParams.setUtmCampaign(product.getParentCategoryId() + "_" + product.getSubCategoryId());
            utmParams.setUtmMedium(gsId);

            String url = product.getUrl() + "?" + utmParams.toString() + "&vendorCode=" + sellerCode;
            String mobileUrl = product.getMobileUrl() + "?" + utmParams.toString();

            // Create Google shopping item
            GoogleShoppingItem item = new GoogleShoppingItem(product.getPogId(), product.getTitle(), description, url, "insert", true);
            item.setImageLink(product.getImageUrl());
            item.setBrand(product.getBrand());
            item.setmLink(mobileUrl);
            item.setGoogleProductCategory(product.getGoogleProductCategory());
            item.setPrice(product.getPrice());
            item.setProductType(product.getProductType());
            item.setAvailability(sellerMap.getValue().getAvailability());
            item.setSubaccountId(gsId);
            item.setPrimary(sellerMap.getValue().isPrimary());

            String ageGroup = product.getAgeGroup();
            if(ageGroup!=null && !ageGroup.isEmpty()){
                item.setAgeGroup(ageGroup);
            }

            String gender = product.getGender();
            if(gender!=null && !gender.isEmpty()){
                item.setGender(gender);
            }

            String adGroup = product.getAdwordsGroup();
            if(adGroup!=null && !adGroup.isEmpty()){
                item.setAdwordsGrouping(adGroup);
            }
            

            List<String> labels = product.getCustomLabels();
            if(labels != null && !labels.isEmpty()){
                for(int i=0;i<labels.size();i++){
                    switch (i) {
                        case 0:
                            item.setCustomLabel0(labels.get(i));
                            break;
                        case 1:
                            item.setCustomLabel1(labels.get(i));
                            break;
                        case 2:
                            item.setCustomLabel2(labels.get(i));
                            break;
                        case 3:
                            item.setCustomLabel3(labels.get(i));
                            break;
                    }
                }
            }

            feedItems.add(item);
        }
        
        return feedItems;
    }

    private List<Long> checkErrorInBatchResponse(String xmlString,Logger log) throws Exception {
        List<Long> failPOGs = new ArrayList<Long>();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new InputSource(new StringReader(xmlString)));
        NodeList nodes = doc.getElementsByTagName("entry");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element entryNode = (Element) nodes.item(i);
            NodeList gdErrorTagList = entryNode.getElementsByTagName("gd:error");
            if(gdErrorTagList == null || gdErrorTagList.getLength() == 0){
                continue;
            }

            long pogId = 0;
            for(int index=0;index<gdErrorTagList.getLength();index++){
                //Element errorNode = (Element) gdErrorTagList.item(index);
                String batchId = entryNode.getElementsByTagName("batch:id").item(0).getTextContent();
                pogId = Long.parseLong(batchId);
            }
            if(pogId != 0){
                failPOGs.add(pogId);
            }
        }
        return failPOGs;
    }

    private List<Long> publish(Map<String, List<GoogleShoppingItem>> requestMap,Logger log) throws Exception{
        
        if (requestMap.isEmpty()) {
            log.info("Request Map Empty");
            return null;
        }
        
        int requestNumber = 0;
        boolean isRequestOverflow = false;
        List<Long> failPOGs = new ArrayList<Long>();
        
        for (Map.Entry<String, List<GoogleShoppingItem>> entry : requestMap.entrySet()) {
            // Create feed for each batch request
            List<ProductFeed> batchRequestFeedList = new ArrayList<ProductFeed>();
            List<GoogleShoppingItem> pList = entry.getValue();
            
            if(isRequestOverflow){
                // Add all items to failedPogList
                for(GoogleShoppingItem item: pList){
                    failPOGs.add(item.getId());
                }
                continue;
            }
            
            log.info("Sending request for subaccount: " + entry.getKey());
            int numberOfBatches = 1;
            int pLength = pList.size();
            if (pLength <= 50) {
                batchRequestFeedList.add(new ProductFeed(pList));
            } else {
                numberOfBatches = pLength / 50;
                if (pLength % 50 != 0) {
                    numberOfBatches++;
                }
                // Create feed batches of max 50 entries
                for (int i = 0; i < numberOfBatches; i++) {
                    batchRequestFeedList.add(new ProductFeed(pList.subList(i * 50,Math.min((i + 1) * 50, pLength))));
                }
            }

            log.info("Number of batches: " + numberOfBatches);
            for(int i=0;i < batchRequestFeedList.size();i++){
                
                requestNumber++;
                log.info("Request Number = " + requestNumber);
                log.info(batchRequestFeedList.get(i).toString());

                try{
                    // Send batch requests
                    HttpResponse response = this.gmcApiClient.executeProductBatchOperations(entry.getKey(), batchRequestFeedList.get(i), false);
                    String responseXML = response.parseAsString();
                    if (responseXML.contains("error>")) {
                        log.error("Error in response");
                        processUtilityService.logError("response", entry.getKey() + ":" + requestNumber, responseXML);
                        
                        failPOGs.addAll(checkErrorInBatchResponse(responseXML,log));
                        
                        //check for quota errors in batch response
                        if(responseXML.contains(ErrorCodes.TOO_MANY_ITEMS.getMsg())){
                            log.error("Google item limit crossed for account: "+entry.getKey());
                            //isAccountOverflow = true;
                            break;
                        }
                        if(responseXML.contains(ErrorCodes.TOO_MANY_REQUESTS.getMsg())){
                            log.error("Google rate-limit crossed...Adding further products in failedList");
                            isRequestOverflow = true;
                        }
                        
                        //adding remaining POGs of current google subAcoount
                        if(isRequestOverflow){
                            if(i < batchRequestFeedList.size()){
                                for(int j=i+1;j<batchRequestFeedList.size();j++){
                                    // Add all items to failedPogList
                                    for(GoogleShoppingItem item: batchRequestFeedList.get(j).entries){
                                        failPOGs.add(item.getId());
                                    }
                                }
                            }
                            break;
                        }
                    }
                }catch(Exception ex){
                    log.error("Error while sending request to Google");
                    log.error(ExceptionUtils.getFullStackTrace(ex));
                    processUtilityService.logError("response", entry.getKey() + ":" + requestNumber, ex.toString());

                    // Add all items to failedPogList
                    for(GoogleShoppingItem item: batchRequestFeedList.get(i).entries){
                        failPOGs.add(item.getId());
                    }
                    
                    continue;
                }

                // Update Product Listing in DB
                for(GoogleShoppingItem item: batchRequestFeedList.get(i).entries){
                    if(!failPOGs.contains(item.getId()) && item.isPrimary()){
                        googleShoppingDataService.updateProductListing(item.getId(),entry.getKey());
                    }
                }
            }
        }
        return failPOGs;
    }
    
    public List<Long> publishOnGMC(List<ProductInfo> productInfoList, Logger log) throws Exception{
        
        Map<String, List<GoogleShoppingItem>> requestMap = new HashMap<String, List<GoogleShoppingItem>>();
        
        // Collection of seller codes for which account creation request failed
        Set<String> errorSellers= new HashSet<String>();
        
        // List of POG Ids for which request has failed
        List<Long> failedPogList = new ArrayList<Long>();

        // Reset instance variable
        this.hasSubaccountLimitReached = false;
        
        // Initialise data service for preparing publishing data
        List<GoogleProductDTO> googleProducts = googleShoppingDataService.getGoogleProductListByProductInfoList(productInfoList, log);
        if(googleProducts == null || googleProducts.isEmpty()){
            log.error("No GoogleProductDTO data obtained . Exiting with error");
            return null;
        }
        
        log.info("GoogleProductDTOs obtained: " + googleProducts.size());
        
        // Create google shopping item and request map from obtained data
        for(GoogleProductDTO googleProduct: googleProducts){
            if(!googleProduct.isValid()){
                failedPogList.add(googleProduct.getPogId());
                continue;
            }
            
            for(String sellerCode : googleProduct.getSellers().keySet()){
                if(errorSellers.contains(sellerCode)){
                    failedPogList.add(googleProduct.getPogId());
                    continue;
                }
            }
            
            List<GoogleShoppingItem> shoppingFeedItems = getGoogleShoppingFeedItems(googleProduct, log);
            if(shoppingFeedItems == null || shoppingFeedItems.isEmpty()){
                // Add seller codes to errorSellers Set
                for(String sellerCode : googleProduct.getSellers().keySet()){
                    errorSellers.add(sellerCode);
                }
                failedPogList.add(googleProduct.getPogId());
                continue;
            }
            
            // Insert in request map
            insertInRequestMap(shoppingFeedItems, requestMap);
        }
        
        //Publish
        List<Long> failList = publish(requestMap,log);
        if(failList != null && !failList.isEmpty()){
            failedPogList.addAll(failList);
        }
        
        return failedPogList;
    }
    
}