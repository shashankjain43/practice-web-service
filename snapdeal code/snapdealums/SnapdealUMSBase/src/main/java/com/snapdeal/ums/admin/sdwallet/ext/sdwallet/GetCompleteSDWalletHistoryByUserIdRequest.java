
package com.snapdeal.ums.admin.sdwallet.ext.sdwallet;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetCompleteSDWalletHistoryByUserIdRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 5144178339273449639L;
    @Tag(3)
    private Integer userId;

    public GetCompleteSDWalletHistoryByUserIdRequest() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
