
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class GetUserSDCashExpiredThisMonthResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 7974710658082408702L;
    @Tag(5)
    private int getUserSDCashExpiryOfMonth;

    public GetUserSDCashExpiredThisMonthResponse() {
    }

    public GetUserSDCashExpiredThisMonthResponse(int getUserSDCashExpiryOfMonth) {
        super();
        this.getUserSDCashExpiryOfMonth = getUserSDCashExpiryOfMonth;
    }

    public int getGetUserSDCashExpiryOfMonth() {
        return getUserSDCashExpiryOfMonth;
    }

    public void setGetUserSDCashExpiryOfMonth(int getUserSDCashExpiryOfMonth) {
        this.getUserSDCashExpiryOfMonth = getUserSDCashExpiryOfMonth;
    }

}
