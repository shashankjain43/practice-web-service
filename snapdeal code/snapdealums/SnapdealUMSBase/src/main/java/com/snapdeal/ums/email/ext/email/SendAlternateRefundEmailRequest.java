
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendAlternateRefundEmailRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -1006158434722088171L;
    @Tag(3)
    private Integer orderId;
    
    @Tag(4)
    private Integer suborderId;
    
    @Tag(5)
    private boolean absorb;
    
    @Tag(6)
    private double paidAmount;
    
    @Tag(7)
    private int sdCashRefunded;
    
    @Tag(8)
    private int amountRefunded;
    
    @Tag(9)
    private Integer oldSuborderId;
    
    public SendAlternateRefundEmailRequest() {
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

    public boolean isAbsorb() {
        return absorb;
    }

    public void setAbsorb(boolean absorb) {
        this.absorb = absorb;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public int getSdCashRefunded() {
        return sdCashRefunded;
    }

    public void setSdCashRefunded(int sdCashRefunded) {
        this.sdCashRefunded = sdCashRefunded;
    }

    public int getAmountRefunded() {
        return amountRefunded;
    }

    public void setAmountRefunded(int amountRefunded) {
        this.amountRefunded = amountRefunded;
    }

    public Integer getOldSuborderId() {
        return oldSuborderId;
    }

    public void setOldSuborderId(Integer oldSuborderId) {
        this.oldSuborderId = oldSuborderId;
    }

    public SendAlternateRefundEmailRequest(Integer orderId, Integer suborderId, boolean absorb, double paidAmount, int sdCashRefunded, int amountRefunded, Integer oldSuborderId) {
        super();
        this.orderId = orderId;
        this.suborderId = suborderId;
        this.absorb = absorb;
        this.paidAmount = paidAmount;
        this.sdCashRefunded = sdCashRefunded;
        this.amountRefunded = amountRefunded;
        this.oldSuborderId = oldSuborderId;
    }
    
    
}
