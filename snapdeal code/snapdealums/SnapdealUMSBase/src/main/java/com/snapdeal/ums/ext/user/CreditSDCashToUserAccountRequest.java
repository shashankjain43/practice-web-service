
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class CreditSDCashToUserAccountRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6774583093985518779L;
	@Tag(3)
    private String email;
    @Tag(4)
    private int sdCash;
    @Tag(5)
    private String activityName;

    public CreditSDCashToUserAccountRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSdCash() {
        return sdCash;
    }

    public void setSdCash(int sdCash) {
        this.sdCash = sdCash;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public CreditSDCashToUserAccountRequest(String email, int sdCash, String activityName) {
        this.email = email;
        this.sdCash = sdCash;
        this.activityName = activityName;
    }

}
