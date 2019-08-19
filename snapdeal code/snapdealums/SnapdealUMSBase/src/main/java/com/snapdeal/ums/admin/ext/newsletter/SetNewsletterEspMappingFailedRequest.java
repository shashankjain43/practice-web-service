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

public class SetNewsletterEspMappingFailedRequest extends ServiceRequest {

    /**
     * 
     */
    private static final long serialVersionUID = -7305038155946794496L;

    @Tag(3)
    private Integer           newsletterId;

    @Tag(4)
    private Integer           cityId;

    public SetNewsletterEspMappingFailedRequest() {
        super();
    }

    public SetNewsletterEspMappingFailedRequest(Integer newsletterId, Integer cityId) {
        super();
        this.cityId = cityId;
        this.newsletterId = newsletterId;
    }

    public Integer getNewsletterId() {
        return newsletterId;
    }

    public void setNewsletterId(Integer newsletterId) {
        this.newsletterId = newsletterId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

}
