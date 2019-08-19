/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 25-Nov-2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class IsMobileExistResponse extends ServiceResponse{

    /**
     * 
     */
    private static final long serialVersionUID = 2786670104170383002L;
    
    @Tag(6)
    private Boolean isMobileExist;
    
    public IsMobileExistResponse(){
        
    }

    /**
     * @return the isMobileExist
     */
    public Boolean getIsMobileExist() {
        return isMobileExist;
    }

    /**
     * @param isMobileExist the isMobileExist to set
     */
    public void setIsMobileExist(Boolean isMobileExist) {
        this.isMobileExist = isMobileExist;
    }
    
}
