
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class Send3rdPartyConfirmationEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6323531154758679530L;
	@Tag(3)
    private String email;
    @Tag(4)
    private String contextPath;
    @Tag(5)
    private String contentPath;
    @Tag(6)
    private String confirmationLink;
    @Tag(7)
    private String source;

    public Send3rdPartyConfirmationEmailRequest() {
    }

    public Send3rdPartyConfirmationEmailRequest(String email, String contextPath, String contentPath, String confirmationLink, String source) {
        super();
        this.email = email;
        this.contextPath = contextPath;
        this.contentPath = contentPath;
        this.confirmationLink = confirmationLink;
        this.source = source;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}
