/**
 *  Copyright 2014 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
  
package com.snapdeal.cps.common.mongo;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *  @version   1.0, Jul 3, 2014
 *  @author 	Sushant Taneja <sushant.taneja@snapdeal.com>
 */

@Document(collection = "productInfo")
public class ProductInfo {

    @Id
    private long pogId;

    private String title;
    private String url;
    private String description;
    private Integer mrp;
    private int price;
    private String availability;
    private String imageLink;
    private String brand;
    
    private String sellerCode;
    private int subCategoryId;
    private int mrank;
    private Date updateTs;
    
    public ProductInfo(long pogId) {
        this.pogId = pogId;
    }
    
    public long getPogId() {
        return pogId;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Integer getMrp() {
        return mrp;
    }

    public void setMrp(int mrp) {
        this.mrp = mrp;
    }

    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    
    public String getAvailability() {
        return availability;
    }
    public void setAvailability(String availability) {
        this.availability = availability;
    }
    
    public String getImageLink() {
        return imageLink;
    }
    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
    
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    public String getSellerCode() {
        return sellerCode;
    }
    public void setSellerCode(String sellerCode) {
        this.sellerCode = sellerCode;
    }
    
    public int getSubCategoryId() {
        return subCategoryId;
    }
    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }
    
    public int getMrank() {
        return mrank;
    }
    public void setMrank(int mrank) {
        this.mrank = mrank;
    }
    
    public Date getUpdateTs() {
        return updateTs;
    }
    public void setUpdateTs(Date updateTs) {
        this.updateTs = updateTs;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (pogId ^ (pogId >>> 32));
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
        ProductInfo other = (ProductInfo) obj;
        if (pogId != other.pogId)
            return false;
        return true;
    }
    
    public Integer getDiscountPercentage(){
        if(mrp == null){
            return null;
        }
        return ((mrp - price)/mrp)*100;
    }
    
    @Override
    public String toString() {
        return "ProductInfo [pogId=" + pogId + ", mrp=" + mrp + ", price=" + price + ", availability=" + availability + ", sellerCode=" + sellerCode + ", subCategoryId="
                + subCategoryId + "]";
    }

    @SuppressWarnings("unchecked")
    public Map<String,String> toMap() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
        return BeanUtils.describe(this);
    }
}
