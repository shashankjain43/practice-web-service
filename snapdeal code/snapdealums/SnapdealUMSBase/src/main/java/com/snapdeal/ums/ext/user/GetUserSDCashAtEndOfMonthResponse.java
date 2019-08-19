
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class GetUserSDCashAtEndOfMonthResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6362352498582433989L;
	@Tag(5)
    private int getUserSDCashAtEndOfMonth;

    public GetUserSDCashAtEndOfMonthResponse() {
    }

    public GetUserSDCashAtEndOfMonthResponse(int getUserSDCashAtEndOfMonth) {
        super();
        this.getUserSDCashAtEndOfMonth = getUserSDCashAtEndOfMonth;
    }

    public int getGetUserSDCashAtEndOfMonth() {
        return getUserSDCashAtEndOfMonth;
    }

    public void setGetUserSDCashAtEndOfMonth(int getUserSDCashAtEndOfMonth) {
        this.getUserSDCashAtEndOfMonth = getUserSDCashAtEndOfMonth;
    }

}
