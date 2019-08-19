/*
 *  Copyright 2011 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 24-May-2011
 *  @author KARAN
 */
package com.snapdeal.ums.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class OrderDetailDTO implements Serializable {

    private static final long               serialVersionUID  = 9127996454203827703L;

    private int                             orderId;
    private String                          orderSummaryUrl;
    private String                          orderCode;
    private String                          primaryOrderSummaryUrl;
    private String                          primaryOrderCode;
    private String                          purchaseDate;
    private String                          status;
    private String                          mobile;
    private String                          emailId;
    private int                             totalAmount;
    private int                             paidAmount;
    private int                             sdCash;
    private String                          promoCode;
    private int                             promoValue;
    private String                          customerName;
    private Integer                         zoneId;
    private boolean                         shippingDetailsRequired;
    private int                             shippingCharges;
    private String                          shippingMethod;
    private int                             shippingMethodCharges;
    private String                          addressLine1;
    private String                          addressLine2;
    private String                          city;
    private String                          state;
    private String                          shippingName;
    private String                          pincode;
    private String                          shippingMobile;
    //shippingDetailId required for RayMedi XML
    private int                             shippingDetailId;
    private String                          parentOrderCode;
    private boolean                         childOrder;
    private String                          parentOrderEncodedCode;
    private OrderTransactionDetailDTO       lastTransaction;
    private String                          orderType;
    private List<OrderItemDetailDTO>        orderItems        = new ArrayList<OrderItemDetailDTO>();
    private List<OrderTransactionDetailDTO> orderTransactions = new ArrayList<OrderTransactionDetailDTO>();
    private List<OrderRefundDetailDTO>      orderRefunds      = new ArrayList<OrderRefundDetailDTO>();
    private List<SuborderDetailDTO>         suborders         = new ArrayList<SuborderDetailDTO>();
    private String                          sysMessage = "";
    private boolean                         addressChangeAllowed;
    private int                             internalCashbackValue;
    private int                             externalCashbackValue;
    
    public OrderDetailDTO(int orderId, String orderCode, String purchaseDate, String status, String mobile, String emailId, Integer paidAmount, String customerName,
            Integer zoneId, String addressLine1, String addressLine2, String city, String state, String shippingName, String pincode, String shippingMobile,
            boolean shippingDetailsRequired) {
        this.orderId = orderId;
        this.orderCode = orderCode;
        this.purchaseDate = purchaseDate;
        this.status = status;
        this.mobile = mobile;
        this.emailId = emailId;
        this.paidAmount = paidAmount;
        this.customerName = customerName;
        this.zoneId = zoneId;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.shippingName = shippingName;
        this.pincode = pincode;
        this.shippingMobile = shippingMobile;
        this.shippingDetailsRequired = shippingDetailsRequired;
    }

    public OrderDetailDTO() {

    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderSummaryUrl() {
        return orderSummaryUrl;
    }

    public void setOrderSummaryUrl(String orderSummaryUrl) {
        this.orderSummaryUrl = orderSummaryUrl;
    }

    public String getPrimaryOrderSummaryUrl() {
        return primaryOrderSummaryUrl;
    }

    public void setPrimaryOrderSummaryUrl(String primaryOrderSummaryUrl) {
        this.primaryOrderSummaryUrl = primaryOrderSummaryUrl;
    }

    public String getPrimaryOrderCode() {
        return primaryOrderCode;
    }

    public void setPrimaryOrderCode(String primaryOrderCode) {
        this.primaryOrderCode = primaryOrderCode;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setPaidAmount(int paidAmount) {
        this.paidAmount = paidAmount;
    }

    public void setSdCash(int sdCash) {
        this.sdCash = sdCash;
    }

    public void setPromoValue(int promoValue) {
        this.promoValue = promoValue;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Integer getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Integer paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getSdCash() {
        return sdCash;
    }

    public void setSdCash(Integer sdCash) {
        this.sdCash = sdCash;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public Integer getPromoValue() {
        return promoValue;
    }

    public void setPromoValue(Integer promoValue) {
        this.promoValue = promoValue;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getShippingMobile() {
        return shippingMobile;
    }

    public void setShippingMobile(String shippingMobile) {
        this.shippingMobile = shippingMobile;
    }

    public boolean isShippingDetailsRequired() {
        return shippingDetailsRequired;
    }

    public void setShippingDetailsRequired(boolean shippingDetailsRequired) {
        this.shippingDetailsRequired = shippingDetailsRequired;
    }

    public int getShippingCharges() {
        return shippingCharges;
    }

    public void setShippingCharges(int shippingCharges) {
        this.shippingCharges = shippingCharges;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public int getShippingMethodCharges() {
        return shippingMethodCharges;
    }

    public void setShippingMethodCharges(int shippingMethodCharges) {
        this.shippingMethodCharges = shippingMethodCharges;
    }

    public List<OrderItemDetailDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDetailDTO> orderItems) {
        this.orderItems = orderItems;
    }

    public void setOrderTransactions(List<OrderTransactionDetailDTO> orderTransactions) {
        this.orderTransactions = orderTransactions;
    }

    public OrderTransactionDetailDTO getLastTransaction() {
        return lastTransaction;
    }

    public void setLastTransaction(OrderTransactionDetailDTO successfulTransaction) {
        this.lastTransaction = successfulTransaction;
    }

    public void setOrderTransactions(Set<OrderTransactionDetailDTO> orderTransactions) {
        this.orderTransactions = new ArrayList<OrderTransactionDetailDTO>(orderTransactions);
    }

    public List<OrderTransactionDetailDTO> getOrderTransactions() {
        return orderTransactions;
    }

    public void setOrderRefunds(List<OrderRefundDetailDTO> orderRefunds) {
        this.orderRefunds = orderRefunds;
    }

    public void setOrderRefunds(Set<OrderRefundDetailDTO> orderRefunds) {
        this.orderRefunds = new ArrayList<OrderRefundDetailDTO>(orderRefunds);
    }

    public List<OrderRefundDetailDTO> getOrderRefunds() {
        return orderRefunds;
    }

    public List<SuborderDetailDTO> getSuborders() {
        return suborders;
    }

    public void setSuborders(List<SuborderDetailDTO> suborders) {
        this.suborders = suborders;
    }

    public String getParentOrderCode() {
        return parentOrderCode;
    }

    public void setParentOrderCode(String parentOrderCode) {
        this.parentOrderCode = parentOrderCode;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((orderCode == null) ? 0 : orderCode.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrderDetailDTO dto = (OrderDetailDTO) obj;
        if (orderCode == null) {
            return false;
        } else if (!orderCode.equals(dto.orderCode))
            return false;
        return true;
    }

    public int getShippingDetailId() {
        return shippingDetailId;
    }

    public void setShippingDetailId(int shippingDetailId) {
        this.shippingDetailId = shippingDetailId;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public boolean isChildOrder() {
        return childOrder;
    }

    public void setChildOrder(boolean childOrder) {
        this.childOrder = childOrder;
    }

    public String getParentOrderEncodedCode() {
        return parentOrderEncodedCode;
    }

    public void setParentOrderEncodedCode(String parentOrderEncodedCode) {
        this.parentOrderEncodedCode = parentOrderEncodedCode;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public void setSysMessage(String sysMessage) {
        this.sysMessage = sysMessage;
    }

    public String getSysMessage() {
        return sysMessage;
    }

    public boolean isAddressChangeAllowed() {
        return addressChangeAllowed;
    }

    public void setAddressChangeAllowed(boolean addressChangeAllowed) {
        this.addressChangeAllowed = addressChangeAllowed;
    }

    public int getInternalCashbackValue() {
        return internalCashbackValue;
    }

    public void setInternalCashbackValue(int internalCashbackValue) {
        this.internalCashbackValue = internalCashbackValue;
    }

    public int getExternalCashbackValue() {
        return externalCashbackValue;
    }

    public void setExternalCashbackValue(int externalCashbackValue) {
        this.externalCashbackValue = externalCashbackValue;
    }

}
