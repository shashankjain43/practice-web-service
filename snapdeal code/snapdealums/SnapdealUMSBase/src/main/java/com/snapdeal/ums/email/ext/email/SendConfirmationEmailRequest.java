
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendConfirmationEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2637668875251718260L;
	@Tag(3)
    private String email;
    @Tag(4)
    private String contextPath;
    @Tag(5)
    private String contentPath;
    @Tag(6)
    private String confirmationLink;

    public SendConfirmationEmailRequest() {
    }

    public SendConfirmationEmailRequest(String email, String contextPath, String contentPath, String confirmationLink) {
        super();
        this.email = email;
        this.contextPath = contextPath;
        this.contentPath = contentPath;
        this.confirmationLink = confirmationLink;
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

    public String getConfirmationLink() {
        return confirmationLink;
    }

    public void setConfirmationLink(String confirmationLink) {
        this.confirmationLink = confirmationLink;
    }

    @Override
    public String toString() {
        return "SendConfirmationEmailRequest [email=" + email + ", contextPath=" + contextPath + ", contentPath=" + contentPath + ", confirmationLink=" + confirmationLink + "]";
    }

}
