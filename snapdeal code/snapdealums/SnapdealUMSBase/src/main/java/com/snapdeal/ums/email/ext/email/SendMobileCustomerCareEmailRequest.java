
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendMobileCustomerCareEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3845304196247286931L;
	@Tag(3)
    private Integer orderId;
    @Tag(4)
    private Integer dealId;
    @Tag(5)
    private String contextPath;
    @Tag(6)
    private String contentPath;

    public SendMobileCustomerCareEmailRequest() {
    }

    public SendMobileCustomerCareEmailRequest(Integer orderId, Integer deal, String contextPath, String contentPath) {
        super();
        this.orderId = orderId;
        this.dealId = deal;
        this.contextPath = contextPath;
        this.contentPath = contentPath;
    }


    public Integer getDeal() {
        return dealId;
    }

    public void setDeal(Integer deal) {
        this.dealId = deal;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getContentPath() {
        return contentPath;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
