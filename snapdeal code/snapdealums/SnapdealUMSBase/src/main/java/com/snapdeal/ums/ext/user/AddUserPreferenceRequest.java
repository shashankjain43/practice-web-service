
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.user.UserPreferenceSRO;

public class AddUserPreferenceRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2353042465693841343L;
	@Tag(3)
    private UserPreferenceSRO userPreference;
	
    public AddUserPreferenceRequest() {
    }

    public UserPreferenceSRO getUserPreference() {
        return userPreference;
    }

    public void setUserPreference(UserPreferenceSRO userPreference) {
        this.userPreference = userPreference;
    }

    public AddUserPreferenceRequest(UserPreferenceSRO userPreference) {
        this.userPreference = userPreference;
    }

}
