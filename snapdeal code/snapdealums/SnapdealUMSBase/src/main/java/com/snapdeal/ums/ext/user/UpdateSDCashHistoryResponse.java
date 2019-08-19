
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.user.UserSDCashHistorySRO;

public class UpdateSDCashHistoryResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 222105320241278059L;
	@Tag(5)
    private UserSDCashHistorySRO updateSDCashHistory;

    public UpdateSDCashHistoryResponse() {
    }

    public UpdateSDCashHistoryResponse(UserSDCashHistorySRO updateSDCashHistory) {
        super();
        this.updateSDCashHistory = updateSDCashHistory;
    }

    public UserSDCashHistorySRO getUpdateSDCashHistory() {
        return updateSDCashHistory;
    }

    public void setUpdateSDCashHistory(UserSDCashHistorySRO updateSDCashHistory) {
        this.updateSDCashHistory = updateSDCashHistory;
    }

}
