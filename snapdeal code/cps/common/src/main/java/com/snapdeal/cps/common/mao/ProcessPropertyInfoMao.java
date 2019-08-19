package com.snapdeal.cps.common.mao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.snapdeal.cps.common.mongo.ProcessPropertyInfo;

/**
 * @author Sushant Taneja <sushant.taneja@snapdeal.com>
 *
 */

@Repository("processPropertyMao")
public class ProcessPropertyInfoMao {

    @Autowired
    private MongoTemplate mongoTemplate;
    
    public String getProcessPropertyValue(String processName, String propertyName){
        Query query = new Query(new Criteria("processName").is(processName));
        query.addCriteria(new Criteria("propertyName").is(propertyName));
        ProcessPropertyInfo propertyInfo = mongoTemplate.findOne(query, ProcessPropertyInfo.class);
        if(propertyInfo == null){
            return null;
        }else {
            return propertyInfo.getPropertyValue();
        }
    }
}
