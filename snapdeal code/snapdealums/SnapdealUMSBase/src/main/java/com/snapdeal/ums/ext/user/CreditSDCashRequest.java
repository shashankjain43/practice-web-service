
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class CreditSDCashRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 465911015844619411L;
	@Tag(3)
    private int userId;
    @Tag(4)
    private int sdCash;
    @Tag(5)
    private boolean updateSDCashEarned;

    public CreditSDCashRequest() {
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

    public boolean getUpdateSDCashEarned() {
        return updateSDCashEarned;
    }

    public void setUpdateSDCashEarned(boolean updateSDCashEarned) {
        this.updateSDCashEarned = updateSDCashEarned;
    }

    public CreditSDCashRequest(int userId, int sdCash, boolean updateSDCashEarned) {
        this.userId = userId;
        this.sdCash = sdCash;
        this.updateSDCashEarned = updateSDCashEarned;
    }

}
