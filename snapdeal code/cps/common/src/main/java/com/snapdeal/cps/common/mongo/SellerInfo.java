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

@Document(collection = "sellerInfo")
public class SellerInfo {

    @Id
    private String internalCode;
    private String name;
    private String displayName;

    public SellerInfo(String internalCode, String name, String displayName) {
        this.internalCode = internalCode;
        this.name = name;
        this.displayName = displayName;
    }

    public String getInternalCode() {
        return internalCode;
    }

    public void setInternalCode(String internalCode) {
        this.internalCode = internalCode;
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

    @Override
    public String toString() {
        return "SellerInfo [internalCode=" + internalCode + ", name=" + name + ", displayName=" + displayName + "]";
    }

}