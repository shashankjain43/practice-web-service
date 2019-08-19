
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.user.UserSRO;

public class SendBdayCashBackEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3029821240424291000L;
	@Tag(3)
    private UserSRO user;
    @Tag(4)
    private int cashBackAmount;
    @Tag(5)
    private boolean newUser;

    public SendBdayCashBackEmailRequest() {
    }

    public SendBdayCashBackEmailRequest(UserSRO user, int cashBackAmount, boolean newUser) {
        super();
        this.user = user;
        this.cashBackAmount = cashBackAmount;
        this.newUser = newUser;
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

}
