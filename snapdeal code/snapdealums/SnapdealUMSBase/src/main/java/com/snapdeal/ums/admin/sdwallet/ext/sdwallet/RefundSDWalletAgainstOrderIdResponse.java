package com.snapdeal.ums.admin.sdwallet.ext.sdwallet;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class RefundSDWalletAgainstOrderIdResponse extends ServiceResponse {

    /**
     * 
     */
    private static final long serialVersionUID = -2135067959213310497L;
    @Tag(5)
    private Integer           refundSDWalletAgainstOrderId;

    public RefundSDWalletAgainstOrderIdResponse() {
    }

    public RefundSDWalletAgainstOrderIdResponse(Integer refundSDWalletAgainstOrderId) {
        super();
        this.refundSDWalletAgainstOrderId = refundSDWalletAgainstOrderId;
    }

    public Integer getRefundSDWalletAgainstOrderId() {
        return refundSDWalletAgainstOrderId;
    }

    public void setRefundSDWalletAgainstOrderId(Integer refundSDWalletAgainstOrderId) {
        this.refundSDWalletAgainstOrderId = refundSDWalletAgainstOrderId;
    }

}
