package com.snapdeal.cps.common.mao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.snapdeal.cps.common.mongo.SellerInfo;

/**
 * @author Sushant Taneja <sushant.taneja@snapdeal.com>
 *
 */

@Repository("sellerInfoMao")
public class SellerInfoMao {

    @Autowired
    private MongoTemplate mongoTemplate;
    
    public SellerInfo findBySellerCode(String sellerCode){
        return mongoTemplate.findOne(new Query(new Criteria("internalCode").is(sellerCode)), SellerInfo.class);
    }
    
    public void saveSellerInfo(SellerInfo sellerInfo){
        mongoTemplate.save(sellerInfo);
    }
    
    public void insertSeller(SellerInfo sellerInfo){
        mongoTemplate.insert(sellerInfo);
    }
    
    public void updateSellerDisplayName(String sellerCode, String displayName){
        Query query = new Query(new Criteria("internalCode").is(sellerCode));
        Update update = new Update();
        update.set("displayName", displayName);
        
        mongoTemplate.updateFirst(query, update, SellerInfo.class);
    }
}
