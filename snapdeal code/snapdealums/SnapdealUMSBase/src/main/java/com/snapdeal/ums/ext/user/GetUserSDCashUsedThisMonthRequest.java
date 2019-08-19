
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.base.utils.DateUtils.DateRange;

public class GetUserSDCashUsedThisMonthRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2687832669162420094L;
	@Tag(3)
    private com.snapdeal.base.utils.DateUtils.DateRange range;
    @Tag(4)
    private Integer userId;

    public GetUserSDCashUsedThisMonthRequest() {
    }

    public com.snapdeal.base.utils.DateUtils.DateRange getRange() {
        return range;
    }

    public void setRange(com.snapdeal.base.utils.DateUtils.DateRange range) {
        this.range = range;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public GetUserSDCashUsedThisMonthRequest(DateRange range, Integer userId) {
        this.range = range;
        this.userId = userId;
    }

}
