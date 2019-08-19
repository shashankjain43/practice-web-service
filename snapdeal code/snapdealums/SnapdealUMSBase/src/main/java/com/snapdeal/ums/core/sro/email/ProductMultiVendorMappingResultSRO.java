/*
*  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
*  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*  
*  @version     1.0, 22-Oct-2012
*  @author naveen
*/
package com.snapdeal.ums.core.sro.email;

import java.io.Serializable;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.core.dto.ProductMultiVendorMappingResultDTO;

public class ProductMultiVendorMappingResultSRO implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -8623822199358667318L;
    @Tag(1)
    private String            supc;
    @Tag(2)
    private String            vendorCode;
    @Tag(3)
    private String            message;

    public ProductMultiVendorMappingResultSRO(ProductMultiVendorMappingResultDTO dto) {
        this.supc = dto.getSupc();
        this.vendorCode = dto.getVendorCode();
        this.message = dto.getMessage();
    }

    public String getSupc() {
        return supc;
    }

    public void setSupc(String supc) {
        this.supc = supc;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
