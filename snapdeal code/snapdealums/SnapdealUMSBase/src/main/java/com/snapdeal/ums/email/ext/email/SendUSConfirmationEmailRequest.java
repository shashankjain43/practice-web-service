
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendUSConfirmationEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -709703683371894579L;
	@Tag(3)
    private String email;
    @Tag(4)
    private String contextPath;
    @Tag(5)
    private String contentPath;

    public SendUSConfirmationEmailRequest() {
    }

    public SendUSConfirmationEmailRequest(String email, String contextPath, String contentPath) {
        super();
        this.email = email;
        this.contextPath = contextPath;
        this.contentPath = contentPath;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
