/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Apr 22, 2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.admin.ext.newsletter;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetFailedCitiesForNewsletterRequest extends ServiceRequest {

    /**
     * 
     */
    private static final long serialVersionUID = 5487795910794860143L;
    
    @Tag(3)
    private Integer newsletterId;
    
    public GetFailedCitiesForNewsletterRequest() {
        super();
    }
    
    public GetFailedCitiesForNewsletterRequest(Integer newsletterId){
        super();
        this.newsletterId = newsletterId;
    }

    public Integer getNewsletterId() {
        return newsletterId;
    }

    public void setNewsletterId(Integer newsletterId) {
        this.newsletterId = newsletterId;
    }
    
}
