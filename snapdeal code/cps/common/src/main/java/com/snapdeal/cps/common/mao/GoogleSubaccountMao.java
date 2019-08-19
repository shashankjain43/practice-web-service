/**
 *  Copyright 2014 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
  
package com.snapdeal.cps.common.mao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.snapdeal.cps.common.mongo.GoogleSubaccountInfo;

/**
 *  @version   1.0, Jul 3, 2014
 *  @author 	Sushant Taneja <sushant.taneja@snapdeal.com>
 */

@Repository("GoogleSubaccountMao")
public class GoogleSubaccountMao {

    @Autowired
    private MongoTemplate mongoTemplate;
    
    public void insertGoogleSubaccountInfo(String sellerCode, String gsId){
        
        GoogleSubaccountInfo gsInfo = new GoogleSubaccountInfo(gsId, sellerCode);
        mongoTemplate.insert(gsInfo);
    }
    
    public GoogleSubaccountInfo findById(String subaccountId){
        return mongoTemplate.findOne(new Query(new Criteria("id").is(subaccountId)), GoogleSubaccountInfo.class);
    }
    
    public GoogleSubaccountInfo findBySellerCode(String sellerCode){
        return mongoTemplate.findOne(new Query(new Criteria("sellerCode").is(sellerCode)), GoogleSubaccountInfo.class);
    }
    
    public void removeSubaccountById(String subaccountId){
        Query query = new Query(new Criteria("id").is(subaccountId));
        mongoTemplate.remove(query, GoogleSubaccountInfo.class);
    }
}
