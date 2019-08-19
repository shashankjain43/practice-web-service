
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class DebitSdCashRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4542934927319363786L;
	@Tag(3)
    private int userId;
    @Tag(4)
    private int sdCash;

    public DebitSdCashRequest() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSdCash() {
        return sdCash;
    }

    public void setSdCash(int sdCash) {
        this.sdCash = sdCash;
    }

    public DebitSdCashRequest(int userId, int sdCash) {
        this.userId = userId;
        this.sdCash = sdCash;
    }

}
