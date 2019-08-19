package com.snapdeal.cps.common.mao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.snapdeal.cps.common.mongo.LastRunInfo;

/**
 * @author Sushant Taneja <sushant.taneja@snapdeal.com>
 *
 */

@Repository("lastRunInfoMao")
public class LastRunInfoMao {

    @Autowired
    private MongoTemplate mongoTemplate;
    
    public LastRunInfo findByProcessName(String processName){
        return mongoTemplate.findOne(new Query(new Criteria("processName").is(processName)), LastRunInfo.class);
    }
    
    public void updateLastRunByProcessName(String processName, Date timestamp){
        Query updateQuery = new Query(new Criteria("processName").is(processName));
        Update update = new Update();
        update.set("lastRunTs", timestamp);
        
        mongoTemplate.updateFirst(updateQuery, update, LastRunInfo.class);
    }
    
}
