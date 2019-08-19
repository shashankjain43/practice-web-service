 /*
*  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
*  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*  
*  @version     1.0, 23-Nov-2012
*  @author naveen
*/
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendAlternateRetryEmailRequest 
    extends ServiceRequest
{
    /**
     * 
     */
    private static final long serialVersionUID = -1195960681070008102L;
    @Tag(3)
    Integer orderId;
    @Tag(4)
    Integer alternateSuborderId;
    @Tag(5)
    String buyPageUrl;
    
    public SendAlternateRetryEmailRequest(){
        
    }
    public SendAlternateRetryEmailRequest(Integer orderId, Integer alternateSuborderId, String buyPageUrl) {
        super();
        this.orderId = orderId;
        this.alternateSuborderId = alternateSuborderId;
        this.buyPageUrl = buyPageUrl;
    }
 
    public Integer getOrderId() {
        return orderId;
    }
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    public Integer getAlternateSuborderId() {
        return alternateSuborderId;
    }
    public void setAlternateSuborderId(Integer alternateSuborderId) {
        this.alternateSuborderId = alternateSuborderId;
    }
    public String getBuyPageUrl() {
        return buyPageUrl;
    }
    public void setBuyPageUrl(String buyPageUrl) {
        this.buyPageUrl = buyPageUrl;
    }
}

 