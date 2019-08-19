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

public class DeleteSDWalletActivityTypeResponse extends ServiceResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 6052666768241917896L;
    @Tag(5)
    private boolean           deleteActivityType;

    public boolean isDeleteActivityType() {
        return deleteActivityType;
    }

    public void setDeleteActivityType(boolean deleteActivityType) {
        this.deleteActivityType = deleteActivityType;
    }

    public DeleteSDWalletActivityTypeResponse(boolean deleteActivityType) {
        super();
        this.deleteActivityType = deleteActivityType;
    }

    public DeleteSDWalletActivityTypeResponse() {
        super();
    }
}
