/**
 *  Copyright 2014 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
  
package com.snapdeal.cps.common.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *  @version   1.0, Jul 2, 2014
 *  @author 	Sushant Taneja <sushant.taneja@snapdeal.com>
 */

@Document(collection = "googleSubaccountInfo")
public class GoogleSubaccountInfo {

    @Id
    private String id;
    private String sellerCode;

    public GoogleSubaccountInfo(String id, String sellerCode) {
        this.id = id;
        this.sellerCode = sellerCode;
    }

    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getSellerCode() {
        return sellerCode;
    }
    
    public void setSellerCode(String sellerCode) {
        this.sellerCode = sellerCode;
    }

    @Override
    public String toString() {
        return "GoogleSubaccountInfo [id=" + id + ", sellerCode=" + sellerCode + "]";
    }
    
}
