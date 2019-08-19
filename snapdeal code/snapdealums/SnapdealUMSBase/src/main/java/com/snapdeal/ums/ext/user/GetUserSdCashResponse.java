
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class GetUserSdCashResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6142360361450575902L;
	@Tag(5)
    private int getUserSdCash;

    public GetUserSdCashResponse() {
    }

    public GetUserSdCashResponse(int getUserSdCash) {
        super();
        this.getUserSdCash = getUserSdCash;
    }

    public int getGetUserSdCash() {
        return getUserSdCash;
    }

    public void setGetUserSdCash(int getUserSdCash) {
        this.getUserSdCash = getUserSdCash;
    }

}
