/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 09-Jan-2013
 *  @author himanshu
 */
package com.snapdeal.ums.admin.sdwallet.ext.sdwallet;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class ModifySDWalletActivityTypeResponse extends ServiceResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 6721717826683329949L;
    @Tag(5)
    private boolean           modifySDWalletActivityType;

    public boolean isModifySDWalletActivityType() {
        return modifySDWalletActivityType;
    }

    public void setModifySDWalletActivityType(boolean modifySDWalletActivityType) {
        this.modifySDWalletActivityType = modifySDWalletActivityType;
    }

    public ModifySDWalletActivityTypeResponse(boolean modifySDWalletActivityType) {
        super();
        this.modifySDWalletActivityType = modifySDWalletActivityType;
    }

    public ModifySDWalletActivityTypeResponse() {
        super();
    }

}
