package com.snapdeal.cps.common.mongo;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "googleKeywordInfo")
public class GoogleKeywordInfo {

    private String brand;
    private int subCategoryId;
    private String keywords;

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

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return "KeywordInfo [brand=" + brand + ", subCategoryId=" + subCategoryId + ", keywords=" + keywords + "]";
    }

}
