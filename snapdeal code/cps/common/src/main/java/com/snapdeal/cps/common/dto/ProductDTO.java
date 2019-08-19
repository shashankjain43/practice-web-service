/**
 *  Copyright 2014 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
  
package com.snapdeal.cps.common.dto;

import java.util.List;

import com.snapdeal.cps.common.SellerAvailability;


/**
 *  @version   1.0, Jul 10, 2014
 *  @author 	Sushant Taneja <sushant.taneja@snapdeal.com>
 */

public class ProductDTO {

    // Catalog Info
    private long pogId;
    private String title;
    private String url;
    private String description;
    private String brand;
    private int subCategoryId;
    private String subCategoryName;
    private int parentCategoryId;
    private String parentCategoryName;
    private String imageLink;
    
    private String availability;
    
    private List<SellerDTO> sellers;
    
    public ProductDTO(long pogId) {
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

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public List<SellerDTO> getSellers() {
        return sellers;
    }

    public void setSellers(List<SellerDTO> sellers) {
        this.sellers = sellers;
    }

    public SellerDTO getPrimarySeller(){
        if(sellers == null || sellers.isEmpty()){
            return null;
        }
        for(SellerDTO seller: sellers){
            if(seller.getAvailability().equals(SellerAvailability.IN_STOCK)){
                return seller;
            }
        }
        
        return sellers.get(0);
    }
    
}
