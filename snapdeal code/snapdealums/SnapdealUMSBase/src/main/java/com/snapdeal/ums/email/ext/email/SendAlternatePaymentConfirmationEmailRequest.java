
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendAlternatePaymentConfirmationEmailRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -7815243785138302155L;
    @Tag(3)
    private int collectableAmount;
    @Tag(4)
    private Integer orderId;
    @Tag(5)
    private Integer originalSuborderId;
    @Tag(6)
    private Integer alternateSuborderId;

    public SendAlternatePaymentConfirmationEmailRequest() {
    }

    public SendAlternatePaymentConfirmationEmailRequest(int collectableAmount, Integer orderId, Integer originalSuborderId, Integer alternateSuborderId) {
        super();
        this.collectableAmount = collectableAmount;
        this.orderId = orderId;
        this.originalSuborderId = originalSuborderId;
        this.alternateSuborderId = alternateSuborderId;
    }

    public int getCollectableAmount() {
        return collectableAmount;
    }

    public void setCollectableAmount(int collectableAmount) {
        this.collectableAmount = collectableAmount;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOriginalSuborderId() {
        return originalSuborderId;
    }

    public void setOriginalSuborderId(Integer originalSuborderId) {
        this.originalSuborderId = originalSuborderId;
    }

    public Integer getAlternateSuborderId() {
        return alternateSuborderId;
    }

    public void setAlternateSuborderId(Integer alternateSuborderId) {
        this.alternateSuborderId = alternateSuborderId;
    }


}
