/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Dec 28, 2010
 *  @author rahul
 */
package com.snapdeal.ums.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.utils.DateUtils;
import com.snapdeal.catalog.base.sro.MinProductOfferGroupSRO;
import com.snapdeal.core.sro.serviceDeal.ServiceDealSRO;

@XmlRootElement
public class PopularCatalogSRO implements Serializable {

    private static final long serialVersionUID = -1818346761630322360L;
    @Tag(1)
    private Integer           code;
    @Tag(2)
    private Long              id;
    @Tag(3)
    private String            image;
    @Tag(4)
    private int               price;
    @Tag(5)
    private int               voucherPrice;
    @Tag(6)
    private int               discount;
    @Tag(7)
    private String            pageUrl;
    @Tag(8)
    private String            vendorName;
    @Tag(9)
    private Date              startDate;
    @Tag(10)
    private boolean           addedToday;
    @Tag(11)
    private boolean           codValid;
    @Tag(12)
    private boolean           soldOut;
    @Tag(13)
    private String            name;
    @Tag(14)
    private String            type;
    @Tag(15)
    private String            newsletterLocations;
    @Tag(16)
    private String            categoryUrl;
    @Tag(17)
    private String            categoryName;

    public PopularCatalogSRO() {
    }

    public PopularCatalogSRO(MinProductOfferGroupSRO pdtDTO) {
        this.id = pdtDTO.getId();
        this.image = pdtDTO.getImage();
        this.price = pdtDTO.getPrice();
        this.voucherPrice = pdtDTO.getVoucherPrice();
        this.discount = pdtDTO.getDiscount();
        this.pageUrl = pdtDTO.getPageUrl();
        this.vendorName = pdtDTO.getName();
        this.startDate = pdtDTO.getStartDate();
        this.addedToday = DateUtils.isSameDay(startDate, DateUtils.getCurrentTime());
        this.soldOut = pdtDTO.isSoldOut();
        this.name = pdtDTO.getName();
        this.type = "product";

    }

    public PopularCatalogSRO(ServiceDealSRO deal) {
        this.id = deal.getId();
        this.image = deal.getRecentDealPic();
        this.price = deal.getPrice();
        this.voucherPrice = deal.getSellingPrice();
        this.discount = deal.getDiscount();
        this.pageUrl = deal.getPageUrl();
        this.name = deal.getTitle();
        this.type = "deal";
        this.newsletterLocations = deal.getNewsletterLocations();
    }

    public PopularCatalogSRO(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getVoucherPrice() {
        return voucherPrice;
    }

    public void setVoucherPrice(int voucherPrice) {
        this.voucherPrice = voucherPrice;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public boolean isAddedToday() {
        return addedToday;
    }

    public void setAddedToday(boolean addedToday) {
        this.addedToday = addedToday;
    }

    public void setCodValid(boolean codValid) {
        this.codValid = codValid;
    }

    public boolean isCodValid() {
        return codValid;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    public boolean isSoldOut() {
        return soldOut;
    }

    public void setSoldOut(boolean soldOut) {
        this.soldOut = soldOut;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNewsletterLocations() {
        return newsletterLocations;
    }

    public void setNewsletterLocations(String newsletterLocations) {
        this.newsletterLocations = newsletterLocations;
    }

    public String getCategoryUrl() {
        return categoryUrl;
    }

    public void setCategoryUrl(String categoryUrl) {
        this.categoryUrl = categoryUrl;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PopularCatalogDTO [code=");
        builder.append(code);
        builder.append(", id=");
        builder.append(id);
        builder.append(", image=");
        builder.append(image);
        builder.append(", price=");
        builder.append(price);
        builder.append(", voucherPrice=");
        builder.append(voucherPrice);
        builder.append(", discount=");
        builder.append(discount);
        builder.append(", pageUrl=");
        builder.append(pageUrl);
        builder.append(", vendorName=");
        builder.append(vendorName);
        builder.append(", name=");
        builder.append(name);
        builder.append(", type=");
        builder.append(type);
        builder.append("]");
        return builder.toString();
    }
}
