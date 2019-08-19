/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 25-Nov-2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class IsMobileExistRequest extends ServiceRequest{

    /**
     * 
     */
    private static final long serialVersionUID = 343148937349558335L;

    @Tag(3)
    private String mobile;
    
    public IsMobileExistRequest(){
        super();
    }
    
    public IsMobileExistRequest(String mobile){
        super();
        this.mobile = mobile;
    }

    /**
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
