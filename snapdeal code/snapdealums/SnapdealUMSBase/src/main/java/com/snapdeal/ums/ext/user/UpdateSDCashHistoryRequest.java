
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.user.UserSDCashHistorySRO;

public class UpdateSDCashHistoryRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8769389967684233418L;
	@Tag(3)
    private UserSDCashHistorySRO userSDCashHistory;

    public UpdateSDCashHistoryRequest() {
    }

    public UserSDCashHistorySRO getUserSDCashHistory() {
        return userSDCashHistory;
    }

    public void setUserSDCashHistory(UserSDCashHistorySRO userSDCashHistory) {
        this.userSDCashHistory = userSDCashHistory;
    }

    public UpdateSDCashHistoryRequest(UserSDCashHistorySRO userSDCashHistory) {
        this.userSDCashHistory = userSDCashHistory;
    }

}
