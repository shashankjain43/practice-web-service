/*
 *  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 22-Oct-2012
 *  @author naveen
 */
package com.snapdeal.ums.core.sro.email;

import java.io.Serializable;

import com.dyuproject.protostuff.Tag;

public class SuborderCancellationEmailSRO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2637122793419155112L;
    @Tag(1)
    private String            productName;
    @Tag(2)
    private Integer           refundAmount;
    @Tag(3)
    private Integer           refundsdCash;
    @Tag(4)
    private Integer           shippingCharges;

    /*public SuborderCancellationEmailSRO(
            OrderCancellationRequest cancellationRequest, String productName,
            Integer shippingCharges) {
        if (cancellationRequest != null) {
            this.productName = productName;
            this.refundAmount = cancellationRequest.getRefundAmount();
            this.refundsdCash = cancellationRequest.getRefundSDCash();
            this.shippingCharges = shippingCharges;
        }
    }

    public SuborderCancellationEmailSRO(SuborderCancellationEmailDTO dto) {
        this.productName = dto.getProductName();
        this.refundAmount = dto.getRefundAmount();
        this.refundsdCash = dto.getRefundsdCash();
        this.shippingCharges = dto.getShippingCharges();
    }*/

    public SuborderCancellationEmailSRO(String productName, Integer refundAmount, Integer refundsdCash, Integer shippingCharges) {
        super();
        this.productName = productName;
        this.refundAmount = refundAmount;
        this.refundsdCash = refundsdCash;
        this.shippingCharges = shippingCharges;
    }
   
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Integer refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Integer getRefundsdCash() {
        return refundsdCash;
    }

    public void setRefundsdCash(Integer refundsdCash) {
        this.refundsdCash = refundsdCash;
    }

    public Integer getShippingCharges() {
        return shippingCharges;
    }

    public void setShippingCharges(Integer shippingCharges) {
        this.shippingCharges = shippingCharges;
    }

}
