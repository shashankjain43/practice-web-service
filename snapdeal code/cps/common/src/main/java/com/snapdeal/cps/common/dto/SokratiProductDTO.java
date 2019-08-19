/**
 *  Copyright 2014 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */  
package com.snapdeal.cps.common.dto;

import java.util.Date;

/**
 *  
 *  @version     1.0, 14-Aug-2014
 *  @author Shashank Jain <jain.shashank@snapdeal.com>
 */
public class SokratiProductDTO {
    
    private long pogId;
    private String title;
    private String description;
    private String url;
    private String brand;
    private String mLink;
    private String imageLink;
    private String productType;
    private String googleProductCategory;
    private String adwordsGroup;
    private String availability;
    private int price;
    private String subAccountId;
    private boolean isPrimary;
    private Date updateTs;
    private String operation;
    private String ageGroup;
    private String gender;
    private String customLabel0;
    private String customLabel1;
    private String customLabel2;
    private String customLabel3;
    private final String lang = "en";
    private final String targetCountry = "IN";
    private final String condition= "new";
    private final String adultFlag= "false";
    private final String channel = "online";
    
    public SokratiProductDTO(){
    }
    
    public SokratiProductDTO(long pogId,String description, String title,String url, String operation){
        this.pogId = pogId;
        this.title = title;
        this.description = description;
        this.url= url;
        this.operation = operation;
        this.adwordsGroup = "";
        this.ageGroup = "";
        this.gender = "";
        this.customLabel0 = "";
        this.customLabel1 = "";
        this.customLabel2 = "";
        this.customLabel3 = "";
    }

    public long getPogId() {
        return pogId;
    }

    public void setPogId(long pogId) {
        this.pogId = pogId;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getmLink() {
        return mLink;
    }

    public void setmLink(String mLink) {
        this.mLink = mLink;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
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

    public String getAdwordsGroup() {
        return adwordsGroup;
    }

    public void setAdwordsGroup(String adwordsGroup) {
        this.adwordsGroup = adwordsGroup;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSubAccountId() {
        return subAccountId;
    }

    public void setSubAccountId(String subAccountId) {
        this.subAccountId = subAccountId;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public Date getUpdateTs() {
        return updateTs;
    }

    public void setUpdateTs(Date updateTs) {
        this.updateTs = updateTs;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
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

    public String getCustomLabel0() {
        return customLabel0;
    }

    public void setCustomLabel0(String customLabel0) {
        this.customLabel0 = customLabel0;
    }

    public String getCustomLabel1() {
        return customLabel1;
    }

    public void setCustomLabel1(String customLabel1) {
        this.customLabel1 = customLabel1;
    }

    public String getCustomLabel2() {
        return customLabel2;
    }

    public void setCustomLabel2(String customLabel2) {
        this.customLabel2 = customLabel2;
    }

    public String getCustomLabel3() {
        return customLabel3;
    }

    public void setCustomLabel3(String customLabel3) {
        this.customLabel3 = customLabel3;
    }

    public String getLang() {
        return lang;
    }

    public String getTargetCountry() {
        return targetCountry;
    }

    public String getCondition() {
        return condition;
    }

    public String getAdultFlag() {
        return adultFlag;
    }

    public String getChannel() {
        return channel;
    }
    
    
}
