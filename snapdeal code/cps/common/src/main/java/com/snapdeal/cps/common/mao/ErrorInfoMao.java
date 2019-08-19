package com.snapdeal.cps.common.mao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.snapdeal.cps.common.mongo.ErrorInfo;

/**
 * @author Sushant Taneja <sushant.taneja@snapdeal.com>
 *
 */

@Repository("errorInfoMao")
public class ErrorInfoMao {

    @Autowired
    private MongoTemplate mongoTemplate;
    
    public void saveError(String elementType, String elementId, String description){
        ErrorInfo error = new ErrorInfo(elementType, elementId);
        error.setDescription(description);
        mongoTemplate.save(error);
    }
    
}
