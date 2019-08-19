package com.snapdeal.ums.admin.sdwallet.ext.sdwallet;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class DebitSDWalletResponse extends ServiceResponse {

    /**
     * 
     */
    private static final long serialVersionUID = -8607230495319810305L;
    @Tag(5)
    private Integer           transactionId;

    public DebitSDWalletResponse() {
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public DebitSDWalletResponse(Integer transactionId) {
        super();
        this.transactionId = transactionId;
    }
}
