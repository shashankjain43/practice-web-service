
package com.snapdeal.ums.services.ext.smsservice;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.sms.sro.OrderComplaintSRO;

public class SendAcknowladgeSmsRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 2045613026715855560L;
    @Tag(3)
    private OrderComplaintSRO orderComplaint;
    @Tag(4)
    private String templateName;

    public SendAcknowladgeSmsRequest() {
    }

    public OrderComplaintSRO getOrderComplaint() {
        return orderComplaint;
    }

    public void setOrderComplaint(OrderComplaintSRO orderComplaint) {
        this.orderComplaint = orderComplaint;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

}
