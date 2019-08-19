/*
 *  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 22-Oct-2012
 *  @author naveen
 */
package com.snapdeal.ums.core.sro.email;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dyuproject.protostuff.Tag;

public class OrderCancellationEmailSRO implements Serializable {

    /**
     * 
     */
    private static final long                  serialVersionUID              = -8577118160190454467L;
    @Tag(1)
    private List<SuborderCancellationEmailSRO> suborderCancellationEmailSROs = new ArrayList<SuborderCancellationEmailSRO>();
    @Tag(2)
    private String                             summaryLink;
    @Tag(3)
    private boolean                            isPrepaidOrder;
    @Tag(4)
    private String                             cancellationReasonCode;
    @Tag(5)
    private String                             emailId;
    @Tag(6)
    private String                             userName;
    @Tag(7)
    private Date                               cancellationDate;
    @Tag(8)
    private Integer                            totalRefundAmount;
    @Tag(9)
    private Integer                            totalSdcashRefund;
    @Tag(10)
    private String                             orderCode;


    public List<SuborderCancellationEmailSRO> getSuborderCancellationEmailSROs() {
        return suborderCancellationEmailSROs;
    }

    public void setSuborderCancellationEmailSROs(List<SuborderCancellationEmailSRO> suborderCancellationEmailSROs) {
        this.suborderCancellationEmailSROs = suborderCancellationEmailSROs;
    }

    public String getSummaryLink() {
        return summaryLink;
    }

    public void setSummaryLink(String summaryLink) {
        this.summaryLink = summaryLink;
    }

    public boolean isPrepaidOrder() {
        return isPrepaidOrder;
    }

    public void setPrepaidOrder(boolean isPrepaidOrder) {
        this.isPrepaidOrder = isPrepaidOrder;
    }

    public String getCancellationReasonCode() {
        return cancellationReasonCode;
    }

    public void setCancellationReasonCode(String cancellationReasonCode) {
        this.cancellationReasonCode = cancellationReasonCode;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCancellationDate() {
        return cancellationDate;
    }

    public void setCancellationDate(Date cancellationDate) {
        this.cancellationDate = cancellationDate;
    }

    public Integer getTotalRefundAmount() {
        return totalRefundAmount;
    }

    public void setTotalRefundAmount(Integer totalRefundAmount) {
        this.totalRefundAmount = totalRefundAmount;
    }

    public Integer getTotalSdcashRefund() {
        return totalSdcashRefund;
    }

    public void setTotalSdcashRefund(Integer totalSdcashRefund) {
        this.totalSdcashRefund = totalSdcashRefund;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
