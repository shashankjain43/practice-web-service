
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class IsOrderPresentInHistoryResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4610311391772946970L;
	@Tag(5)
    private boolean isOrderPresentInHistory;

    public IsOrderPresentInHistoryResponse() {
    }

    public IsOrderPresentInHistoryResponse(boolean isOrderPresentInHistory) {
        super();
        this.isOrderPresentInHistory = isOrderPresentInHistory;
    }

    public boolean getIsOrderPresentInHistory() {
        return isOrderPresentInHistory;
    }

    public void setIsOrderPresentInHistory(boolean isOrderPresentInHistory) {
        this.isOrderPresentInHistory = isOrderPresentInHistory;
    }

}
