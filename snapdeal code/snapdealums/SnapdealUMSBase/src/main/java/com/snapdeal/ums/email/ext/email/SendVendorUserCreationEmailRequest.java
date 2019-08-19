
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.user.UserSRO;

public class SendVendorUserCreationEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4006447460303317973L;
	@Tag(3)
    private UserSRO user;
    @Tag(4)
    private String contextPath;
    @Tag(5)
    private String contentPath;

    public SendVendorUserCreationEmailRequest() {
    }

    public SendVendorUserCreationEmailRequest(UserSRO user, String contextPath, String contentPath) {
        super();
        this.user = user;
        this.contextPath = contextPath;
        this.contentPath = contentPath;
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

}
