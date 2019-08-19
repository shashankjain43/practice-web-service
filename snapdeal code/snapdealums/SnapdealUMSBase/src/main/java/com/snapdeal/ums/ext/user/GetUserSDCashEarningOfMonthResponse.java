
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class GetUserSDCashEarningOfMonthResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7974710658082408702L;
	@Tag(5)
    private int getUserSDCashEarningOfMonth;

    public GetUserSDCashEarningOfMonthResponse() {
    }

    public GetUserSDCashEarningOfMonthResponse(int getUserSDCashEarningOfMonth) {
        super();
        this.getUserSDCashEarningOfMonth = getUserSDCashEarningOfMonth;
    }

    public int getGetUserSDCashEarningOfMonth() {
        return getUserSDCashEarningOfMonth;
    }

    public void setGetUserSDCashEarningOfMonth(int getUserSDCashEarningOfMonth) {
        this.getUserSDCashEarningOfMonth = getUserSDCashEarningOfMonth;
    }

}
