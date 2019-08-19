/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Apr 24, 2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.admin.ext.newsletter;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.bulkemail.EmailServiceProviderSRO;

public class GetAllESPResponse extends ServiceResponse{
    
    /**
     * 
     */
    private static final long serialVersionUID = 8151021968831952484L;
    @Tag(5)
    private List<EmailServiceProviderSRO> espSROs = new ArrayList<EmailServiceProviderSRO>();
    
    public GetAllESPResponse(){
        super();
    }

    public List<EmailServiceProviderSRO> getEspSROs() {
        return espSROs;
    }

    public void setEspSROs(List<EmailServiceProviderSRO> espSROs) {
        this.espSROs = espSROs;
    }
    
}
