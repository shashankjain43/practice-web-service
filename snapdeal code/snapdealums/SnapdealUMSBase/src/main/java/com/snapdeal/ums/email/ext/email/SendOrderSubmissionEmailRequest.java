
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendOrderSubmissionEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4678652557179908888L;
	@Tag(3)
    private Integer suborderId;
    @Tag(4)
    private String contextPath;
    @Tag(5)
    private String contentPath;

    public SendOrderSubmissionEmailRequest() {
    }
    
    public SendOrderSubmissionEmailRequest(Integer suborderId, String contextPath, String contentPath) {
        super();
        this.suborderId = suborderId;
        this.contextPath = contextPath;
        this.contentPath = contentPath;
    }

    public Integer getSuborderId() {
        return suborderId;
    }

    public void setSuborderId(Integer suborderId) {
        this.suborderId = suborderId;
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
