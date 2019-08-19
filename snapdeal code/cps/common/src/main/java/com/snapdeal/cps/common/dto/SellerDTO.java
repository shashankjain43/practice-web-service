package com.snapdeal.cps.common.dto;

import com.snapdeal.cps.common.SellerAvailability;

/**
 * @author Sushant Taneja <sushant.taneja@snapdeal.com>
 *
 */
public class SellerDTO {

    // Vendor basic info
    private String code;
    private String name;
    private String displayName;
    
    // IPMS related info
    private int price;
    private Integer mrp;
    private SellerAvailability availability;
    
    // Getters and Setters
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public Integer getMrp() {
        return mrp;
    }
    public void setMrp(Integer mrp) {
        this.mrp = mrp;
    }
    public SellerAvailability getAvailability() {
        return availability;
    }
    public void setAvailability(SellerAvailability availability) {
        this.availability = availability;
    }
    
}
