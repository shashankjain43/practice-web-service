
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendCodOrderSubmissionEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6648641019870797380L;
	@Tag(3)
    private Integer suborderId;
    @Tag(4)
    private String contextPath;
    @Tag(5)
    private String contentPath;

    public SendCodOrderSubmissionEmailRequest() {
    }

    public SendCodOrderSubmissionEmailRequest(Integer suborderId, String contextPath, String contentPath) {
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

    public void setContextPath(String http) {
        this.contextPath = http;
    }

    public String getContentPath() {
        return contentPath;
    }

    public void setContentPath(String resources) {
        this.contentPath = resources;
    }

}
