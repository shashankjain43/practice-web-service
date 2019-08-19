
package com.snapdeal.ums.ext.user;

import java.util.Date;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetAllUsersFromSDWalletHistoryRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -8064795188306947946L;
	@Tag(3)
    private Date startDate;
    @Tag(4)
    private int firstResult;
    @Tag(5)
    private int maxResults;

    public GetAllUsersFromSDWalletHistoryRequest() {
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public GetAllUsersFromSDWalletHistoryRequest(Date startDate, int firstResult, int maxResults) {
        this.startDate = startDate;
        this.firstResult = firstResult;
        this.maxResults = maxResults;
    }

}
