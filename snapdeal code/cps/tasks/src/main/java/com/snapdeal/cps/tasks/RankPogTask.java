package com.snapdeal.cps.tasks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.cps.common.service.GoogleProductInfoService;
import com.snapdeal.cps.common.service.ProcessUtilityService;
import com.snapdeal.cps.common.service.ProductService;

/**
 * @author Sushant Taneja <sushant.taneja@snapdeal.com>
 *
 */

@Component("rankPogTask")
public class RankPogTask {

    private static final Logger log = LoggerFactory.getLogger("tasks.logger");
    
    private static final long ONE_DAY = 24 * 3600 * 1000;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private ProcessUtilityService processUtilityService;
    
    @Autowired
    private GoogleProductInfoService googleProductService;
    
    @Autowired
    private DataSource dataSource;
    
    private Connection dbConnection;
    
    public void rankMonthly(){
        
        String processName = "rankPogMonthly";
        
        // Get LastRunInfo for this process
        Date lastRunTs = processUtilityService.getLastRunInfoByProcessName(processName).getLastRunTs();
        log.info("Process last ran successfully on: " + lastRunTs);
        
        Date currentDate = Calendar.getInstance().getTime();
        
        long differenceInDays = currentDate.getTime() - lastRunTs.getTime();
        
        if(differenceInDays <= 2 * ONE_DAY){
            log.info("Time difference between runs is less than 2 days. Skipping this run");
            return;
        }
        
        // Set date formatter
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        
        // Define start and end dates
        Date startDate = new Date(currentDate.getTime() - 30 * ONE_DAY);
        
        String formattedStartDate = sdf.format(startDate) + "000000";
        String formattedEndDate = sdf.format(currentDate) + "000000";
        
        double start = Double.parseDouble(formattedStartDate);
        double end = Double.parseDouble(formattedEndDate);
        
        log.info("Start Date: " + start);
        log.info("End Date: " + end);
        
        // Fetch POGs from DWH
        List<Long> pogList = new ArrayList<Long>();
        Map<Integer,List<Long>> categPogMap = new HashMap<Integer, List<Long>>(); 
        
        // Connect to DB
        try{
            dbConnection = dataSource.getConnection();
        }catch(SQLException sqle){
            log.error("Unable to form the connection");
            log.error(ExceptionUtils.getFullStackTrace(sqle));
            return;
        }
        
        // Form SQL statement
        String query = "SELECT product_offer_group_id, subcategory_id, SUM(total_orders) as order_count FROM aggregated_sales_data WHERE order_date >= ? AND order_date < ? GROUP BY product_offer_group_id, subcategory_id ORDER BY order_count DESC";
        
       try{
           PreparedStatement ps = dbConnection.prepareStatement(query);
           ps.setDouble(1, start);
           ps.setDouble(2, end);
           
           ResultSet rs = ps.executeQuery();
           
           if(rs == null){
               log.error("Unable to get data from DB");
               return;
           }
        
           while(rs.next()){
               Long pogId = rs.getLong("product_offer_group_id");
               Integer subCategoryId = rs.getInt("subcategory_id");
               
               List<Long> topPogs = categPogMap.get(subCategoryId);
               if(topPogs == null){
                   topPogs = new ArrayList<Long>();
                   topPogs.add(pogId);
                   categPogMap.put(subCategoryId, topPogs);
               }else if(topPogs.size() < 50){
                   topPogs.add(pogId);
                   categPogMap.put(subCategoryId, topPogs);
               }
               pogList.add(pogId);
           }
        
           log.info("Total rows returned from DB: " + pogList.size());
           
           rs.close();
           ps.close();
           
           
       }catch(SQLException sqle){
           log.error("Error while executing the query");
           log.error(ExceptionUtils.getFullStackTrace(sqle));
           return;

       }finally{
           if(dbConnection != null){
               try {
                   dbConnection.close();
               } catch (SQLException e) {
                   log.error("Unable to close DB Connection");
                   log.error(ExceptionUtils.getFullStackTrace(e));
               }
           }
       }
       
       if(pogList == null || pogList.size() == 0){
           log.error("No data obtained from DB. Exiting");
           return;
       }
        
       // Reset POG rank from Mongo
       log.info("Resetting monthly ranks in Mongo");
       productService.resetMonthlyRankForAll();

       // Set monthly rank as per new data
       log.info("Setting the new monthly ranks");
       for(int i=0; i<pogList.size();i++){
           productService.updateMonthlyRank(pogList.get(i), i+1);
       }
       
       // Reset bestseller custom label for all
       log.info("Resetting bestseller custom label");
       googleProductService.resetBestSellingLabelForAll();
       
       log.info("Setting bestseller custom label");
       for(Entry<Integer, List<Long>> categPogList: categPogMap.entrySet()){
           for(Long pogId: categPogList.getValue()){
               googleProductService.setBestsellerLabel(pogId);
           }
       }

       // Update last run
       log.info("All data updated successfully. Updating the last run ts");
       processUtilityService.updateLastRunTs(processName, currentDate);

       log.info("Process completed successfully");
        
    }
    
}
