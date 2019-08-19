
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class DebitSdCashResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2203762062333528696L;
	@Tag(5)
    private boolean debitSdCash;

    public DebitSdCashResponse() {
    }

    public DebitSdCashResponse(boolean debitSdCash) {
        super();
        this.debitSdCash = debitSdCash;
    }

    public boolean getDebitSdCash() {
        return debitSdCash;
    }

    public void setDebitSdCash(boolean debitSdCash) {
        this.debitSdCash = debitSdCash;
    }

}
