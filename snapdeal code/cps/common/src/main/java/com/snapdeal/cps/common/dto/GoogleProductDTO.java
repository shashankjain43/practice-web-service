/**
 *  Copyright 2014 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
  
package com.snapdeal.cps.common.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  @version   1.0, Sep 10, 2014
 *  @author 	Sushant Taneja <sushant.taneja@snapdeal.com>
 */

public class GoogleProductDTO {

    private long pogId;
    private String title;
    private String description;
    private String keywords;
    private String url;
    private String mobileUrl;
    private String imageUrl;
    private String brand;

    private int subCategoryId;
    private int parentCategoryId;
    private String productType;
    private String googleProductCategory;
    private String ageGroup;
    private String gender;
    private int price;
    
    private Map<String, GoogleSellerDTO> sellerInfoMap;
    
    private String adwordsGroup;
    private List<String> customLabels;
    
    private Date updateTs;
    
    private boolean isValid;
    
    public GoogleProductDTO(long pogId){
        this.pogId = pogId;
        this.sellerInfoMap = new HashMap<String, GoogleSellerDTO>();
        this.customLabels = new ArrayList<String>();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMobileUrl() {
        return mobileUrl;
    }

    public void setMobileUrl(String mobileUrl) {
        this.mobileUrl = mobileUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public int getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(int parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getGoogleProductCategory() {
        return googleProductCategory;
    }

    public void setGoogleProductCategory(String googleProductCategory) {
        this.googleProductCategory = googleProductCategory;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
    
    public Map<String,GoogleSellerDTO> getSellers() {
        return sellerInfoMap;
    }

    public void addSeller(GoogleSellerDTO seller) {
        this.sellerInfoMap.put(seller.getSellerCode(), seller);
    }
    
    public String getAdwordsGroup() {
        return adwordsGroup;
    }

    public void setAdwordsGroup(String adwordsGroup) {
        this.adwordsGroup = adwordsGroup;
    }

    public List<String> getCustomLabels() {
        return customLabels;
    }

    public void setCustomLabels(List<String> customLabels) {
        this.customLabels = customLabels;
    }

    public Date getUpdateTs() {
        return updateTs;
    }

    public void setUpdateTs(Date updateTs) {
        this.updateTs = updateTs;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }
    

    
}
