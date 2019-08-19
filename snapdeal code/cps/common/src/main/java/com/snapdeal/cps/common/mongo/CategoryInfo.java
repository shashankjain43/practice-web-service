package com.snapdeal.cps.common.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "categoryInfo")
public class CategoryInfo {

    @Id
    private int subCategoryId;
    private String subCategoryName;
    private int parentCategoryId;
    private String parentCategoryName;

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public int getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(int parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getParentCategoryName() {
        return parentCategoryName;
    }

    public void setParentCategoryName(String parentCategoryName) {
        this.parentCategoryName = parentCategoryName;
    }

    @Override
    public String toString() {
        return "CategoryInfo [subCategoryId=" + subCategoryId + ", parentCategoryId=" + parentCategoryId + "]";
    }

}
