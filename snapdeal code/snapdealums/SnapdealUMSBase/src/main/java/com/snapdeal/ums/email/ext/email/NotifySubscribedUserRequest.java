
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class NotifySubscribedUserRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8723094201057534691L;
	@Tag(3)
    private String email;
    @Tag(4)
    private Integer zoneId;
    @Tag(5)
    private String contextPath;
    @Tag(6)
    private String contentPath;
    @Tag(7)
    private String confirmationLink;

    public NotifySubscribedUserRequest() {
    }

    public NotifySubscribedUserRequest(String email, Integer zone, String contextPath, String contentPath, String confirmationLink) {
        super();
        this.email = email;
        this.zoneId = zone;
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

    public Integer getZone() {
        return zoneId;
    }

    public void setZone(Integer zone) {
        this.zoneId = zone;
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

}
