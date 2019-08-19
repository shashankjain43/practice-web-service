/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 17-Jan-2013
 *  @author himanshu
 */
package com.snapdeal.ums.admin.sdwallet.ext.sdwallet;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.admin.sdwallet.sro.SDWalletHistoryInfoSRO;

public class SDWalletHistoryResponse extends ServiceResponse {

    /**
     * 
     */
    private static final long      serialVersionUID = -5249428750847031122L;
    @Tag(5)
    private SDWalletHistoryInfoSRO sdWalletHistoryInfoSRO;

    public SDWalletHistoryInfoSRO getSdWalletHistoryInfoSRO() {
        return sdWalletHistoryInfoSRO;
    }

    public void setSdWalletHistoryInfoSRO(SDWalletHistoryInfoSRO sdWalletHistoryInfoSRO) {
        this.sdWalletHistoryInfoSRO = sdWalletHistoryInfoSRO;
    }

    public SDWalletHistoryResponse(SDWalletHistoryInfoSRO sdWalletHistoryInfoSRO) {
        super();
        this.sdWalletHistoryInfoSRO = sdWalletHistoryInfoSRO;
    }

    public SDWalletHistoryResponse() {
        super();
    }

}
