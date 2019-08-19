
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendPrebookPaymentConfirmationEmailRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -2817978367107588553L;
    @Tag(3)
    private Integer orderId;
    @Tag(4)
    private Integer suborderId;
    @Tag(5)
    private Integer collectedAmount;
    @Tag(6)
    private Integer prebookAmount;

    public SendPrebookPaymentConfirmationEmailRequest() {
    }
    

    public SendPrebookPaymentConfirmationEmailRequest(Integer orderId, Integer suborderId, Integer collectedAmount, Integer prebookAmount) {
        super();
        this.orderId = orderId;
        this.suborderId = suborderId;
        this.collectedAmount = collectedAmount;
        this.prebookAmount = prebookAmount;
    }



    public Integer getOrderId() {
        return orderId;
    }


    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }


    public Integer getSuborderId() {
        return suborderId;
    }


    public void setSuborderId(Integer suborderId) {
        this.suborderId = suborderId;
    }


    public Integer getCollectedAmount() {
        return collectedAmount;
    }

    public void setCollectedAmount(Integer collectedAmount) {
        this.collectedAmount = collectedAmount;
    }

    public Integer getPrebookAmount() {
        return prebookAmount;
    }

    public void setPrebookAmount(Integer prebookAmount) {
        this.prebookAmount = prebookAmount;
    }

}
