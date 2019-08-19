
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.user.UserSRO;

public class SendSDCashEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1093316576060423918L;
	@Tag(3)
    private UserSRO user;
    @Tag(4)
    private String emailTemplateName;
    @Tag(5)
    private int cashBackAmount;
    @Tag(6)
    private String confirmationLink;
    @Tag(7)
    private boolean newUser;

    public SendSDCashEmailRequest() {
    }

    public SendSDCashEmailRequest(UserSRO user, String emailTemplateName, int cashBackAmount, String confirmationLink, boolean newUser) {
        super();
        this.user = user;
        this.emailTemplateName = emailTemplateName;
        this.cashBackAmount = cashBackAmount;
        this.confirmationLink = confirmationLink;
        this.newUser = newUser;
    }

    public UserSRO getUser() {
        return user;
    }

    public void setUser(UserSRO user) {
        this.user = user;
    }

    public String getEmailTemplateName() {
        return emailTemplateName;
    }

    public void setEmailTemplateName(String emailTemplateName) {
        this.emailTemplateName = emailTemplateName;
    }

    public int getCashBackAmount() {
        return cashBackAmount;
    }

    public void setCashBackAmount(int cashBackAmount) {
        this.cashBackAmount = cashBackAmount;
    }

    public String getConfirmationLink() {
        return confirmationLink;
    }

    public void setConfirmationLink(String confirmationLink) {
        this.confirmationLink = confirmationLink;
    }

    public boolean getNewUser() {
        return newUser;
    }

    public void setNewUser(boolean newUser) {
        this.newUser = newUser;
    }

}
