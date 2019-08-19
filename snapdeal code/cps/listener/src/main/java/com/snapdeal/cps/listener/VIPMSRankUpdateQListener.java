package com.snapdeal.cps.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.catalog.base.sro.POGDetailSRO;
import com.snapdeal.cps.common.dto.ProductDTO;
import com.snapdeal.ipms.base.queue.update.SUPCSellerUpdateEventSRO;
import com.snapdeal.ipms.base.queue.update.SUPCSellerUpdateEventsSRO;

@Component("VIPMSRankUpdateQListener")
public class VIPMSRankUpdateQListener implements MessageListener {

    private static final Logger log = LoggerFactory.getLogger("vipmsRankQListener.logger");

    @Autowired
    private ListenerWorker listenerWorker;
    
    /** Whenever there is some message in the queue, this function is called */
    @SuppressWarnings("unchecked")
    @Override
    public void onMessage(Message message) {
        
        int messageSize = 0;
        List<String> supcList = new ArrayList<String>();
        try {
            SUPCSellerUpdateEventsSRO supcSellerUpdateEventsSRO = (SUPCSellerUpdateEventsSRO) (((ObjectMessage) message).getObject());
            List<SUPCSellerUpdateEventSRO> supcSellerList= supcSellerUpdateEventsSRO.getSucpSellerUpdateEventList();
            if(supcSellerList == null || supcSellerList.isEmpty()){
                log.error("SUPC-Seller list is empty in received message, Exiting ");
                return;
            }
            messageSize = supcSellerList.size();
            log.info("Listened a message of size from the VIPMS Queue: " + messageSize);
            for(SUPCSellerUpdateEventSRO supcSellerUpdateEventSRO:supcSellerList){
                supcList.add(supcSellerUpdateEventSRO.getSupc());
            }

        } catch (JMSException e) {
            log.error("Exception while listening to the queue ");
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        
        
        int numberOfBatches = messageSize / 100;
        if(messageSize % 100 !=0 ){
            numberOfBatches++;
        }
        
        log.info("Number of batches: " + numberOfBatches);
        
        Map<Long, POGDetailSRO> productMap = new HashMap<Long, POGDetailSRO>();
        for(int i=0;i<numberOfBatches;i++){
            try{
                List<String> supcBatch = supcList.subList(i*100, Math.min((i+1)*100, messageSize));
                
                // Fetch details from Catalog
                log.info("Fetching Catalog details");
                List<POGDetailSRO> pogList = listenerWorker.getPOGDetailListBySupcList(supcBatch, log);
                if(pogList == null || pogList.isEmpty()){
                    continue;
                }
                
                // Add to product map
                for(POGDetailSRO pog : pogList){
                    // Check if pogId exists in productMap, if not then add else ignore
                    if(!productMap.containsKey(pog.getId())){
                        productMap.put(pog.getId(), pog);
                    }
                }
                
            }catch(Exception ex){
                log.error("Exception while processing the message");
                log.error(ExceptionUtils.getFullStackTrace(ex));
            }
        }
        try{
            if(productMap.isEmpty()){
                log.warn("No details obtained from catalog");
                return;
            }else{
                log.info("Catalog information fetched for " + productMap.size() + " POGs");
            }
            
            // Create product DTOs
            List<ProductDTO> products = listenerWorker.getProductListByPOGDetailList(productMap.values(), log);
            if(products == null || products.isEmpty()){
                log.warn("Product list obtained as null or empty");
                return;
            }
            
            // Add to database
            log.info("Saving a product list of " + products.size());
            listenerWorker.saveToMongo(products, log);
        }catch(Exception e){
            log.error("Unhandeled Exception");
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
            
    }
}
