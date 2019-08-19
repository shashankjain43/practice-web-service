package com.snapdeal.cps.common;

/**
 * @author Sushant Taneja <sushant.taneja@snapdeal.com>
 *
 */
public enum SellerAvailability {
    
    IN_STOCK("in stock"), OUT_OF_STOCK("out of stock"), PREORDER("preorder");
    
    private String status; 
    
    SellerAvailability(String status){
        this.status = status;
    }
    
    public String getStatus(){
        return this.status;
    }
}
