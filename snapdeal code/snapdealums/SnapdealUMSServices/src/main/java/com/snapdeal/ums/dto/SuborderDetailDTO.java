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
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.snapdeal.catalog.base.sro.CatalogSRO;
import com.snapdeal.oms.base.dto.ShippingHistoryDTO;
import com.snapdeal.oms.base.model.SuborderPaymentDTO;
import com.snapdeal.oms.base.sro.order.OrderRefundSuborderMappingSRO;
import com.snapdeal.oms.base.sro.order.OrderTransactionSuborderMappingSRO;

@XmlRootElement
public class SuborderDetailDTO implements Serializable {

    private static final long                        serialVersionUID             = 9127996454205466323L;

    private int                                      id;
    private int                                      itemId;
    private String                                   itemName;
    private String                                   itemTitle;
    private String                                   pageUrl;
    private String                                   status;
    private String                                   statusCode;
    private String                                   code;
    private String                                   encodedCode;
    private String                                   orderCode;
    private String                                   suborderType;
    private String                                   secondorySuborderType;
    private String                                   referenceCode;
    private Integer                                  sellingPrice;
    private int                                      paidAmount;
    private int                                      sdCash;
    private int                                      promoValue;
    private int                                      sdCashCredited;
    private boolean                                  redeemed;
    private Date                                     validUpto;
    private Date                                     redemptionDate;
    private String                                   merchantPromoCode;
    private String                                   shippingStatusCode;
    private String                                   customerName;
    private Integer                                  shippingCharges;
    private Integer                                  shippingMethodCharges;
    private String                                   shipmentNumber;
    private String                                   shipmentTrackingLink;
    private String                                   shippingProvider;
    private Date                                     shippingDate;
    private String                                   shippingStatus;
    private Date                                     created;
    private CatalogSRO                               catalog;
    private String                                   shippingPackageRefCode;
    private String                                   expectedDeliveryDate;
    private String                                   expectedDeliveryDateText;
    private Date                                     expectedShippingDate;
    private boolean                                  parentSuborder;
    private String                                   childOrderCode;
    private String                                   childOrderEncodeCode;
    private String                                   orderSummaryViewSource;
    private String                                   userEmail;
    private String                                   vendorName;
    private String                                   primarySuborderCode;
    private String                                   secondarySuborderCode;
    private String                                   alternateSuborderCode;
    private int                                      offeredDiscount;
    private int                                      refundedAmount;
    private boolean                                  alternate;
    private boolean                                  prebook;
    private Integer                                  pendingPrebookAmount;

    private List<ShippingHistoryDTO>                 shippingHistory              = new ArrayList<ShippingHistoryDTO>();

    private List<OrderTransactionSuborderMappingSRO> orderTransactionSuborderMaps = new ArrayList<OrderTransactionSuborderMappingSRO>();

    private List<OrderRefundSuborderMappingSRO>      orderRefundSuborderMaps      = new ArrayList<OrderRefundSuborderMappingSRO>();

    private List<String>                             freebies;

    private List<SuborderPaymentDTO>                 suborderPaymentDTOs          = new ArrayList<SuborderPaymentDTO>();

    private OrderItemDetailDTO                       orderItem;

    public SuborderDetailDTO() {
    }

    public SuborderDetailDTO(int id, String status, String code, String suborderType, Integer sellingPrice, int paidAmount, int sdCash, int promoValue, Integer shippingCharges,
            Integer shippingMethodCharges, Date created) {
        this.id = id;
        this.status = status;
        this.code = code;
        this.suborderType = suborderType;
        this.sellingPrice = sellingPrice;
        this.paidAmount = paidAmount;
        this.sdCash = sdCash;
        this.promoValue = promoValue;
        this.shippingCharges = shippingCharges;
        this.shippingMethodCharges = shippingMethodCharges;
        this.created = created;
    }

    public SuborderDetailDTO(int id, String status, String code, String suborderType, Integer sellingPrice, int paidAmount, int sdCash, int promoValue, Integer shippingCharges,
            Integer shippingMethodCharges, Date created, CatalogSRO catalogDTO) {
        this.id = id;
        this.status = status;
        this.code = code;
        this.suborderType = suborderType;
        this.sellingPrice = sellingPrice;
        this.paidAmount = paidAmount;
        this.sdCash = sdCash;
        this.promoValue = promoValue;
        this.shippingCharges = shippingCharges;
        this.shippingMethodCharges = shippingMethodCharges;
        this.created = created;
        this.catalog = catalogDTO;

    }

    public SuborderDetailDTO(int id, String status, String statusCode, String code, String suborderType, Integer sellingPrice, int paidAmount, int sdCash, int promoValue,
            Integer shippingCharges, Integer shippingMethodCharges, Date created, CatalogSRO catalogDTO, String itemName, String itemTitle) {
        this.id = id;
        this.status = status;
        this.statusCode = statusCode;
        this.code = code;
        this.suborderType = suborderType;
        this.sellingPrice = sellingPrice;
        this.paidAmount = paidAmount;
        this.sdCash = sdCash;
        this.promoValue = promoValue;
        this.shippingCharges = shippingCharges;
        this.shippingMethodCharges = shippingMethodCharges;
        this.created = created;
        this.catalog = catalogDTO;
        this.itemName = itemName;
        this.itemTitle = itemTitle;

    }

    public SuborderDetailDTO(String code, String itemName, String shippingStatus, String expectedDeliveryDateText) {
        this.code = code;
        this.itemName = itemName;
        this.shippingStatus = shippingStatus;
        this.expectedDeliveryDateText = expectedDeliveryDateText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public CatalogSRO getCatalog() {
        return catalog;
    }

    public void setCatalog(CatalogSRO catalogDTO) {
        this.catalog = catalogDTO;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEncodedCode() {
        return encodedCode;
    }

    public void setEncodedCode(String encodedCode) {
        this.encodedCode = encodedCode;
    }

    public String getSuborderType() {
        return suborderType;
    }

    public void setSuborderType(String suborderType) {
        this.suborderType = suborderType;
    }

    public Integer getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Integer sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(int paidAmount) {
        this.paidAmount = paidAmount;
    }

    public int getSdCash() {
        return sdCash;
    }

    public void setSdCash(int sdCash) {
        this.sdCash = sdCash;
    }

    public int getPromoValue() {
        return promoValue;
    }

    public void setPromoValue(int promoValue) {
        this.promoValue = promoValue;
    }

    public int getSdCashCredited() {
        return sdCashCredited;
    }

    public void setSdCashCredited(int sdCashCredited) {
        this.sdCashCredited = sdCashCredited;
    }

    public Integer getShippingCharges() {
        return shippingCharges;
    }

    public void setShippingCharges(Integer shippingCharges) {
        this.shippingCharges = shippingCharges;
    }

    public Integer getShippingMethodCharges() {
        return shippingMethodCharges;
    }

    public void setShippingMethodCharges(Integer shippingMethodCharges) {
        this.shippingMethodCharges = shippingMethodCharges;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getShipmentNumber() {
        return shipmentNumber;
    }

    public void setShipmentNumber(String shipmentNumber) {
        this.shipmentNumber = shipmentNumber;
    }

    public String getShipmentTrackingLink() {
        return shipmentTrackingLink;
    }

    public void setShipmentTrackingLink(String shipmentTrackingLink) {
        this.shipmentTrackingLink = shipmentTrackingLink;
    }

    public String getShippingProvider() {
        return shippingProvider;
    }

    public void setShippingProvider(String shippingProvider) {
        this.shippingProvider = shippingProvider;
    }

    public Date getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Date shippingdate) {
        this.shippingDate = shippingdate;
    }

    public String getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(String shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public boolean isRedeemed() {
        return redeemed;
    }

    public void setRedeemed(boolean redeemed) {
        this.redeemed = redeemed;
    }

    public Date getValidUpto() {
        return validUpto;
    }

    public void setValidUpto(Date validUpto) {
        this.validUpto = validUpto;
    }

    public Date getRedemptionDate() {
        return redemptionDate;
    }

    public void setRedemptionDate(Date redemptionDate) {
        this.redemptionDate = redemptionDate;
    }

    public String getMerchantPromoCode() {
        return merchantPromoCode;
    }

    public void setMerchantPromoCode(String merchantPromoCode) {
        this.merchantPromoCode = merchantPromoCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(String expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public void setShippingPackageRefCode(String shippingPackageRefCode) {
        this.shippingPackageRefCode = shippingPackageRefCode;
    }

    public String getShippingPackageRefCode() {
        return shippingPackageRefCode;
    }

    public void setExpectedDeliveryDateText(String expectedDeliveryDateText) {
        this.expectedDeliveryDateText = expectedDeliveryDateText;
    }

    public String getExpectedDeliveryDateText() {
        return expectedDeliveryDateText;
    }

    public Date getExpectedShippingDate() {
        return expectedShippingDate;
    }

    public void setExpectedShippingDate(Date expectedShippignDate) {
        this.expectedShippingDate = expectedShippignDate;
    }

    public boolean isParentSuborder() {
        return parentSuborder;
    }

    public void setParentSuborder(boolean parentSuborder) {
        this.parentSuborder = parentSuborder;
    }

    public void setShippingHistory(List<ShippingHistoryDTO> shippingHistory) {
        this.shippingHistory = shippingHistory;
    }

    public List<ShippingHistoryDTO> getShippingHistory() {
        return shippingHistory;
    }

    public String getChildOrderCode() {
        return childOrderCode;
    }

    public void setChildOrderCode(String childOrderCode) {
        this.childOrderCode = childOrderCode;
    }

    public String getChildOrderEncodeCode() {
        return childOrderEncodeCode;
    }

    public void setChildOrderEncodeCode(String childOrderEncodeCode) {
        this.childOrderEncodeCode = childOrderEncodeCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderSummaryViewSource() {
        return orderSummaryViewSource;
    }

    public void setOrderSummaryViewSource(String orderSummaryViewSource) {
        this.orderSummaryViewSource = orderSummaryViewSource;
    }

    public List<OrderTransactionSuborderMappingSRO> getOrderTransactionSuborderMaps() {
        return orderTransactionSuborderMaps;
    }

    public void setOrderTransactionSuborderMaps(List<OrderTransactionSuborderMappingSRO> orderTransactionSuborderMaps) {
        this.orderTransactionSuborderMaps = orderTransactionSuborderMaps;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<OrderRefundSuborderMappingSRO> getOrderRefundSuborderMaps() {
        return orderRefundSuborderMaps;
    }

    public void setOrderRefundSuborderMaps(List<OrderRefundSuborderMappingSRO> orderRefundSuborderMaps) {
        this.orderRefundSuborderMaps = orderRefundSuborderMaps;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public String getSecondorySuborderType() {
        return secondorySuborderType;
    }

    public void setSecondorySuborderType(String secondorySuborderType) {
        this.secondorySuborderType = secondorySuborderType;
    }

    // Freebie Accessers
    public List<String> getFreebies() {
        return freebies;
    }

    public void setFreebies(List<String> freebies) {
        this.freebies = freebies;
    }

    public void setSuborderPaymentDTOs(List<SuborderPaymentDTO> suborderPaymentDTOs) {
        this.suborderPaymentDTOs = suborderPaymentDTOs;
    }

    public List<SuborderPaymentDTO> getSuborderPaymentDTOs() {
        return suborderPaymentDTOs;
    }

    public void setOrderItem(OrderItemDetailDTO orderItem) {
        this.orderItem = orderItem;
    }

    public OrderItemDetailDTO getOrderItem() {
        return orderItem;
    }

    public String getAlternateSuborderCode() {
        return alternateSuborderCode;
    }

    public void setAlternateSuborderCode(String alternateSuborderCode) {
        this.alternateSuborderCode = alternateSuborderCode;
    }

    public boolean isAlternate() {
        return alternate;
    }

    public void setAlternate(boolean alternate) {
        this.alternate = alternate;
    }

    public void setPrimarySuborderCode(String primarySuborderCode) {
        this.primarySuborderCode = primarySuborderCode;
    }

    public String getPrimarySuborderCode() {
        return primarySuborderCode;
    }

    public void setSecondarySuborderCode(String secondarySuborderCode) {
        this.secondarySuborderCode = secondarySuborderCode;
    }

    public String getSecondarySuborderCode() {
        return secondarySuborderCode;
    }

    public int getOfferedDiscount() {
        return offeredDiscount;
    }

    public void setOfferedDiscount(int offeredDiscount) {
        this.offeredDiscount = offeredDiscount;
    }

    public int getRefundedAmount() {
        return refundedAmount;
    }

    public void setRefundedAmount(int refundedAmount) {
        this.refundedAmount = refundedAmount;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof SuborderDetailDTO)) {
            return false;
        }
        SuborderDetailDTO other = (SuborderDetailDTO) obj;
        if (code == null) {
            if (other.code != null) {
                return false;
            }
        } else if (!code.equals(other.code)) {
            return false;
        }
        return true;
    }

    public boolean isPrebook() {
        return prebook;
    }

    public void setPrebook(boolean prebook) {
        this.prebook = prebook;
    }

    public Integer getPendingPrebookAmount() {
        return pendingPrebookAmount;
    }

    public void setPendingPrebookAmount(Integer pendingPrebookAmount) {
        this.pendingPrebookAmount = pendingPrebookAmount;
    }

    public String getShippingStatusCode() {
        return shippingStatusCode;
    }

    public void setShippingStatusCode(String shippingStatusCode) {
        this.shippingStatusCode = shippingStatusCode;
    }

}
