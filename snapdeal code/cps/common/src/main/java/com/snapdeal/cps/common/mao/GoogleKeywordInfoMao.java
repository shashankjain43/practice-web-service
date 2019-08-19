package com.snapdeal.cps.common.mao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.snapdeal.cps.common.mongo.GoogleKeywordInfo;

/**
 * @author Sushant Taneja <sushant.taneja@snapdeal.com>
 *
 */

@Repository("googleKeywordMao")
public class GoogleKeywordInfoMao {

    @Autowired
    private MongoTemplate mongoTemplate;
    
    public GoogleKeywordInfo findByBrandAndSubCategoryId(String brand, int subCategoryId){
        Query keywordQuery = new Query(new Criteria("brand").is(brand.toLowerCase()));
        keywordQuery.addCriteria(new Criteria("subCategoryId").is(subCategoryId));
        
        return mongoTemplate.findOne(keywordQuery, GoogleKeywordInfo.class);
    }
    
    public GoogleKeywordInfo findByNoBrandAndSubCategoryId(int subCategoryId){
        Query keywordQuery = new Query(new Criteria("subCategoryId").is(subCategoryId));
        keywordQuery.addCriteria(new Criteria("brand").exists(false));
        
        return mongoTemplate.findOne(keywordQuery, GoogleKeywordInfo.class);
    }
    
}
