/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Apr 16, 2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class IsVisaBenefitAvailedRequest extends ServiceRequest {

    /**
     * 
     */
    private static final long serialVersionUID = -4178725859722617482L;

    @Tag(3)
    private int               userId;

    @Tag(4)
    private String            cardNumber;

    public IsVisaBenefitAvailedRequest() {
        super();
    }

    public IsVisaBenefitAvailedRequest(int userId, String cardNumber) {
        super();
        this.userId = userId;
        this.cardNumber = cardNumber;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

}
