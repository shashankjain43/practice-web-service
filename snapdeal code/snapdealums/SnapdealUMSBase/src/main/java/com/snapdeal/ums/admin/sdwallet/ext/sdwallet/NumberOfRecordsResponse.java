/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 17-Jan-2013
 *  @author himanshu
 */
package com.snapdeal.ums.admin.sdwallet.ext.sdwallet;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class NumberOfRecordsResponse extends ServiceResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 2546351451682580304L;
    @Tag(5)
    private Integer           numberOfRecords;

    public Integer getNumberOfRecords() {
        return numberOfRecords;
    }

    public void setNumberOfRecords(Integer numberOfRecords) {
        this.numberOfRecords = numberOfRecords;
    }

    public NumberOfRecordsResponse() {
        super();
    }

}
