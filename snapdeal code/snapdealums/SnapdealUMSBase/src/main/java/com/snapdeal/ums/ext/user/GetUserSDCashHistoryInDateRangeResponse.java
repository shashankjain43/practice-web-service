
package com.snapdeal.ums.ext.user;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.user.UserSDCashHistorySRO;

public class GetUserSDCashHistoryInDateRangeResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -884333342740644622L;
	@Tag(5)
    private List<UserSDCashHistorySRO> getUserSDCashHistoryInDateRange = new ArrayList<UserSDCashHistorySRO>();

    public GetUserSDCashHistoryInDateRangeResponse() {
    }

    public GetUserSDCashHistoryInDateRangeResponse(List<UserSDCashHistorySRO> getUserSDCashHistoryInDateRange) {
        super();
        this.getUserSDCashHistoryInDateRange = getUserSDCashHistoryInDateRange;
    }

    public List<UserSDCashHistorySRO> getGetUserSDCashHistoryInDateRange() {
        return getUserSDCashHistoryInDateRange;
    }

    public void setUserSDCashHistoryInDateRange(List<UserSDCashHistorySRO> getUserSDCashHistoryInDateRange) {
        this.getUserSDCashHistoryInDateRange = getUserSDCashHistoryInDateRange;
    }

}
