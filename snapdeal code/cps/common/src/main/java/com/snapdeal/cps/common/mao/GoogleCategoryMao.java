/**
 *  Copyright 2014 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
  
package com.snapdeal.cps.common.mao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.snapdeal.cps.common.mongo.GoogleCategoryInfo;

/**
 *  @version   1.0, Jul 3, 2014
 *  @author 	Sushant Taneja <sushant.taneja@snapdeal.com>
 */

@Repository("googleCategoryMao")
public class GoogleCategoryMao {

    @Autowired
    private MongoTemplate mongoTemplate;
    
    public GoogleCategoryInfo findByCategoryId(int categoryId){
        return mongoTemplate.findOne(new Query(new Criteria("categoryId").is(categoryId)), GoogleCategoryInfo.class);
    }
    
    public List<GoogleCategoryInfo> findByBannedStatus(Boolean banned){
        return mongoTemplate.find(new Query(new Criteria("banned").is(banned.toString())), GoogleCategoryInfo.class);
    }
    
}
