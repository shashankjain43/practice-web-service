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

public class SDWalletInfoSRO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7582773023919687800L;
    @Tag(1)
    private List<SDWalletSRO> sdWalletSRO;

    public List<SDWalletSRO> getSdWalletSRO() {
        return sdWalletSRO;
    }

    public void setSdWalletSRO(List<SDWalletSRO> sdWalletSRO) {
        this.sdWalletSRO = sdWalletSRO;
    }

}
