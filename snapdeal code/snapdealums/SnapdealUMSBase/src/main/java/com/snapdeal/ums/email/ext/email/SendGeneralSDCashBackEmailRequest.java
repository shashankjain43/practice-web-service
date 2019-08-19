
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.user.UserSRO;

public class SendGeneralSDCashBackEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7881720189645284640L;
	@Tag(3)
    private UserSRO user;
    @Tag(4)
    private Integer sdCashValue;
    @Tag(5)
    private boolean newUser;
    @Tag(6)
    private String conirmationLink;
    @Tag(7)
    private String contextPath;
    @Tag(8)
    private String contentPath;

    public SendGeneralSDCashBackEmailRequest() {
    }
    
    public SendGeneralSDCashBackEmailRequest(UserSRO user, Integer sdCashValue, boolean newUser, String conirmationLink, String contextPath, String contentPath) {
        super();
        this.user = user;
        this.sdCashValue = sdCashValue;
        this.newUser = newUser;
        this.conirmationLink = conirmationLink;
        this.contextPath = contextPath;
        this.contentPath = contentPath;
    }

    public UserSRO getUser() {
        return user;
    }

    public void setUser(UserSRO user) {
        this.user = user;
    }

    public Integer getSdCashValue() {
        return sdCashValue;
    }

    public void setSdCashValue(Integer sdCashValue) {
        this.sdCashValue = sdCashValue;
    }

    public boolean getNewUser() {
        return newUser;
    }

    public void setNewUser(boolean newUser) {
        this.newUser = newUser;
    }

    public String getConirmationLink() {
        return conirmationLink;
    }

    public void setConirmationLink(String conirmationLink) {
        this.conirmationLink = conirmationLink;
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
