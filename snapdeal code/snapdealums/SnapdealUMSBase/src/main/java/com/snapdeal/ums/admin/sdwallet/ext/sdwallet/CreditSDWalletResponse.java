package com.snapdeal.ums.admin.sdwallet.ext.sdwallet;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class CreditSDWalletResponse extends ServiceResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 2748426880924157928L;
    @Tag(5)
    private Integer           transactionId;

    public CreditSDWalletResponse() {
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public CreditSDWalletResponse(Integer transactionId) {
        super();
        this.transactionId = transactionId;
    }

}
