/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 16-Jan-2013
 *  @author himanshu
 */
package com.snapdeal.ums.admin.sdwallet.ext.sdwallet;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.admin.sdwallet.sro.SDWalletHistoryInfoSRO;

public class GetSDWalletHistoryForMobileResponse extends ServiceResponse {

    /**
     * 
     */
    private static final long      serialVersionUID = 100092476690726619L;
    @Tag(5)
    private SDWalletHistoryInfoSRO sdWalletHistoryInfoSRO;

    public GetSDWalletHistoryForMobileResponse() {
    }

    public GetSDWalletHistoryForMobileResponse(SDWalletHistoryInfoSRO sdWalletHistoryInfoSRO) {
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
