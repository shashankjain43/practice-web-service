package com.snapdeal.ums.admin.sdwallet.ext.sdwallet;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.admin.sdwallet.sro.SDWalletHistoryInfoSRO;

public class GetCompleteSDWalletHistoryByUserIdResponse extends ServiceResponse {

    /**
     * 
     */
    private static final long      serialVersionUID = -1751984674669207899L;
    @Tag(5)
    private SDWalletHistoryInfoSRO sdWalletHistoryInfoSRO;

    public GetCompleteSDWalletHistoryByUserIdResponse() {
    }

    public GetCompleteSDWalletHistoryByUserIdResponse(SDWalletHistoryInfoSRO sdWalletHistoryInfoSRO) {
        super();
        this.sdWalletHistoryInfoSRO = sdWalletHistoryInfoSRO;
    }

    public SDWalletHistoryInfoSRO getSdWalletHistoryInfoSRO() {
        return sdWalletHistoryInfoSRO;
    }

    public void setSdWalletHistoryInfoSRO(SDWalletHistoryInfoSRO sdWalletHistoryInfoSRO) {
        this.sdWalletHistoryInfoSRO = sdWalletHistoryInfoSRO;
    }

}
