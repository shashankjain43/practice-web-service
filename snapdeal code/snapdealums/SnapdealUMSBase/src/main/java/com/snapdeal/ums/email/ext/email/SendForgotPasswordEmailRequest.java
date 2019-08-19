
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.user.UserSRO;

public class SendForgotPasswordEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -4996283813149477242L;
	@Tag(3)
    private UserSRO user;
    @Tag(4)
    private String contextPath;
    @Tag(5)
    private String contentPath;
    @Tag(6)
    private String forgotPasswordLink;

    public SendForgotPasswordEmailRequest() {
    }
    
    public SendForgotPasswordEmailRequest(UserSRO user, String contextPath, String contentPath, String forgotPasswordLink) {
        super();
        this.user = user;
        this.contextPath = contextPath;
        this.contentPath = contentPath;
        this.forgotPasswordLink = forgotPasswordLink;
    }

    public UserSRO getUser() {
        return user;
    }

    public void setUser(UserSRO user) {
        this.user = user;
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

    public String getForgotPasswordLink() {
        return forgotPasswordLink;
    }

    public void setForgotPasswordLink(String forgotPasswordLink) {
        this.forgotPasswordLink = forgotPasswordLink;
    }

    @Override
    public String toString() {
        return "SendForgotPasswordEmailRequest [user=" + user.getEmail() + ", contextPath=" + contextPath + ", contentPath=" + contentPath + ", forgotPasswordLink=" + forgotPasswordLink
                + "]";
    }

}
