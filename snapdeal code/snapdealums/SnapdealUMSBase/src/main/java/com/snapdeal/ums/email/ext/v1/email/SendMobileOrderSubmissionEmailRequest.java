
package com.snapdeal.ums.email.ext.v1.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendMobileOrderSubmissionEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2955050331807115783L;
	@Tag(3)
    private Integer orderId;
    @Tag(4)
    private Long dealId;
    @Tag(5)
    private String contextPath;
    @Tag(6)
    private String contentPath;

    public SendMobileOrderSubmissionEmailRequest() {
    }

    public SendMobileOrderSubmissionEmailRequest(Integer orderId, Long dealId, String contextPath, String contentPath) {
        super();
        this.orderId = orderId;
        this.dealId = dealId;
        this.contextPath = contextPath;
        this.contentPath = contentPath;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrder(Integer orderId) {
        this.orderId = orderId;
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

    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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

}
