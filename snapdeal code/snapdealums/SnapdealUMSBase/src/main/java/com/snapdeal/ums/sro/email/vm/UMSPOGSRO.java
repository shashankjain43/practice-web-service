/**
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.snapdeal.ums.sro.email.vm;

import java.io.Serializable;

import com.dyuproject.protostuff.Tag;

/**
 * @version 1.0, 27-Nov-2013
 * @author Ghanshyam
 */
public class UMSPOGSRO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4053657989519451207L;

    @Tag(1)
    private Long              id;
    @Tag(2)
    private String            name;
    @Tag(3)
    private String            pageUrl;
    @Tag(4)
    private Integer           discount;
    @Tag(5)
    private Integer           price;
    @Tag(6)
    private Integer           sellingPrice;
    @Tag(7)
    private String            imagePath;
    @Tag(8)
    private String            categoryName;
    @Tag(9)
    private String            categoryPageUrl;
    @Tag(10)
    private String            parentCategoryName;
    @Tag(11)
    private String            parentCategoryPageUrl;
    @Tag(12)
    private Double            avgRating;
    @Tag(13)
    private Boolean           codValid;
    @Tag(14)
    private Integer            internalCashBack;

    public UMSPOGSRO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public void calculateDiscount() {
        this.discount = Math.round((this.price - this.sellingPrice) / ((float) this.price) * 100);
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Integer sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Boolean isCodValid() {
        return codValid;
    }

    public void setCodValid(Boolean codValid) {
        this.codValid = codValid;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryPageUrl() {
        return categoryPageUrl;
    }

    public void setCategoryPageUrl(String categoryPageUrl) {
        this.categoryPageUrl = categoryPageUrl;
    }

    public String getParentCategoryName() {
        return parentCategoryName;
    }

    public void setParentCategoryName(String parentCategoryName) {
        this.parentCategoryName = parentCategoryName;
    }

    public String getParentCategoryPageUrl() {
        return parentCategoryPageUrl;
    }

    public void setParentCategoryPageUrl(String parentCategoryPageUrl) {
        this.parentCategoryPageUrl = parentCategoryPageUrl;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }

    /**
     * @return the internalCashBack
     */
    public Integer getInternalCashBack() {
        return internalCashBack;
    }

    /**
     * @param internalCashBack the internalCashBack to set
     */
    public void setInternalCashBack(Integer internalCashBack) {
        this.internalCashBack = internalCashBack;
    }

    /**
     * @return the codValid
     */
    public Boolean getCodValid() {
        return codValid;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((avgRating == null) ? 0 : avgRating.hashCode());
        result = prime * result + ((categoryName == null) ? 0 : categoryName.hashCode());
        result = prime * result + ((categoryPageUrl == null) ? 0 : categoryPageUrl.hashCode());
        result = prime * result + ((codValid == null) ? 0 : codValid.hashCode());
        result = prime * result + ((discount == null) ? 0 : discount.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((imagePath == null) ? 0 : imagePath.hashCode());
        result = prime * result + ((internalCashBack == null) ? 0 : internalCashBack.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((pageUrl == null) ? 0 : pageUrl.hashCode());
        result = prime * result + ((parentCategoryName == null) ? 0 : parentCategoryName.hashCode());
        result = prime * result + ((parentCategoryPageUrl == null) ? 0 : parentCategoryPageUrl.hashCode());
        result = prime * result + ((price == null) ? 0 : price.hashCode());
        result = prime * result + ((sellingPrice == null) ? 0 : sellingPrice.hashCode());
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
        UMSPOGSRO other = (UMSPOGSRO) obj;
        if (avgRating == null) {
            if (other.avgRating != null)
                return false;
        } else if (!avgRating.equals(other.avgRating))
            return false;
        if (categoryName == null) {
            if (other.categoryName != null)
                return false;
        } else if (!categoryName.equals(other.categoryName))
            return false;
        if (categoryPageUrl == null) {
            if (other.categoryPageUrl != null)
                return false;
        } else if (!categoryPageUrl.equals(other.categoryPageUrl))
            return false;
        if (codValid == null) {
            if (other.codValid != null)
                return false;
        } else if (!codValid.equals(other.codValid))
            return false;
        if (discount == null) {
            if (other.discount != null)
                return false;
        } else if (!discount.equals(other.discount))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (imagePath == null) {
            if (other.imagePath != null)
                return false;
        } else if (!imagePath.equals(other.imagePath))
            return false;
        if (internalCashBack == null) {
            if (other.internalCashBack != null)
                return false;
        } else if (!internalCashBack.equals(other.internalCashBack))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (pageUrl == null) {
            if (other.pageUrl != null)
                return false;
        } else if (!pageUrl.equals(other.pageUrl))
            return false;
        if (parentCategoryName == null) {
            if (other.parentCategoryName != null)
                return false;
        } else if (!parentCategoryName.equals(other.parentCategoryName))
            return false;
        if (parentCategoryPageUrl == null) {
            if (other.parentCategoryPageUrl != null)
                return false;
        } else if (!parentCategoryPageUrl.equals(other.parentCategoryPageUrl))
            return false;
        if (price == null) {
            if (other.price != null)
                return false;
        } else if (!price.equals(other.price))
            return false;
        if (sellingPrice == null) {
            if (other.sellingPrice != null)
                return false;
        } else if (!sellingPrice.equals(other.sellingPrice))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UMSPOGDTO [id=" + id + ", name=" + name + ", pageUrl=" + pageUrl + ", discount=" + discount + ", price=" + price + ", displayPrice=" + sellingPrice
                + ", imagePath=" + imagePath + ", categoryName=" + categoryName + ", categoryPageUrl=" + categoryPageUrl + ", parentCategoryName=" + parentCategoryName
                + ", parentCategoryPageUrl=" + parentCategoryPageUrl + ", avgRating=" + avgRating + ", codValid=" + codValid + "]";
    }

}
