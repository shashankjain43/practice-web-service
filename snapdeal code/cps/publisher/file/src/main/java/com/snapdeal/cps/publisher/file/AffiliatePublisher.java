package com.snapdeal.cps.publisher.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.snapdeal.cps.common.dto.PublisherProductDTO;
import com.snapdeal.cps.common.mongo.CategoryInfo;
import com.snapdeal.cps.common.mongo.ProductInfo;
import com.snapdeal.cps.common.service.CategoryService;
import com.snapdeal.cps.common.service.ProcessUtilityService;
import com.snapdeal.cps.common.service.ProductService;
import com.snapdeal.cps.common.utils.FTPUtils;
import com.snapdeal.cps.common.utils.PogCsvWriter;
import com.snapdeal.cps.common.utils.PogXmlWriter;

/**
 * @author Sushant Taneja <sushant.taneja@snapdeal.com>
 *
 */

public class AffiliatePublisher {

    // Define property constants
    private static final String FILE_NAME = "fileName";
    private static final String FILE_TYPE = "type";
    private static final String FILE_PATH = "filePath";
    private static final String FILE_SEPARATOR = "separator";
    private static final String FILE_FIELD_MAPPING = "fieldMapping";
    private static final String MAX_RECORDS = "limit";
    private static final String CATEGORY_LIST = "categories";
    private static final String FTP_HOST = "ftpHost";
    private static final String FTP_PORT = "ftpPort";
    private static final String FTP_USER = "ftpUser";
    private static final String FTP_PASS = "ftpPass";
    private static final String FTP_DIR = "ftpDir";
    
    private String processName;
    
    private Logger log;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private ProcessUtilityService processUtilityService;
    
    public AffiliatePublisher(String processName){
        this.processName = processName;
        this.log = LoggerFactory.getLogger(processName+".logger");
    }
    
    private void updateLastRun(Date runTs){
        log.info("Updating Last Run Ts");
        processUtilityService.updateLastRunTs(processName, runTs);
    }
    
    public void publish(){

        Date runTs = Calendar.getInstance().getTime();
        log.info("START: Publisher Name: " + processName);
        
        // Get required categories
        List<Integer> subcategoryList = new ArrayList<Integer>();
        String categories = processUtilityService.getProcessProperty(processName, CATEGORY_LIST);
        if(categories == null || categories.isEmpty()) {
            log.info("No categories obtained. Will fetch for all");
        }else{
            String[] categoryArray = categories.split(",");
            for(String category: categoryArray){
                List<Integer> subcategories = categoryService.getSubCategoriesByCategoryId(Integer.parseInt(category));
                if(subcategories == null || subcategories.isEmpty()){
                    continue;
                }
                subcategoryList.addAll(subcategories);
            }
        }
        
        // Get limit value
        int limit = 0;
        try{
            String limitValue = processUtilityService.getProcessProperty(processName, MAX_RECORDS);
            if(limitValue == null){
                log.info("Limit not defined. Will write all the records fetched");
            }
            limit = Integer.parseInt(limitValue);
            
        }catch(NumberFormatException nfe){
            log.info("Cannot convert limit property value to int. Will write all the records fetched");
        }
        
        // Get data from Mongo
        List<ProductInfo> pogData = productService.getProductsBySubCategoryListSortByMonthlySales(subcategoryList, limit);
        
        if(pogData == null || pogData.isEmpty()){
            log.error("No data obtained from Mongo. Exiting");
            updateLastRun(runTs);
            return;
        }
        
        log.info("Number of records obtained from DB: " + pogData.size());
        
        // Create list of PogInfoSROs
        List<Object> pogDTOList = new ArrayList<Object>();
        
        for(ProductInfo productInfo: pogData){
            PublisherProductDTO pogDTO = new PublisherProductDTO();
            
            pogDTO.setPogId(productInfo.getPogId());
            pogDTO.setTitle(productInfo.getTitle());
            pogDTO.setUrl(productInfo.getUrl());
            pogDTO.setDescription(productInfo.getDescription());
            pogDTO.setImageLink(productInfo.getImageLink());
            pogDTO.setBrand(productInfo.getBrand());
            
            pogDTO.setSubCategoryID(productInfo.getSubCategoryId());
            
            // Get and set category names
            CategoryInfo categInfo = categoryService.getCategoryInfoBySubCategoryId(productInfo.getSubCategoryId());
            pogDTO.setParentCategoryID(categInfo.getParentCategoryId());
            pogDTO.setSubCategoryName(categInfo.getSubCategoryName());
            pogDTO.setParentCategoryName(categInfo.getParentCategoryName());
            
            pogDTO.setPrice(productInfo.getPrice());
            pogDTO.setAvailability(productInfo.getAvailability());
            if(productInfo.getMrp() != null && productInfo.getMrp()!=0){
                pogDTO.setMrp(productInfo.getMrp().toString());
                pogDTO.setDiscount(productInfo.getDiscountPercentage().toString());
            }else{
                pogDTO.setMrp("");
                pogDTO.setDiscount("");
            }
            
            // Add to list
            pogDTOList.add(pogDTO);
        }
        
        // Write data into file
        
        // Get file name
        String fileName = processUtilityService.getProcessProperty(processName, FILE_NAME);
        if(fileName == null || fileName.length() == 0){
            log.error("No file name defined. Exiting.");
            updateLastRun(runTs);
            return;
        }
        
        // Get file path
        String filePath = processUtilityService.getProcessProperty(processName, FILE_PATH);
        if(filePath == null || filePath.length() == 0){
            log.warn("File path not defined. Using current directory");
            filePath = "";
        }
        
        // Get file types
        String fileTypes = processUtilityService.getProcessProperty(processName, FILE_TYPE);
        if(fileTypes == null){
            log.error("File type not defined. Exiting");
            updateLastRun(runTs);
            return;
        }
        
        // Get field column mapping
        String fieldMap = processUtilityService.getProcessProperty(processName, FILE_FIELD_MAPPING);
        if(fieldMap == null || fieldMap.length() == 0){
            log.error("No field mapping available. Exiting");
            updateLastRun(runTs);
            return;
        }
        
        for(String type: fileTypes.split(",")){
            // Create file object
            File file = new File(filePath, fileName+"."+type);
            log.info("Creating " + type + " file");
            
            try{
                if(type.equalsIgnoreCase("csv")){
                    // Get separator
                    char separator = processUtilityService.getProcessProperty(processName, FILE_SEPARATOR).charAt(0);
                    PogCsvWriter csvWriter = new PogCsvWriter(file, separator, fieldMap);
                    csvWriter.writeToFile(pogDTOList);
                }else if(type.equalsIgnoreCase("xml")){
                    PogXmlWriter xmlWriter = new PogXmlWriter(file, fieldMap);
                    xmlWriter.writeToFile(pogDTOList);
                }else {
                    log.error("Undefined file type. Exiting");
                    updateLastRun(runTs);
                    return;
                }
                
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
                        processUtilityService.getProcessProperty(processName, FTP_HOST),
                        Integer.parseInt(processUtilityService.getProcessProperty(processName, FTP_PORT)),
                        processUtilityService.getProcessProperty(processName, FTP_USER), 
                        processUtilityService.getProcessProperty(processName, FTP_PASS), 
                        processUtilityService.getProcessProperty(processName, FTP_DIR)
                        );

            } catch (Exception ex) {
                log.error("Error while transferring the file");
                log.error(ExceptionUtils.getFullStackTrace(ex));
            }

            if(isTransferSuccessful){
                log.info("File successfully copied to FTP server");
            }else {
                log.error("Upload to FTP failed");
            }
        }
        // Update
        updateLastRun(runTs);
        log.info("END: Publisher run complete");
    }
}
