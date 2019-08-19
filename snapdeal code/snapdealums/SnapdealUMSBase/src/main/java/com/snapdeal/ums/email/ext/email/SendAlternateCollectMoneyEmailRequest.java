
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendAlternateCollectMoneyEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -8128976642803131920L;
	@Tag(3)
    private double collectableAmount;
    @Tag(4)
    private Integer orderId;
    @Tag(5)
    private Integer originalSuborderId;
    @Tag(6)
    private Integer alternateSuborderId;
    @Tag(7)
    private String buyPageurl;

    public SendAlternateCollectMoneyEmailRequest() {
    }

    public SendAlternateCollectMoneyEmailRequest(double collectableAmount, Integer orderId, Integer originalSuborderId, Integer alternateSuborderId, String buyPageurl) {
        super();
        this.collectableAmount = collectableAmount;
        this.orderId = orderId;
        this.originalSuborderId = originalSuborderId;
        this.alternateSuborderId = alternateSuborderId;
        this.buyPageurl = buyPageurl;
    }

    public double getCollectableAmount() {
        return collectableAmount;
    }

    public void setCollectableAmount(double collectableAmount) {
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

    public String getBuyPageurl() {
        return buyPageurl;
    }

    public void setBuyPageurl(String buyPageurl) {
        this.buyPageurl = buyPageurl;
    }

}
