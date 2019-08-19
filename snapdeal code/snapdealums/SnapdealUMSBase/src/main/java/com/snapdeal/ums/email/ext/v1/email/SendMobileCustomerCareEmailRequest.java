
package com.snapdeal.ums.email.ext.v1.email;

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
    private Long dealId;
    @Tag(5)
    private String contextPath;
    @Tag(6)
    private String contentPath;

    public SendMobileCustomerCareEmailRequest() {
    }

    public SendMobileCustomerCareEmailRequest(Integer orderId, Long dealId, String contextPath, String contentPath) {
        super();
        this.orderId = orderId;
        this.dealId = dealId;
        this.contextPath = contextPath;
        this.contentPath = contentPath;
    }

    /**
     * @return the dealId
     */
    public Long getDealId() {
        return dealId;
    }

    /**
     * @param dealId the dealId to set
     */
    public void setDealId(Long dealId) {
        this.dealId = dealId;
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
