
package com.snapdeal.ums.email.ext.email;

import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.user.UserSRO;

public class SendNewSubscriberReferralOneTimeEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7379863194125331987L;
	@Tag(3)
    private UserSRO user;
    @Tag(4)
    private String confirmationLink;
    @Tag(5)
    private  List<String>  referredUserEmails;
    @Tag(6)
    private String contextPath;
    @Tag(7)
    private String contentPath;

    public SendNewSubscriberReferralOneTimeEmailRequest() {
    }

    public SendNewSubscriberReferralOneTimeEmailRequest(UserSRO user, String confirmationLink, List<String> referredUserEmails, String contextPath, String contentPath) {
        super();
        this.user = user;
        this.confirmationLink = confirmationLink;
        this.referredUserEmails = referredUserEmails;
        this.contextPath = contextPath;
        this.contentPath = contentPath;
    }

    public UserSRO getUser() {
        return user;
    }

    public void setUser(UserSRO user) {
        this.user = user;
    }

    public String getConfirmationLink() {
        return confirmationLink;
    }

    public void setConfirmationLink(String confirmationLink) {
        this.confirmationLink = confirmationLink;
    }

    public  List<String>  getReferredUserEmails() {
        return referredUserEmails;
    }

    public void setReferredUserEmails( List<String>  referredUserEmails) {
        this.referredUserEmails = referredUserEmails;
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
