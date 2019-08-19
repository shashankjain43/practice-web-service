
package com.snapdeal.ums.email.ext.email;

import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.user.UserSRO;

public class SendReferralBenefitEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -47763766814984894L;
	
	@Tag(3)
    private UserSRO user;
    @Tag(4)
    private int sdCashValue;
    @Tag(5)
    private int friendsReferred;
    @Tag(6)
    private int noOfConversions;
    @Tag(7)
    private boolean newUser;
    @Tag(8)
    private String confirmationPath;
    @Tag(9)
    private List<String> referredUserEmails;
    @Tag(10)
    private String contextPath;
    @Tag(11)
    private String contentPath;

    public SendReferralBenefitEmailRequest() {
    }

    public SendReferralBenefitEmailRequest(UserSRO user, int sdCashValue, int friendsReferred, int noOfConversions, boolean newUser, String confirmationPath,
            List<String> referredUserEmails, String contextPath, String contentPath) {
        super();
        this.user = user;
        this.sdCashValue = sdCashValue;
        this.friendsReferred = friendsReferred;
        this.noOfConversions = noOfConversions;
        this.newUser = newUser;
        this.confirmationPath = confirmationPath;
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

    public int getSdCashValue() {
        return sdCashValue;
    }

    public void setSdCashValue(int sdCashValue) {
        this.sdCashValue = sdCashValue;
    }

    public int getFriendsReferred() {
        return friendsReferred;
    }

    public void setFriendsReferred(int friendsReferred) {
        this.friendsReferred = friendsReferred;
    }

    public int getNoOfConversions() {
        return noOfConversions;
    }

    public void setNoOfConversions(int noOfConversions) {
        this.noOfConversions = noOfConversions;
    }

    public boolean getNewUser() {
        return newUser;
    }

    public void setNewUser(boolean newUser) {
        this.newUser = newUser;
    }

    public String getConfirmationPath() {
        return confirmationPath;
    }

    public void setConfirmationPath(String confirmationPath) {
        this.confirmationPath = confirmationPath;
    }

    public List<String> getReferredUserEmails() {
        return referredUserEmails;
    }

    public void setReferredUserEmails(List<String> referredUserEmails) {
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
