
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetEmailSubscribersIncrementalRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 4893033934000207107L;
    @Tag(3)
    private Integer cityId;
    @Tag(4)
    private Integer lastUpdated;
    @Tag(5)
    private int firstResult;
    @Tag(6)
    private int maxResults;

    public GetEmailSubscribersIncrementalRequest() {
    }
    

    public GetEmailSubscribersIncrementalRequest(Integer cityId, Integer lastUpdated, int firstResult, int maxResults) {
        super();
        this.cityId = cityId;
        this.lastUpdated = lastUpdated;
        this.firstResult = firstResult;
        this.maxResults = maxResults;
    }


    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
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
