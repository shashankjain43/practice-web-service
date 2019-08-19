
package com.snapdeal.ums.admin.sdwallet.ext.sdwallet;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetAvailableBalanceInSDWalletByUserIdRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -1849349748381590820L;
    @Tag(3)
    private Integer userId;

    public GetAvailableBalanceInSDWalletByUserIdRequest() {
    }
    
    public GetAvailableBalanceInSDWalletByUserIdRequest(Integer userId) {
        super();
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
