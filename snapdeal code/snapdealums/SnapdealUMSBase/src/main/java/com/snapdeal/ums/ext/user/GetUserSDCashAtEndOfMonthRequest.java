
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.base.utils.DateUtils.DateRange;

public class GetUserSDCashAtEndOfMonthRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2188907761124048223L;
	@Tag(3)
    private com.snapdeal.base.utils.DateUtils.DateRange lastMonthRange;
    @Tag(4)
    private com.snapdeal.base.utils.DateUtils.DateRange currentMonthRange;
    @Tag(5)
    private Integer userId;

    public GetUserSDCashAtEndOfMonthRequest() {
    }

    public com.snapdeal.base.utils.DateUtils.DateRange getLastMonthRange() {
        return lastMonthRange;
    }

    public void setLastMonthRange(com.snapdeal.base.utils.DateUtils.DateRange lastMonthRange) {
        this.lastMonthRange = lastMonthRange;
    }

    public com.snapdeal.base.utils.DateUtils.DateRange getCurrentMonthRange() {
        return currentMonthRange;
    }

    public void setCurrentMonthRange(com.snapdeal.base.utils.DateUtils.DateRange currentMonthRange) {
        this.currentMonthRange = currentMonthRange;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public GetUserSDCashAtEndOfMonthRequest(DateRange lastMonthRange, DateRange currentMonthRange, Integer userId) {
        this.lastMonthRange = lastMonthRange;
        this.currentMonthRange = currentMonthRange;
        this.userId = userId;
    }

}
