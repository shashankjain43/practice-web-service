
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendInviteEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3030636390438642748L;
	@Tag(3)
    private String refererEmail;
    @Tag(4)
    private String contextPath;
    @Tag(5)
    private String contentPath;
    @Tag(6)
    private String userName;
    @Tag(7)
    private String to;
    @Tag(8)
    private String from;
    @Tag(9)
    private String trackingUID;
    
    

    public SendInviteEmailRequest(String refererEmail, String contextPath, String contentPath, String userName, String to, String from, String trackingUID) {
        this.refererEmail = refererEmail;
        this.contextPath = contextPath;
        this.contentPath = contentPath;
        this.userName = userName;
        this.to = to;
        this.from = from;
        this.trackingUID = trackingUID;
    }

    public SendInviteEmailRequest() {
    }

    public String getRefererEmail() {
        return refererEmail;
    }

    public void setRefererEmail(String refererEmail) {
        this.refererEmail = refererEmail;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTrackingUID() {
        return trackingUID;
    }

    public void setTrackingUID(String trackingUID) {
        this.trackingUID = trackingUID;
    }

}
