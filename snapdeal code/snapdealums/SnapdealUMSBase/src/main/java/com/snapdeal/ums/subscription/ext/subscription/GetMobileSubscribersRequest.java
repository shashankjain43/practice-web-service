
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetMobileSubscribersRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 4763091305448904350L;
    @Tag(3)
    private int cityIds;
    @Tag(4)
    private String filterType;
    @Tag(5)
    private boolean subscribed;
    @Tag(6)
    private boolean dnd;
    @Tag(7)
    private int firstResult;
    @Tag(8)
    private int maxResults;

    public GetMobileSubscribersRequest() {
    }
    
    public GetMobileSubscribersRequest(int cityIds, String filterType, boolean subscribed, boolean dnd, int firstResult, int maxResults) {
        super();
        this.cityIds = cityIds;
        this.filterType = filterType;
        this.subscribed = subscribed;
        this.dnd = dnd;
        this.firstResult = firstResult;
        this.maxResults = maxResults;
    }

    public int getCityIds() {
        return cityIds;
    }

    public void setCityIds(int cityIds) {
        this.cityIds = cityIds;
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public boolean getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }

    public boolean getDnd() {
        return dnd;
    }

    public void setDnd(boolean dnd) {
        this.dnd = dnd;
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
