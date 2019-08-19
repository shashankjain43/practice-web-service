package com.snapdeal.ums.admin.sdwallet.ext.sdwallet;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class GetAvailableBalanceInSDWalletByUserIdResponse extends ServiceResponse {

    /**
     * 
     */
    private static final long serialVersionUID = -3923013712486559743L;
    @Tag(5)
    private Integer           availableAmount;

    public GetAvailableBalanceInSDWalletByUserIdResponse() {
    }

    public Integer getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(Integer availableAmount) {
        this.availableAmount = availableAmount;
    }

    public GetAvailableBalanceInSDWalletByUserIdResponse(Integer availableAmount) {
        super();
        this.availableAmount = availableAmount;
    }

}
