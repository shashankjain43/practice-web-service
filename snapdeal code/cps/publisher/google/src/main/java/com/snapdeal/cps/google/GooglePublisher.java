package com.snapdeal.cps.google;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.snapdeal.cps.common.mongo.LastRunInfo;
import com.snapdeal.cps.common.mongo.ProductInfo;
import com.snapdeal.cps.common.service.CategoryService;
import com.snapdeal.cps.common.service.ProcessUtilityService;
import com.snapdeal.cps.common.service.ProductService;

/**
 * @author Shashank Jain<jain.shashank@snapdeal.com>
 *
 */
public class GooglePublisher {
    
    // Define process properties
    private static final String RECORD_LIMIT = "updatesLimit";
    private static final String TEST_SELLER_CODES = "testSellers";
    private static final String EXCLUDED_CATEGORIES = "excludeSubCategories";
    private static final String CATEGORY_LIST = "categories";
    
    private String processName;
    private boolean isProdEnv;
    private Logger log;
    private Date lastRunTs;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private ProcessUtilityService processUtilityService;
    
    @Autowired
    private GooglePublisherService googlePublisherService;  
    
    public GooglePublisher(String processName){
        this.processName = processName;
        this.log = LoggerFactory.getLogger(processName+".logger");
    }
    
    @PostConstruct
    public void init(){
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
        this.lastRunTs = runTs;
        processUtilityService.updateLastRunTs(processName, runTs);
    }
    
    private void passTestSellerProducts(List<String> testSellers, List<ProductInfo> products){
        Iterator<ProductInfo> it = products.iterator();
        while(it.hasNext()){
            ProductInfo productDTO = it.next();
            if(!testSellers.contains(productDTO.getSellerCode())){
                it.remove();
            }
        }
    }
    
    public void publish(){
        // List of POG Ids for which request has failed
        List<Long> failedPogList;
        Date maxTs = null;
        try{
            Date runTs = Calendar.getInstance().getTime();
            log.info("START: Publisher Name: " + processName);

            // Get process last run info
            if(lastRunTs == null){
                LastRunInfo processRunInfo = processUtilityService.getLastRunInfoByProcessName(processName);
                if(processRunInfo == null){
                    log.error("Unable to fetch last run info from DB. Exiting with error");
                    return;
                }
                this.lastRunTs = processRunInfo.getLastRunTs();
            }
            

            log.info("Last Run Ts: " + this.lastRunTs);

            // Get limit value
            int limit = 0;
            try{
                String limitValue = processUtilityService.getProcessProperty(processName, RECORD_LIMIT);
                if(limitValue == null){
                    log.info("Limit not defined. Will write all the records fetched");
                }
                limit = Integer.parseInt(limitValue);
                
            }catch(NumberFormatException nfe){
                log.info("Cannot convert limit property value to int. Will write all the records fetched");
            }
            
            // Get required categories
            List<Integer> allSubcategoryList = new ArrayList<Integer>();
            
            String categories = processUtilityService.getProcessProperty(processName, CATEGORY_LIST);
            if(categories == null || categories.isEmpty()) {
                log.info("No categories obtained, returning");
                return;
            }    
                
            String[] categoryArray = categories.split(",");
            for(String category: categoryArray){
                List<Integer> subcategories = categoryService.getSubCategoriesByCategoryId(Integer.parseInt(category));
                if(subcategories == null || subcategories.isEmpty()){
                    continue;
                }
                allSubcategoryList.addAll(subcategories);
            }
            
            // Get excluded subCategories from db
            List<Integer> excludedSubCategories = new ArrayList<Integer>();
            
            String excludedCategories = processUtilityService.getProcessProperty(processName, EXCLUDED_CATEGORIES);
            if(excludedCategories != null && !excludedCategories.isEmpty()){
                List<String> excludedCategoryList = Arrays.asList(excludedCategories.split(","));
                for(String exCategory:excludedCategoryList){
                    excludedSubCategories.add(Integer.parseInt(exCategory));
                }
            }
            
            //remove excluded subcategories
            Iterator<Integer> iter = allSubcategoryList.iterator();
            while (iter.hasNext()) {
                if (excludedSubCategories.contains(iter.next())) {
                    iter.remove();
                }
            }
            
            //get data updated since last run, from db
            List<ProductInfo> updatedProducts;
            updatedProducts = productService.getProductsUpdatedFromTsFilterBySubCategoryList(lastRunTs, allSubcategoryList, limit);
            
            if(updatedProducts == null || updatedProducts.isEmpty()){
                log.info("No products could be obtained from DB.");
                updateLastRun(runTs);
                return;
            }
            log.info("Number of products obtained from DB: " + updatedProducts.size());
            
            // Calculate timestamp for next run
            if(updatedProducts.size() > 0){
                maxTs = updatedProducts.get(updatedProducts.size()-1).getUpdateTs();
                log.info("Max Timestamp: " + maxTs);
            }
            
            // If running in non production environment, only pass test seller codes
            if(!isProdEnv){
                String[] testSellerArray = processUtilityService.getProcessProperty(processName, TEST_SELLER_CODES).split(",");
                List<String> testSellerCodes = new ArrayList<String>(Arrays.asList(testSellerArray));
                passTestSellerProducts(testSellerCodes, updatedProducts);
                if(updatedProducts == null || updatedProducts.isEmpty()){
                    log.info("No products could be obtained after test filtering");
                    updateLastRun(maxTs);
                    return;
                }
                log.info("Test products obtained from DB: " + updatedProducts.size());
                for(ProductInfo pi: updatedProducts){
                    log.info(""+pi.getPogId());
                }
            }
            
            //use publisher service to publish updated data on GMC
            failedPogList = googlePublisherService.publishOnGMC(updatedProducts,log);
            if(failedPogList != null && !failedPogList.isEmpty()){
                log.info("Found " + failedPogList.size() +" failed Pogs during publishing, hence updating timestamp for them");
                // Update timestamp for all failed Pogs, so that they can be picked up later
                Calendar cal = Calendar.getInstance();
                for(long pog: failedPogList){
                    productService.updateProductTs(pog, cal.getTime());
                }
            }
            
        }catch(Exception ex){
            log.error("Unhandled Exception");
            log.error(ExceptionUtils.getFullStackTrace(ex));
        }
        
        //Update
        if(maxTs!=null){
            updateLastRun(maxTs);
        }else{
            log.info("Not updating timestamp");
        }
        
        log.info("END: " + processName + " Publisher run complete");
    }
    
}
