/**
 *  Copyright 2014 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
  
package com.snapdeal.cps.common.mongo;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *  @version   1.0, Jul 2, 2014
 *  @author 	Sushant Taneja <sushant.taneja@snapdeal.com>
 */

@Document(collection = "googlePL")
public class GoogleProductListing {

    @Id
    private long pogId;
    private List<String> gsIds;
    private List<String> labels;
    private String keywords;
    private String adGroup;
    
    public GoogleProductListing(long pogId, List<String> gsIds) {
        this.pogId = pogId;
        this.gsIds = gsIds;
    }

    public long getPogId() {
        return pogId;
    }
    
    public void setPogId(long pogId) {
        this.pogId = pogId;
    }
    
    public List<String> getGsIds() {
        return gsIds;
    }
    
    public void setGsIds(List<String> gsIds) {
        this.gsIds = gsIds;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getAdGroup() {
        return adGroup;
    }

    public void setAdGroup(String adGroup) {
        this.adGroup = adGroup;
    }
    
    public String getListedPrimarySubaccountId(){
        if(gsIds == null || gsIds.isEmpty()){
            return null;
        }else{
            return gsIds.get(0);
        }
    }
    
    @Override
    public String toString() {
        return "GoogleProductListing [pogId=" + pogId + ", gsIds=" + gsIds + ", labels=" + labels + ", keywords=" + keywords + ", adGroup=" + adGroup + "]";
    }

}
