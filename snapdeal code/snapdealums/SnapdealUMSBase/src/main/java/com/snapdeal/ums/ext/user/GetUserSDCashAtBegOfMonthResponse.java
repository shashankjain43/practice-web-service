
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class GetUserSDCashAtBegOfMonthResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6962791700739389950L;
	@Tag(5)
    private int userSDCashAtBegOfMonth;

    public GetUserSDCashAtBegOfMonthResponse() {
    }

    public GetUserSDCashAtBegOfMonthResponse(int getUserSDCashAtBegOfMonth) {
        super();
        this.userSDCashAtBegOfMonth = getUserSDCashAtBegOfMonth;
    }

    public int getUserSDCashAtBegOfMonth() {
        return userSDCashAtBegOfMonth;
    }

    public void setUserSDCashAtBegOfMonth(int getUserSDCashAtBegOfMonth) {
        this.userSDCashAtBegOfMonth = getUserSDCashAtBegOfMonth;
    }

}
