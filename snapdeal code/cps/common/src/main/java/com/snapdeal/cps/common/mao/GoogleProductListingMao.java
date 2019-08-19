/**
 *  Copyright 2014 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
  
package com.snapdeal.cps.common.mao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.snapdeal.cps.common.mongo.GoogleProductListing;

/**
 *  @version   1.0, Jul 3, 2014
 *  @author 	Sushant Taneja <sushant.taneja@snapdeal.com>
 */

@Repository("googlePLMao")
public class GoogleProductListingMao {

    @Autowired
    private MongoTemplate mongoTemplate;
    
    public GoogleProductListing findByPogId(long pogId){
        return mongoTemplate.findOne(new Query(new Criteria("pogId").is(pogId)), GoogleProductListing.class);
    }
    
    public void saveProductListing(GoogleProductListing googleListing){
        mongoTemplate.save(googleListing);
    }
    
    public void removeSubaccountById(String gsId){
        Query query = new Query(new Criteria("gsIds").is(gsId));
        Update update = new Update();
        update.pull("gsIds", gsId);
        
        mongoTemplate.updateMulti(query, update, GoogleProductListing.class);
    }
    
    public void resetBestSellingLabelForAll() {
        Query query = new Query(new Criteria("labels").is("bestseller"));
        Update update = new Update();
        update.set("labels.$","");
        
        mongoTemplate.updateMulti(query, update, GoogleProductListing.class);
        
    }
}
