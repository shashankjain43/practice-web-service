
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class GetUserSDCashUsedThisMonthResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6139548320691650451L;
	@Tag(5)
    private int getUserSDCashUsedThisMonth;

    public GetUserSDCashUsedThisMonthResponse() {
    }

    public GetUserSDCashUsedThisMonthResponse(int getUserSDCashUsedThisMonth) {
        super();
        this.getUserSDCashUsedThisMonth = getUserSDCashUsedThisMonth;
    }

    public int getGetUserSDCashUsedThisMonth() {
        return getUserSDCashUsedThisMonth;
    }

    public void setUserSDCashUsedThisMonth(int getUserSDCashUsedThisMonth) {
        this.getUserSDCashUsedThisMonth = getUserSDCashUsedThisMonth;
    }

}
