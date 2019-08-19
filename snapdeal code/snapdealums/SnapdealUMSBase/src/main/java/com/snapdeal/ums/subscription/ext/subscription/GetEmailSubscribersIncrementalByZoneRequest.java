/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Apr 22, 2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetEmailSubscribersIncrementalByZoneRequest extends ServiceRequest{

    /**
     * 
     */
    private static final long serialVersionUID = -5497354038055008053L;
    @Tag(3)
    private Integer zoneId;
    @Tag(4)
    private Integer lastUpdated;
    @Tag(5)
    private int firstResult;
    @Tag(6)
    private int maxResults;

    public GetEmailSubscribersIncrementalByZoneRequest() {
    }
    

    public GetEmailSubscribersIncrementalByZoneRequest(Integer zoneId, Integer lastUpdated, int firstResult, int maxResults) {
        super();
        this.zoneId = zoneId;
        this.lastUpdated = lastUpdated;
        this.firstResult = firstResult;
        this.maxResults = maxResults;
    }


    public Integer getZoneId() {
        return zoneId;
    }


    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }


    public Integer getLastUpdated() {
        return lastUpdated;
    }


    public void setLastUpdated(Integer lastUpdated) {
        this.lastUpdated = lastUpdated;
    }


    public int getFirstResult() {
        return firstResult;
    }


    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }


    public int getMaxResults() {
        return maxResults;
    }


    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }
    
}
