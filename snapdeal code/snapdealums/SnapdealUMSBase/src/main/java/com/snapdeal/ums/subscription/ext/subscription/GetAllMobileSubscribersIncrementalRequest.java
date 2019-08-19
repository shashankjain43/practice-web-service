
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetAllMobileSubscribersIncrementalRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -3782314581023012801L;
    @Tag(3)
    private Integer lastUpdated;
    @Tag(4)
    private int firstResult;
    @Tag(5)
    private int maxResults;

    public GetAllMobileSubscribersIncrementalRequest() {
    }
    
    public GetAllMobileSubscribersIncrementalRequest(Integer lastUpdated, int firstResult, int maxResults) {
        super();
        this.lastUpdated = lastUpdated;
        this.firstResult = firstResult;
        this.maxResults = maxResults;
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
