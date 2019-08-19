package com.snapdeal.cps.publisher.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.bytecode.opencsv.CSVWriter;

import com.snapdeal.cps.common.SellerAvailability;
import com.snapdeal.cps.common.mongo.CategoryInfo;
import com.snapdeal.cps.common.mongo.ProductInfo;
import com.snapdeal.cps.common.service.CategoryService;
import com.snapdeal.cps.common.service.GoogleProductInfoService;
import com.snapdeal.cps.common.service.ProcessUtilityService;
import com.snapdeal.cps.common.service.ProductService;
import com.snapdeal.cps.common.utils.FTPUtils;

/**
 * @author Sushant Taneja <sushant.taneja@snapdeal.com>
 *
 */

@Component("googleDataFeedPublisher")
public class GoogleDataFeedPublisher {
    
    private static final Logger log = LoggerFactory.getLogger("googleDataFeed.logger");

    // Define process name
    private static final String PROCESS_NAME = "GDFCsv";

    // Define property constants
    private static final String FILE_NAME = "fileName";
    private static final String FILE_PATH = "filePath";
    private static final String FILE_SEPARATOR = "seperator";
    private static final String FILE_FIELD_MAPPING = "fieldMapping";
    private static final String FTP_HOST = "ftpHost";
    private static final String FTP_PORT = "ftpPort";
    private static final String FTP_USER = "ftpUser";
    private static final String FTP_PASS = "ftpPass";
    private static final String FTP_DIR = "ftpDir";
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private ProcessUtilityService processUtilityService;
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private GoogleProductInfoService googleProductService;
    
    private void updateLastRun(Date runTs){
        log.info("Updating Last Run Ts");
        processUtilityService.updateLastRunTs(PROCESS_NAME, runTs);
    }
    
    private String[] convertToStringArray(ProductInfo productInfo){
        String[] pogDetails = new String[15];
        
        pogDetails[0] = Long.toString(productInfo.getPogId());
        pogDetails[1] = productInfo.getTitle();
        pogDetails[2] = productInfo.getDescription();
        
        // Get and set google product category
        CategoryInfo categInfo = categoryService.getCategoryInfoBySubCategoryId(productInfo.getSubCategoryId());
        if(categInfo == null){
            return null;
        }
        
        pogDetails[3] = googleProductService.getGoogleCategory(productInfo.getSubCategoryId());
        
        // Breadcrumbs
        pogDetails[4] = categInfo.getParentCategoryName() + " > " + categInfo.getSubCategoryName();

        pogDetails[5] = productInfo.getUrl()+"?utm_source=earth_feed&utm_campaign="+categInfo.getParentCategoryId()+"_"+categInfo.getSubCategoryId();
        
        pogDetails[6] = productInfo.getImageLink();
        
        // Condition is always new
        pogDetails[7] = "new";
        
        pogDetails[8] = productInfo.getAvailability();
        pogDetails[9] = ""+productInfo.getPrice();
        pogDetails[10] = productInfo.getBrand();
        
        // Adwords Label and Adwords Grouping
        pogDetails[11] = ""+categInfo.getSubCategoryId();
        pogDetails[12] = ""+categInfo.getParentCategoryId();
        
        // Adult content always no
        pogDetails[13] = "no";
        
        // Set bestseller custom label
        pogDetails[14] = "bestseller";
        
        return pogDetails;
    }
    
    
    public void publish(){
        
        Date runTs = Calendar.getInstance().getTime();
        log.info("START: Process for generating GoogleDataFeed. RunTs: " + runTs);
        
        // Get Categories
        List<Integer> categoryList = googleProductService.getBannedCategoryIds();
        
        log.info("Fetch data from mongo according to monthly rank");
        
        // Get information of all the monthly selling POGs
        List<ProductInfo> pogList = productService.getProductsByMonthlyRankGreaterThanEqualTo(1);
        
        if(pogList == null || pogList.size() == 0){
            log.warn("No POG returned from Mongo. Exiting.");
            updateLastRun(runTs);
            return;
        }
        
        log.info("Number of POGs obtained: " + pogList.size());
        
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
        
        // Create file object
        File file = new File(filePath, fileName);
        
        // Get seperator
        char separator = processUtilityService.getProcessProperty(PROCESS_NAME, FILE_SEPARATOR).charAt(0);
        
        // Get headers
        String headers = processUtilityService.getProcessProperty(PROCESS_NAME, FILE_FIELD_MAPPING);
        
        if(headers == null || headers.length() == 0){
            log.error("File headers not available. Exiting");
            updateLastRun(runTs);
            return;
        }
        
        // Write the Pog info to a file
        CSVWriter csvWriter = null;
        int numberOfRows = 0;
        
        try {
            csvWriter = new CSVWriter(new FileWriter(file), separator, CSVWriter.NO_QUOTE_CHARACTER);
            
            // Write Header
            csvWriter.writeNext(headers.split(","));
            
            // Write Data
            for(ProductInfo productInfo: pogList){
                
                // Check is the pog belongs to the defined publisher categories
                if(categoryList.contains(productInfo.getSubCategoryId())){
                    // Skip 
                    continue;
                }
                
                // Check if Pog is in stock
                if(!productInfo.getAvailability().equals(SellerAvailability.IN_STOCK.getStatus())){
                    continue;
                }
                
                String[] pogDetails = convertToStringArray(productInfo);
                if(pogDetails == null){
                    log.error("Null array obtained for POG: " + productInfo.getPogId());
                    continue;
                }
                csvWriter.writeNext(pogDetails);
                numberOfRows = numberOfRows + 1;
            }
            
            csvWriter.flush();
            
        } catch (IOException ioe) {
            log.error("Unable to write in the file");
            log.error(ExceptionUtils.getFullStackTrace(ioe));
        }catch(Exception ex){
            log.error(ExceptionUtils.getFullStackTrace(ex));
        }finally{
            if(csvWriter != null){
                try {
                    csvWriter.close();
                } catch (IOException e) {
                    log.error("Unable to close file ! ");
                    log.error(ExceptionUtils.getFullStackTrace(e));
                }
            }
        }
        
        log.info("Rows written to file: " + numberOfRows);
        
        // Publish the file
        
        // Check the environment
        String environment = System.getProperty("env");
        if(environment != null && environment.equals("prod")){
            // Upload file to SFTP
            log.info("Running in production mode");
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
            }else {
                log.error("Upload to FTP failed");
            }

        }
        
        // Update
        updateLastRun(runTs);
        log.info("END: Publisher run complete");
    }
    
}
