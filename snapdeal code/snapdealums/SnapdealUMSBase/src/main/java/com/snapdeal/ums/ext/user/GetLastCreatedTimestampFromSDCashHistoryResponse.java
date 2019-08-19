
package com.snapdeal.ums.ext.user;

import java.util.Date;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class GetLastCreatedTimestampFromSDCashHistoryResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2026352089085578056L;
	@Tag(5)
    private Date getLastCreatedTimestampFromSDCashHistory;

    public GetLastCreatedTimestampFromSDCashHistoryResponse() {
    }

    public GetLastCreatedTimestampFromSDCashHistoryResponse(Date getLastCreatedTimestampFromSDCashHistory) {
        super();
        this.getLastCreatedTimestampFromSDCashHistory = getLastCreatedTimestampFromSDCashHistory;
    }

    public Date getGetLastCreatedTimestampFromSDCashHistory() {
        return getLastCreatedTimestampFromSDCashHistory;
    }

    public void setLastCreatedTimestampFromSDCashHistory(Date getLastCreatedTimestampFromSDCashHistory) {
        this.getLastCreatedTimestampFromSDCashHistory = getLastCreatedTimestampFromSDCashHistory;
    }

}
