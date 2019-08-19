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

import javax.xml.bind.annotation.XmlRootElement;

import com.snapdeal.catalog.base.sro.CatalogSRO;

@XmlRootElement
public class OrderItemDetailDTO implements Serializable {

    private static final long         serialVersionUID = 9127996454203827703L;

    private String                    id;
    private Integer                   orderId;
    private String                    pageUrl;
    private int                       quantity;
    private Integer                   sellingPrice;
    private CatalogSRO                catalog;
    private String                    title;
    private String                    name;
    private int                       sdCash;
    private int                       promoValue;
    private int                       shippingCharges;
    private int                       shippingMethodCharges;
    private Integer                   totalAmount;
    private String                    encodedCode;
    private boolean                   delivered;
    private String                    vendorName;
    private String                    tag;
    private String                    expectedDeliveryDate;
    private String                    tagLastUpdatedBy;
    private int                       amountToCollect;
    private int                       totalSDCash;
    private int                       refundAmount;
    private boolean                   alternate;
    private boolean                   prebook;
    private Integer                   pendingPrebookAmount;
    private int                       offeredDiscount;
    private List<String>              suborderCodeList;
    private int                       emiCharges;

    public OrderItemDetailDTO() {
    }

    public OrderItemDetailDTO(int quantity, String pageUrl, Integer sellingPrice, CatalogSRO catalogDTO, String vendorName) {
        this.quantity = quantity;
        this.pageUrl = pageUrl;
        this.sellingPrice = sellingPrice;
        this.catalog = catalogDTO;
        this.title = catalogDTO.getTitle();
        this.vendorName = vendorName;
    }

    public OrderItemDetailDTO(int quantity, String pageUrl, Integer sellingPrice, CatalogSRO catalogDTO) {
        this.quantity = quantity;
        this.pageUrl = pageUrl;
        this.sellingPrice = sellingPrice;
        this.catalog = catalogDTO;
        this.title = catalogDTO.getTitle();
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public Integer getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Integer sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public CatalogSRO getCatalog() {
        return catalog;
    }

    public void setCatalog(CatalogSRO catalogDTO) {
        this.catalog = catalogDTO;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getShippingCharges() {
        return shippingCharges;
    }

    public void setShippingCharges(int shippingCharges) {
        this.shippingCharges = shippingCharges;
    }

    public int getShippingMethodCharges() {
        return shippingMethodCharges;
    }

    public void setShippingMethodCharges(int shippingMethodCharges) {
        this.shippingMethodCharges = shippingMethodCharges;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public String getEncodedCode() {
        return encodedCode;
    }

    public void setEncodedCode(String encodedCode) {
        this.encodedCode = encodedCode;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setExpectedDeliveryDate(String expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public String getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setTagLastUpdatedBy(String lastUpdatedBy) {
        this.tagLastUpdatedBy = lastUpdatedBy;
    }

    public String getTagLastUpdatedBy() {
        return tagLastUpdatedBy;
    }

    public int getAmountToCollect() {
        return amountToCollect;
    }

    public void setAmountToCollect(int amountToCollect) {
        this.amountToCollect = amountToCollect;
    }

    public int getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(int refundAmount) {
        this.refundAmount = refundAmount;
    }

    public int getTotalSDCash() {
        return totalSDCash;
    }

    public void setTotalSDCash(int totalSDCash) {
        this.totalSDCash = totalSDCash;
    }

    public boolean isAlternate() {
        return alternate;
    }

    public void setAlternate(boolean isAlternate) {
        this.alternate = isAlternate;
    }

    public int getOfferedDiscount() {
        return offeredDiscount;
    }

    public void setOfferedDiscount(int offeredDiscount) {
        this.offeredDiscount = offeredDiscount;
    }

    public List<String> getSuborderCodeList() {
        return suborderCodeList;
    }

    public void setSuborderCodeList(List<String> suborderCodeList) {
        this.suborderCodeList = suborderCodeList;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((catalog == null) ? 0 : catalog.hashCode());
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
        if (!(obj instanceof OrderItemDetailDTO)) {
            return false;
        }
        OrderItemDetailDTO other = (OrderItemDetailDTO) obj;
        if (catalog == null) {
            if (other.catalog != null) {
                return false;
            }
        } else if (!catalog.equals(other.catalog)) {
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

    public void setEmiCharges(int emiCharges) {
        this.emiCharges = emiCharges;
    }

    public int getEmiCharges() {
        return emiCharges;
    }
}
