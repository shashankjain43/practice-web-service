
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetMobileSubscribersAndDNDStatusRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -2192815147747608313L;
    @Tag(3)
    private int firstResult;
    @Tag(4)
    private int maxResults;

    public GetMobileSubscribersAndDNDStatusRequest() {
    }
    
    public GetMobileSubscribersAndDNDStatusRequest(int firstResult, int maxResults) {
        super();
        this.firstResult = firstResult;
        this.maxResults = maxResults;
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
