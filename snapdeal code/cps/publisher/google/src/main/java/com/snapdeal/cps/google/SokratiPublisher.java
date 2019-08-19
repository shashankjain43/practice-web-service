/**
 *  Copyright 2014 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */  
package com.snapdeal.cps.google;

import java.io.File;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.google.api.client.http.HttpResponse;
import com.snapdeal.cps.common.dto.GoogleProductDTO;
import com.snapdeal.cps.common.dto.GoogleSellerDTO;
import com.snapdeal.cps.common.dto.SokratiProductDTO;
import com.snapdeal.cps.common.mongo.LastRunInfo;
import com.snapdeal.cps.common.service.CategoryService;
import com.snapdeal.cps.common.service.GoogleShoppingDataService;
import com.snapdeal.cps.common.service.ProcessUtilityService;
import com.snapdeal.cps.common.utils.FTPUtils;
import com.snapdeal.cps.common.utils.PogCsvWriter;
import com.snapdeal.cps.google.api.ErrorCodes;
import com.snapdeal.cps.google.api.GoogleContentAPI;

/**
 *  
 *  @version     1.0, 13-Aug-2014
 *  @author Shashank Jain(jain.shashank@snapdeal.com)
 */
@Component("sokratiPublisher")
public class SokratiPublisher {
    private static final Logger log = LoggerFactory.getLogger("sokratiPublisher.logger");
    
    //Define property constants
    private static final String FILE_NAME = "fileName";
    private static final String FILE_TYPE = "type";
    private static final String FILE_PATH = "filePath";
    private static final String FILE_SEPARATOR = "separator";
    private static final String RECORD_LIMIT = "recordLimit";
    private static final String CATEGORY_LIST = "categories";
    private static final String MIN_DATE = "minDate";
    private static final String FTP_HOST = "ftpHost";
    private static final String FTP_PORT = "ftpPort";
    private static final String FTP_USER = "ftpUser";
    private static final String FTP_PASS = "ftpPass";
    private static final String FTP_DIR = "ftpDir";
    private static final String FILE_FIELD_MAPPING = "fieldMapping";
    private static final String TEST_SELLER_CODES = "testSellers";
    private static final String PROCESS_NAME = "sokratiPublisher";
    
    private boolean isProdEnv;
    private GoogleContentAPI gmcApiClient;
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private ProcessUtilityService processUtilityService;
    
    @Autowired
    private GoogleShoppingDataService googleShoppingDataService;
    
    @PostConstruct
    public void init(){
        log.info("Initializing Sokrati publisher");
        this.gmcApiClient = new GoogleContentAPI();

        // Check if running in production environment
        String environment = System.getProperty("env");
        if(environment != null && environment.equals("prod")){
            this.isProdEnv = true;
        }else{
            this.isProdEnv = false;
        }
    }
    
    private void updateLastRun(Date runTs){
        log.info("Updating Last Run Ts");
        processUtilityService.updateLastRunTs(PROCESS_NAME, runTs);
    }
    
    private void passTestSellerProducts(List<String> testSellers, List<GoogleProductDTO> googleProducts){
        Iterator<GoogleProductDTO> it = googleProducts.iterator();
        while(it.hasNext()){
            GoogleProductDTO productDTO = it.next();
            for(String sellerCode: productDTO.getSellers().keySet()){
                if(!testSellers.contains(sellerCode)){
                    it.remove();
                }
            }
        }
    }
    
    public void publish(){
        
        // List of seller codes for which account creation request failed
        List<String> errorSellers;
        
        Date runTs = Calendar.getInstance().getTime();
        log.info("START: Publisher Name: " + PROCESS_NAME);
        
        LastRunInfo lastRunInfo = processUtilityService.getLastRunInfoByProcessName(PROCESS_NAME);
        if(lastRunInfo == null){
            log.error("Could not obtain last run timestamp.Exiting");
            return;
        }
        
        Date lastRunTs = lastRunInfo.getLastRunTs();
        
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date maxTs = null;
        int fileNumber = 0;
        int fileFtpCount = 0;
        try{
            maxTs = df.parse(processUtilityService.getProcessProperty(PROCESS_NAME, MIN_DATE));
            log.info("Fetched min date is : {}",maxTs);
            
            // Get categories
            String categories = processUtilityService.getProcessProperty(PROCESS_NAME, CATEGORY_LIST);
            if(categories == null || categories.isEmpty()){
                log.error("No categories fetched. Exiting.");
                updateLastRun(runTs);
                return;
            }
            List<String> categoryList = Arrays.asList(categories.split(","));

            List<Integer> validSubCategories = new ArrayList<Integer>();
            for(String category: categoryList){
                List<Integer> subCategories = categoryService.getSubCategoriesByCategoryId(Integer.parseInt(category));
                if(subCategories == null || subCategories.isEmpty()){
                    continue;
                }
                validSubCategories.addAll(subCategories);
            }
            
            // Get file name
            String fileName = processUtilityService.getProcessProperty(PROCESS_NAME, FILE_NAME);
            if(fileName == null || fileName.length() == 0){
                log.error("No file name defined. Exiting.");
                updateLastRun(runTs);
                return;
            }
            
            // Get file path
            String filePath = processUtilityService.getProcessProperty(PROCESS_NAME, FILE_PATH);
            if(filePath == null || filePath.length() == 0){
                log.warn("File path not defined. Using current directory");
                filePath = "";
            }
            
            // Get file type
            String fileType = processUtilityService.getProcessProperty(PROCESS_NAME, FILE_TYPE);
            if(fileType == null){
                log.error("File type not defined. Exiting");
                updateLastRun(runTs);
                return;
            }
            
            // Get field column mapping
            String fieldMap = processUtilityService.getProcessProperty(PROCESS_NAME, FILE_FIELD_MAPPING);
            
            if(fieldMap == null || fieldMap.length() == 0){
                log.error("No field mapping available. Exiting");
                updateLastRun(runTs);
                return;
            }
            
            //Get record limit of file
            int recordLimit = Integer.parseInt(processUtilityService.getProcessProperty(PROCESS_NAME, RECORD_LIMIT));
            if(recordLimit <= 0){
                log.error("File record limit is not available. Exiting");
                updateLastRun(runTs);
                return;
            }
            
            // Get separator
            char separator = processUtilityService.getProcessProperty(PROCESS_NAME, FILE_SEPARATOR).charAt(0);
            
            errorSellers = new ArrayList<String>();
            
            do{
                log.info("*********************************************************************");
                log.info("Latest timestamp for current iteration is : {}", maxTs);
                // Initialize data service for preparing publishing data
                List<GoogleProductDTO> googleProducts = googleShoppingDataService.getUpdatedProductListBySubCategoryList(maxTs,validSubCategories,recordLimit,log);
                if(googleProducts == null || googleProducts.isEmpty()){
                    log.info("No further GoogleProductDTOs obtained from DB. Exiting the loop");
                    break;
                }
                log.info("GoogleProductDTOs obtained from DB: " + googleProducts.size());
                
                // Calculate timestamp for next ftp
                maxTs = googleProducts.get(googleProducts.size()-1).getUpdateTs();
                
                if(!isProdEnv){
                    String[] testSellerArray = processUtilityService.getProcessProperty(PROCESS_NAME, TEST_SELLER_CODES).split(",");
                    List<String> testSellerCodes = new ArrayList<String>(Arrays.asList(testSellerArray));
                    passTestSellerProducts(testSellerCodes, googleProducts);
                    log.info("Test GoogleProductDTOs obtained from DB: " + googleProducts.size());
                }
                
                if(googleProducts.isEmpty()){
                    continue;
                }
                
                // Create list of PogInfoSROs
                List<Object> pogDTOList = prepareDataForPublishing(googleProducts,errorSellers);
                
                if(pogDTOList.isEmpty()){
                    continue;
                }
                // Create file object
                File file = new File(filePath, fileName + Integer.toString(fileNumber) + "." + fileType);
                
                // Write the Pog info to a file
                try{
                    PogCsvWriter csvWriter = new PogCsvWriter(file, separator, fieldMap);
                    csvWriter.writeToFile(pogDTOList);
                    fileNumber++;
                }catch(Exception ex){
                    log.error("Error while creating the file.");
                    log.error(ExceptionUtils.getFullStackTrace(ex));
                    return;
                }
                
                // Publish File to FTP server
                log.info("Uploading file to FTP");
                boolean isTransferSuccessful = false;
                try{
                    
                    isTransferSuccessful = FTPUtils.uploadFileToFtp(file, 
                                                    processUtilityService.getProcessProperty(PROCESS_NAME, FTP_HOST),
                                                    Integer.parseInt(processUtilityService.getProcessProperty(PROCESS_NAME, FTP_PORT)),
                                                    processUtilityService.getProcessProperty(PROCESS_NAME, FTP_USER), 
                                                    processUtilityService.getProcessProperty(PROCESS_NAME, FTP_PASS), 
                                                    processUtilityService.getProcessProperty(PROCESS_NAME, FTP_DIR)
                                               );
                    
                } catch (Exception ex) {
                    log.error("Error while transferring the file");
                    log.error(ExceptionUtils.getFullStackTrace(ex));
                }
                
                if(isTransferSuccessful){
                    log.info("File successfully copied to FTP server");
                    fileFtpCount++;
                    
                    // Update Product Listing
                    if(maxTs.compareTo(lastRunTs)>0){
                        for(Object pogDTO: pogDTOList){
                            SokratiProductDTO sokratiDTO = (SokratiProductDTO) pogDTO;
                            if(sokratiDTO.getUpdateTs().compareTo(lastRunTs) > 0 && sokratiDTO.isPrimary()){
                                googleShoppingDataService.updateProductListing(sokratiDTO.getPogId(), sokratiDTO.getSubAccountId());
                            }
                        }
                    }
                    
                    
                }else {
                    log.error("Upload to FTP failed");
                }
            } while(true);
        }catch(Exception ex){
            log.error("Unhandled Exception");
            log.error(ExceptionUtils.getFullStackTrace(ex));
        }
        
        // Update
        updateLastRun(runTs);
        log.info("*********************************************************************");
        log.info("Total {} files copied to FTP server",fileFtpCount);
        log.info("END: Sokrati Publisher run complete");
    }
        
    private String createSubaccountOnGoogle(String sellerName) throws Exception {
        log.info("Creating seller account: " + sellerName);
        HttpResponse response = gmcApiClient.createSubAccount(sellerName);
        String xmlString = response.parseAsString();
        if (xmlString.contains("<error>")) {
            throw new Exception(xmlString);
        }

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new InputSource(new StringReader(xmlString)));

        NodeList nodes = doc.getElementsByTagName("id");
        String subaccountURI = nodes.item(0).getFirstChild().getTextContent();
        log.info(subaccountURI);
        String[] splitedString = subaccountURI.split("/");
        return splitedString[splitedString.length - 1];
    }

    private List<Object> prepareDataForPublishing(List<GoogleProductDTO> googleProducts,List<String> errorSellers){
        
        List<Object> pogDTOList = new ArrayList<Object>();
        boolean hasSubaccountLimitReached = false;
        
        for(GoogleProductDTO googleProductDTO: googleProducts){
            
            if(!googleProductDTO.isValid()){
                continue;
            }
            
            long pogId = googleProductDTO.getPogId();
            
            try {
                for(GoogleSellerDTO seller: googleProductDTO.getSellers().values()){
                    String sellerCode = seller.getSellerCode();
                    if(errorSellers.contains(sellerCode)){
                        continue;
                    }

                    String gsId = seller.getSubaccountId();
                    if(gsId==null || gsId.isEmpty()){
                        try{
                            gsId = googleShoppingDataService.getGoogleSubaccountIdBySellerCode(sellerCode);
                            if(gsId==null || gsId.isEmpty()){
                                if(!hasSubaccountLimitReached){
                                    gsId = createSubaccountOnGoogle(seller.getSellerName());
                                    googleShoppingDataService.createSubaccount(sellerCode, gsId);
                                }else{
                                    continue;
                                }
                                
                            }
                            
                        }catch(Exception ex){
                            log.error("Unable to create google sub account for the seller: " + sellerCode);
                            log.error(ExceptionUtils.getFullStackTrace(ex));
                            processUtilityService.logError("seller", sellerCode, "Unable to create seller account");
                            errorSellers.add(sellerCode);
                            
                            // Check for too_many_subaccounts error
                            if(ex.getMessage().contains(ErrorCodes.TOO_MANY_SUBACCOUNTS.getMsg())){
                                log.error("Google subAccount creation limit has crossed ");
                                hasSubaccountLimitReached = true;
                            }
                            
                            continue;
                        }
                    }
                    
                    String title = googleProductDTO.getTitle();
                    // Add keywords to description
                    String description = googleProductDTO.getDescription();
                    String keywords = googleProductDTO.getKeywords();
                    if(keywords != null && !keywords.isEmpty()){
                        description = title + ". "+ description + ". Other Details: " + keywords;
                    }
                    // Append params to PDP URL
                    UtmParams utmParams = new UtmParams();
                    utmParams.setUtmCampaign(googleProductDTO.getParentCategoryId() + "_" + googleProductDTO.getSubCategoryId());
                    utmParams.setUtmMedium(gsId);

                    String url = googleProductDTO.getUrl() + "?" + utmParams.toString() + "&vendorCode=" + sellerCode;
                    String mobileUrl = googleProductDTO.getMobileUrl() + "?" + utmParams.toString();
                    
                    
                    //create sokrati product
                    SokratiProductDTO pogDTO = new SokratiProductDTO(pogId, title, description, url, "insert");
                    pogDTO.setBrand(googleProductDTO.getBrand());
                    pogDTO.setmLink(mobileUrl);
                    pogDTO.setImageLink(googleProductDTO.getImageUrl());
                    pogDTO.setProductType(googleProductDTO.getProductType());
                    pogDTO.setGoogleProductCategory(googleProductDTO.getGoogleProductCategory());
                    pogDTO.setUpdateTs(googleProductDTO.getUpdateTs());
                    
                    String adWordsGroup = googleProductDTO.getAdwordsGroup();
                    if(adWordsGroup!=null && !adWordsGroup.isEmpty()){
                        pogDTO.setAdwordsGroup(adWordsGroup);
                    }
                    
                    pogDTO.setAvailability(seller.getAvailability());
                    pogDTO.setPrimary(seller.isPrimary());
                    pogDTO.setPrice(googleProductDTO.getPrice());
                    pogDTO.setSubAccountId(gsId);
                    
                    String ageGroup = googleProductDTO.getAgeGroup();
                    if(ageGroup!=null && !ageGroup.isEmpty()){
                        pogDTO.setAgeGroup(ageGroup);
                    }
                    
                    String gender = googleProductDTO.getGender();
                    if(gender!=null && !gender.isEmpty()){
                        pogDTO.setGender(gender);
                    }
                    
                    List<String> labels = googleProductDTO.getCustomLabels();
                    if(labels != null && !labels.isEmpty()){
                        for(int i=0;i<labels.size();i++){
                            switch (i) {
                                case 0:
                                    String customLabel0 = labels.get(i);
                                    if(customLabel0 != null && !customLabel0.isEmpty()){
                                        pogDTO.setCustomLabel0(customLabel0);
                                    }
                                    break;
                                case 1:
                                    String customLabel1 = labels.get(i);
                                    if(customLabel1 != null && !customLabel1.isEmpty()){
                                        pogDTO.setCustomLabel1(customLabel1);
                                    }
                                    break;
                                case 2:
                                    String customLabel2 = labels.get(i);
                                    if(customLabel2 != null && !customLabel2.isEmpty()){
                                        pogDTO.setCustomLabel2(customLabel2);
                                    }
                                    break;
                                case 3:
                                    String customLabel3 = labels.get(i);
                                    if(customLabel3 != null && !customLabel3.isEmpty()){
                                        pogDTO.setCustomLabel3(customLabel3);
                                    }
                            }
                        }
                    }
                    
                    pogDTOList.add(pogDTO);
                    
                }
            } catch (Exception ex) {
                log.error("Error for POG: " + pogId);
                log.error(ExceptionUtils.getFullStackTrace(ex));
                // Log for audit and action
                processUtilityService.logError("product", ""+pogId, "Unable to insert in file"); 
            }            
        }
        return pogDTOList;
    }
    
}