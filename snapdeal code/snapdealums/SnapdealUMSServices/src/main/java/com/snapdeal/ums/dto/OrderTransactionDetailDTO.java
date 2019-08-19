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
public class OrderTransactionDetailDTO implements Serializable {

    private static final long serialVersionUID = 9127996454203827703L;

    private String            sdPaymentStatus;
    private String            pgPaymentStatus;
    private String            code;
    private String            suborder;
    private String            paymentMode;
    private String            paymentModeSubtype;
    private String            paymentGateway;
    private String            pgOutgoingParams;
    private String            pgIncomingParams;
    private String            pgResponseMessage;
    private int               paymentAmount;
    private Date              created;

    public OrderTransactionDetailDTO() {
    }

    public OrderTransactionDetailDTO(String paymentStatus, String code, String paymentMode, String paymentModeSubtype, String paymentGateway, String pgOutgoingParams,
            String pgIncomingParams, String pgResponseMessage, int paymentAmount, Date created) {
        this.sdPaymentStatus = paymentStatus;
        this.code = code;
        this.paymentMode = paymentMode;
        this.paymentModeSubtype = paymentModeSubtype;
        this.paymentGateway = paymentGateway;
        this.pgOutgoingParams = pgOutgoingParams;
        this.pgIncomingParams = pgIncomingParams;
        this.pgResponseMessage = pgResponseMessage;
        this.paymentAmount = paymentAmount;
        this.created = created;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaymentModeSubtype() {
        return paymentModeSubtype;
    }

    public void setPaymentModeSubtype(String paymentModeSubtype) {
        this.paymentModeSubtype = paymentModeSubtype;
    }

    public String getPaymentGateway() {
        return paymentGateway;
    }

    public void setPaymentGateway(String paymentGateway) {
        this.paymentGateway = paymentGateway;
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

    public int getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(int paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getSdPaymentStatus() {
        return sdPaymentStatus;
    }

    public void setSdPaymentStatus(String sdPaymentStatus) {
        this.sdPaymentStatus = sdPaymentStatus;
    }

    public String getPgPaymentStatus() {
        return pgPaymentStatus;
    }

    public void setPgPaymentStatus(String pgPaymentStatus) {
        this.pgPaymentStatus = pgPaymentStatus;
    }

    public void setSuborder(String suborder) {
        this.suborder = suborder;
    }

    public String getSuborder() {
        return suborder;
    }

}
