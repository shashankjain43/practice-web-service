package com.snapdeal.ums.subscription.ext.subscription;

import java.util.Date;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetEmailSubscribersIncrementalByZoneDateAndIdRequest extends ServiceRequest {

    /**
     * 
     */
    private static final long serialVersionUID = 4893033934000207107L;

    @Tag(3)
    private long              id;
    @Tag(4)
    private Integer           zoneId;
    @Tag(5)
    private Date              startDate;
    @Tag(6)
    private int               maxResults;

    public GetEmailSubscribersIncrementalByZoneDateAndIdRequest() {
    }

    public GetEmailSubscribersIncrementalByZoneDateAndIdRequest(long id, Integer zoneId, Date start, int maxResults) {
        super();
        this.zoneId = zoneId;
        this.startDate = start;
        this.maxResults = maxResults;
        this.id = id;
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
     * @return the zoneId
     */
    public Integer getZoneId() {
        return zoneId;
    }

    /**
     * @param zoneId the zoneId to set
     */
    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
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
        return "GetEmailSubscribersIncrementalByZoneInDateRangeRequest [id=" + id + ", zoneId=" + zoneId + ", startDate=" + startDate + ", maxResults="
                + maxResults + "]";
    }

}
