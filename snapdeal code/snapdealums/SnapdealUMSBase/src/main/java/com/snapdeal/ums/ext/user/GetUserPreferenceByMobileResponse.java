
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.user.UserPreferenceSRO;

public class GetUserPreferenceByMobileResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2834428929583155800L;
	@Tag(5)
    private UserPreferenceSRO getUserPreferenceByMobile;

    public GetUserPreferenceByMobileResponse() {
    }

    public GetUserPreferenceByMobileResponse(UserPreferenceSRO getUserPreferenceByMobile) {
        super();
        this.getUserPreferenceByMobile = getUserPreferenceByMobile;
    }

    public UserPreferenceSRO getGetUserPreferenceByMobile() {
        return getUserPreferenceByMobile;
    }

    public void setGetUserPreferenceByMobile(UserPreferenceSRO getUserPreferenceByMobile) {
        this.getUserPreferenceByMobile = getUserPreferenceByMobile;
    }

}
