/**
 *  Copyright 2014 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
  
package com.snapdeal.cps.common.dto;

/**
 *  @version   1.0, Sep 11, 2014
 *  @author 	Sushant Taneja <sushant.taneja@snapdeal.com>
 */

public class GoogleSellerDTO {

    private String sellerCode;
    private String sellerName;
    private String subaccountId;
    private String availability;
    private boolean isPrimary;
    

    public String getSellerCode() {
        return sellerCode;
    }

    public void setSellerCode(String sellerCode) {
        this.sellerCode = sellerCode;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSubaccountId() {
        return subaccountId;
    }

    public void setSubaccountId(String subaccountId) {
        this.subaccountId = subaccountId;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
    
    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    
}
