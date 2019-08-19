
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class IsUserActivityPresentInHistoryResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1178041247325201165L;
	@Tag(5)
    private boolean isUserActivityPresentInHistory;

    public IsUserActivityPresentInHistoryResponse() {
    }

    public IsUserActivityPresentInHistoryResponse(boolean isUserActivityPresentInHistory) {
        super();
        this.isUserActivityPresentInHistory = isUserActivityPresentInHistory;
    }

    public boolean getIsUserActivityPresentInHistory() {
        return isUserActivityPresentInHistory;
    }

    public void setIsUserActivityPresentInHistory(boolean isUserActivityPresentInHistory) {
        this.isUserActivityPresentInHistory = isUserActivityPresentInHistory;
    }

}
