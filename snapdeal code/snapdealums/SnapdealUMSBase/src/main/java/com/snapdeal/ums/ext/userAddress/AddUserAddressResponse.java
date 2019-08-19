/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 15-Oct-2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.ext.userAddress;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class AddUserAddressResponse extends ServiceResponse{

    /**
     * 
     */
    private static final long serialVersionUID = 8518684435471224625L;
    
    @Tag(5)
    private Integer savedAddressId;
    
    public AddUserAddressResponse(){
        super();
    }

	public Integer getSavedAddressId() {
		return savedAddressId;
	}

	public void setSavedAddressId(Integer savedAddressId) {
		this.savedAddressId = savedAddressId;
	}

}
