
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.user.UserSRO;

public class SendSdCashBackRewardOnPurchaseEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8583589481185601722L;
	@Tag(3)
    private Integer orderId;
    @Tag(4)
    private UserSRO user;
    @Tag(5)
    private int sdCash;
    @Tag(6)
    private Long purchase;
    @Tag(7)
    private boolean newUser;
    @Tag(8)
    private String confirmationLink;
    @Tag(9)
    private String contextPath;
    @Tag(10)
    private String contentPath;

    
    public SendSdCashBackRewardOnPurchaseEmailRequest(Integer orderId, UserSRO user, int sdCash, Long purchase, boolean newUser, String confirmationLink, String contextPath,
            String contentPath) {
        super();
        this.orderId = orderId;
        this.user = user;
        this.sdCash = sdCash;
        this.purchase = purchase;
        this.newUser = newUser;
        this.confirmationLink = confirmationLink;
        this.contextPath = contextPath;
        this.contentPath = contentPath;
    }

    public SendSdCashBackRewardOnPurchaseEmailRequest() {
    }


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public UserSRO getUser() {
        return user;
    }

    public void setUser(UserSRO user) {
        this.user = user;
    }

    public int getSdCash() {
        return sdCash;
    }

    public void setSdCash(int sdCash) {
        this.sdCash = sdCash;
    }

    public Long getPurchase() {
        return purchase;
    }

    public void setPurchase(Long purchase) {
        this.purchase = purchase;
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
