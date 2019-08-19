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

public class SendPrebookRetryEmailRequest    extends ServiceRequest
{
    /**
     * 
     */
    private static final long serialVersionUID = -5880927243388156893L;
    @Tag(3)
    Integer orderId;
    @Tag(4)
    Integer suborderId;
    @Tag(5)
    String buyPageUrl;
    
    public SendPrebookRetryEmailRequest(){
        
    }
    public SendPrebookRetryEmailRequest(Integer orderId, Integer suborderId, String buyPageUrl) {
        super();
        this.orderId = orderId;
        this.suborderId = suborderId;
        this.buyPageUrl = buyPageUrl;
    }
    public Integer getOrderId() {
        return orderId;
    }
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    public Integer getSuborderId() {
        return suborderId;
    }
    public void setSuborderId(Integer suborderId) {
        this.suborderId = suborderId;
    }
    public String getBuyPageUrl() {
        return buyPageUrl;
    }
    public void setBuyPageUrl(String buyPageUrl) {
        this.buyPageUrl = buyPageUrl;
    }
    
    
    
}

 