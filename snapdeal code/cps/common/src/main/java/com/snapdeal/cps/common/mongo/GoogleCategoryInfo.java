/**
 *  Copyright 2014 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
  
package com.snapdeal.cps.common.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *  @version   1.0, Jul 1, 2014
 *  @author 	Sushant Taneja <sushant.taneja@snapdeal.com>
 */


@Document(collection = "googleCategoryInfo")
public class GoogleCategoryInfo {

    @Id
    private int categoryId;
    private String name;
    private String ageGroup;
    private String gender;
    private boolean banned;
    
    public int getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
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

    public boolean isBanned() {
        return banned;
    }
    
    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    @Override
    public String toString() {
        return "GoogleCategoryInfo [categoryId=" + categoryId + ", name=" + name + ", ageGroup=" + ageGroup + ", gender=" + gender + ", banned=" + banned + "]";
    }

}
