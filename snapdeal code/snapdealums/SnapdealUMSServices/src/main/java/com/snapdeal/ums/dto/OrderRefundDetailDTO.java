/*
 *  Copyright 2011 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 24-May-2011
 *  @author KARAN
 */
package com.snapdeal.ums.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrderRefundDetailDTO implements Serializable {

    private static final long serialVersionUID = 9127996454203827703L;
    private int               id;
    private String            suborderCode;
    private String            transactionCode;
    private String            statusCode;
    private int               refundAmount;
    private String            refundedBy;
    private String            refundReason;
    private String            refundComments;
    private String            paymentGatewayName;
    private String            pgOutgoingParams;
    private String            pgIncomingParams;
    private String            pgResponseMessage;
    private Integer           chequeNumber;
    private Integer           activityId;
    private Date              created;

    public OrderRefundDetailDTO(int id, String suborderCode, String transactionCode, int refundAmount, String refundedBy, String refundReason, String refundComments, String paymentGatewayName,
            String pgOutgoingParams, String pgIncomingParams, String pgResponseMessage, Integer activityId, Integer chequeNumber, String statusCode, Date created) {
        this.id = id;
        this.suborderCode = suborderCode;
        this.transactionCode = transactionCode;
        this.refundAmount = refundAmount;
        this.refundedBy = refundedBy;
        this.refundReason = refundReason;
        this.refundComments = refundComments;
        this.paymentGatewayName = paymentGatewayName;
        this.pgOutgoingParams = pgOutgoingParams;
        this.pgIncomingParams = pgIncomingParams;
        this.pgResponseMessage = pgResponseMessage;
        this.activityId = activityId;
        this.chequeNumber = chequeNumber;
        this.statusCode = statusCode;
        this.created = created;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
    public String getSuborderCode() {
        return suborderCode;
    }

    public void setSuborderCode(String suborderCode) {
        this.suborderCode = suborderCode;
    }
    
    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public OrderRefundDetailDTO() {
    }

    public int getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(int refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundedBy() {
        return refundedBy;
    }

    public void setRefundedBy(String refundedBy) {
        this.refundedBy = refundedBy;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getRefundComments() {
        return refundComments;
    }

    public void setRefundComments(String refundComments) {
        this.refundComments = refundComments;
    }

    public String getPgOutgoingParams() {
        return pgOutgoingParams;
    }

    public void setPgOutgoingParams(String pgOutgoingParams) {
        this.pgOutgoingParams = pgOutgoingParams;
    }

    public String getPgIncomingParams() {
        return pgIncomingParams;
    }

    public void setPgIncomingParams(String pgIncomingParams) {
        this.pgIncomingParams = pgIncomingParams;
    }

    public String getPgResponseMessage() {
        return pgResponseMessage;
    }

    public void setPgResponseMessage(String pgResponseMessage) {
        this.pgResponseMessage = pgResponseMessage;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getPaymentGatewayName() {
        return paymentGatewayName;
    }

    public void setPaymentGatewayName(String paymentGatewayName) {
        this.paymentGatewayName = paymentGatewayName;
    }

    public Integer getChequeNumber() {
        return chequeNumber;
    }

    public void setChequeNumber(Integer chequeNumber) {
        this.chequeNumber = chequeNumber;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }
}
