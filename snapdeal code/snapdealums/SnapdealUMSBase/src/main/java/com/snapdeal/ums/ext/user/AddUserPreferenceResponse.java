
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.user.UserPreferenceSRO;

public class AddUserPreferenceResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8691525769047674528L;
	@Tag(5)
    private UserPreferenceSRO addUserPreference;

    public AddUserPreferenceResponse() {
    }

    public AddUserPreferenceResponse(UserPreferenceSRO addUserPreference) {
        super();
        this.addUserPreference = addUserPreference;
    }

    public UserPreferenceSRO getAddUserPreference() {
        return addUserPreference;
    }

    public void setAddUserPreference(UserPreferenceSRO addUserPreference) {
        this.addUserPreference = addUserPreference;
    }

}
