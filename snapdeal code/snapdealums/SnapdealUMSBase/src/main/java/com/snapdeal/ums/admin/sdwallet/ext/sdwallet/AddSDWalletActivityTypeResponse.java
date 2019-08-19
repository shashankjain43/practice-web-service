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

public class AddSDWalletActivityTypeResponse extends ServiceResponse {

    /**
     * 
     */
    private static final long serialVersionUID = -6529706730693347310L;
    @Tag(5)
    private boolean           addActivityType;

    public boolean isAddActivityType() {
        return addActivityType;
    }

    public void setAddActivityType(boolean addActivityType) {
        this.addActivityType = addActivityType;
    }

    public AddSDWalletActivityTypeResponse(boolean addActivityType) {
        super();
        this.addActivityType = addActivityType;
    }

    public AddSDWalletActivityTypeResponse() {
        super();
    }
}
