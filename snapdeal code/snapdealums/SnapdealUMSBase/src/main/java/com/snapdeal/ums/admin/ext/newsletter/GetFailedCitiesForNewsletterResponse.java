/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Apr 22, 2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.admin.ext.newsletter;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class GetFailedCitiesForNewsletterResponse extends ServiceResponse{

    /**
     * 
     */
    private static final long serialVersionUID = 4488404033774167706L;
    
    @Tag(5)
    private List<Integer> getFailedCitiesForNewsletter = new ArrayList<Integer>();
    
    public GetFailedCitiesForNewsletterResponse(){
        super();
    }

    public List<Integer> getGetFailedCitiesForNewsletter() {
        return getFailedCitiesForNewsletter;
    }

    public void setGetFailedCitiesForNewsletter(List<Integer> getFailedCitiesForNewsletter) {
        this.getFailedCitiesForNewsletter = getFailedCitiesForNewsletter;
    }
    
}
