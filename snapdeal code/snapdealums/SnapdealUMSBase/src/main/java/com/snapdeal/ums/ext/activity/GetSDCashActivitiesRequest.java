
package com.snapdeal.ums.ext.activity;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.base.utils.DateUtils.DateRange;

public class GetSDCashActivitiesRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7009967970348488218L;
	@Tag(3)
    private com.snapdeal.base.utils.DateUtils.DateRange range;
    @Tag(4)
    private int firstResult;
    @Tag(5)
    private int maxResults;

    public GetSDCashActivitiesRequest() {
    }

    public com.snapdeal.base.utils.DateUtils.DateRange getRange() {
        return range;
    }

    public void setRange(com.snapdeal.base.utils.DateUtils.DateRange range) {
        this.range = range;
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

    public GetSDCashActivitiesRequest(DateRange range, int firstResult, int maxResults) {
        this.range = range;
        this.firstResult = firstResult;
        this.maxResults = maxResults;
    }

}
