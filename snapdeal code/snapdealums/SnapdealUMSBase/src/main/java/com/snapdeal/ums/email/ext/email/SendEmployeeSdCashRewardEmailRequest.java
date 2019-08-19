
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.user.UserSRO;

public class SendEmployeeSdCashRewardEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2986536833971063929L;
	@Tag(3)
    private UserSRO user;
    @Tag(4)
    private int cashBackAmount;
    @Tag(5)
    private boolean newUser;
    @Tag(6)
    private String confirmationLink;
    @Tag(7)
    private String contextPath;
    @Tag(8)
    private String contentPath;

    public SendEmployeeSdCashRewardEmailRequest(UserSRO user, int cashBackAmount, boolean newUser, String confirmationLink, String contextPath, String contentPath) {
        super();
        this.user = user;
        this.cashBackAmount = cashBackAmount;
        this.newUser = newUser;
        this.confirmationLink = confirmationLink;
        this.contextPath = contextPath;
        this.contentPath = contentPath;
    }

    public SendEmployeeSdCashRewardEmailRequest() {
    }

    public UserSRO getUser() {
        return user;
    }

    public void setUser(UserSRO user) {
        this.user = user;
    }

    public int getCashBackAmount() {
        return cashBackAmount;
    }

    public void setCashBackAmount(int cashBackAmount) {
        this.cashBackAmount = cashBackAmount;
    }

    public boolean getNewUser() {
        return newUser;
    }

    public void setNewUser(boolean newUser) {
        this.newUser = newUser;
    }

    public String getConfirmationLink() {
        return confirmationLink;
    }

    public void setConfirmationLink(String confirmationLink) {
        this.confirmationLink = confirmationLink;
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
