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
import com.snapdeal.ums.admin.sdwallet.sro.SDWalletInfoSRO;

public class SDWalletResponse extends ServiceResponse {
    /**
     * 
     */
    private static final long serialVersionUID = -8473235107844110441L;
    @Tag(5)
    private SDWalletInfoSRO   sdWalletInfoSRO;

    public SDWalletResponse() {
    }

    public SDWalletInfoSRO getSdWalletInfoSRO() {
        return sdWalletInfoSRO;
    }

    public void setSdWalletInfoSRO(SDWalletInfoSRO sdWalletInfoSRO) {
        this.sdWalletInfoSRO = sdWalletInfoSRO;
    }

    public SDWalletResponse(SDWalletInfoSRO sdWalletInfoSRO) {
        super();
        this.sdWalletInfoSRO = sdWalletInfoSRO;
    }
}
