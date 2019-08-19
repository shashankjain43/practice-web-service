
package com.snapdeal.ums.ext.user;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class GetAllUsersFromSDWalletHistoryResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7087139172381041063L;
	@Tag(5)
    private List<Integer> getAllUsersFromSDCashHistory = new ArrayList<Integer>();

    public GetAllUsersFromSDWalletHistoryResponse() {
    }

    public GetAllUsersFromSDWalletHistoryResponse(List<Integer> getAllUsersFromSDCashHistory) {
        super();
        this.getAllUsersFromSDCashHistory = getAllUsersFromSDCashHistory;
    }

    public List<Integer> getGetAllUsersFromSDCashHistory() {
        return getAllUsersFromSDCashHistory;
    }

    public void setAllUsersFromSDCashHistory(List<Integer> getAllUsersFromSDCashHistory) {
        this.getAllUsersFromSDCashHistory = getAllUsersFromSDCashHistory;
    }

}
