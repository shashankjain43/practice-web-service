/*
 *  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 21-Dec-2012
 *  @author himanshu
 */
package com.snapdeal.ums.admin.sdwallet.sro;

import java.io.Serializable;
import java.util.List;

import com.dyuproject.protostuff.Tag;

public class SDWalletHistoryInfoSRO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7353960214152641672L;
    @Tag(1)
    private List<SDWalletHistorySRO> sdWalletHistorySRO;

    public List<SDWalletHistorySRO> getSdWalletHistorySRO() {
        return sdWalletHistorySRO;
    }

    public void setSdWalletHistorySRO(List<SDWalletHistorySRO> sdWalletHistorySRO) {
        this.sdWalletHistorySRO = sdWalletHistorySRO;
    }

}
