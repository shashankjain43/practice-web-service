package com.snapdeal.cps.common.mongo;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Sushant Taneja <sushant.taneja@snapdeal.com>
 *
 */

@Document(collection = "processPropertyInfo")
public class ProcessPropertyInfo {

    private String processName;
    private String propertyName;
    private String propertyValue;
    
    public String getProcessName() {
        return processName;
    }
    
    public void setProcessName(String publisherName) {
        this.processName = publisherName;
    }
    
    public String getPropertyName() {
        return propertyName;
    }
    
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
    
    public String getPropertyValue() {
        return propertyValue;
    }
    
    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }
    
    
    
}
