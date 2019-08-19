/**
 *  Copyright 2014 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
  
package com.snapdeal.cps.common.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.cps.common.mao.ErrorInfoMao;
import com.snapdeal.cps.common.mao.LastRunInfoMao;
import com.snapdeal.cps.common.mao.ProcessPropertyInfoMao;
import com.snapdeal.cps.common.mongo.LastRunInfo;

/**
 *  @version   1.0, Jul 13, 2014
 *  @author 	Sushant Taneja <sushant.taneja@snapdeal.com>
 */

@Service("processUtilityService")
public class ProcessUtilityService {

    @Autowired
    private LastRunInfoMao lastRunInfoMao;
    
    @Autowired
    private ErrorInfoMao errorInfoMao;

    @Autowired
    private ProcessPropertyInfoMao processPropertyMao;

    public LastRunInfo getLastRunInfoByProcessName(String processName){
        return lastRunInfoMao.findByProcessName(processName);
    }
    
    public void logError(String elementType, String elementId, String description){
        errorInfoMao.saveError(elementType, elementId, description);
    }
    
    public void updateLastRunTs(String processName, Date timestamp){
        lastRunInfoMao.updateLastRunByProcessName(processName, timestamp);
    }
    
    public String getProcessProperty(String processName, String propertyName){
        return processPropertyMao.getProcessPropertyValue(processName, propertyName);
    }
}