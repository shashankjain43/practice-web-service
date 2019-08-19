/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 02-Jul-2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.subscription.ext.subscription;

import java.util.Date;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetEmailAllSubscribersIncrementalByDateAndIdRequest extends ServiceRequest {
    
    /**
     * 
     */
    private static final long serialVersionUID = 4208885963174792277L;
    
    @Tag(3)
    private long id;
    @Tag(4)
    private Date startDate;
    @Tag(5)
    private int  maxResults;

    public GetEmailAllSubscribersIncrementalByDateAndIdRequest() {
    }

    public GetEmailAllSubscribersIncrementalByDateAndIdRequest(long id, Date startDate, int maxResults) {
        super();
        this.id=id;
        this.startDate = startDate;
        this.maxResults = maxResults;
    }
    

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the maxResults
     */
    public int getMaxResults() {
        return maxResults;
    }

    /**
     * @param maxResults the maxResults to set
     */
    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    /**
     * @return toString
     */
    @Override
    public String toString() {
        return "GetEmailAllSubscribersIncrementalInDateRangeRequest [id=" + id + ", startDate=" + startDate + ", maxResults=" + maxResults + "]";
    }

}
