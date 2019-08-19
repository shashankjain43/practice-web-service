/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Apr 16, 2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class IsVisaBenefitAvailedResponse extends ServiceResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 1798589848104338805L;

    @Tag(5)
    private boolean           isVisaBenefitAvailed;

    public boolean isVisaBenefitAvailed() {
        return isVisaBenefitAvailed;
    }

    public void setVisaBenefitAvailed(boolean isVisaBenefitAvailed) {
        this.isVisaBenefitAvailed = isVisaBenefitAvailed;
    }
}
