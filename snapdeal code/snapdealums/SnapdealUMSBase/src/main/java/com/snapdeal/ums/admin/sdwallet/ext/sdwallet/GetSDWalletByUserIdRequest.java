
package com.snapdeal.ums.admin.sdwallet.ext.sdwallet;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetSDWalletByUserIdRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -558392120741083199L;
    @Tag(3)
    private Integer userId;

    public GetSDWalletByUserIdRequest() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}