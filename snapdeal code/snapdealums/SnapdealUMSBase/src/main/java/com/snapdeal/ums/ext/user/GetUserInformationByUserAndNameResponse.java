
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.user.UserInformationSRO;

public class GetUserInformationByUserAndNameResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -8173895395634087989L;
	@Tag(5)
    private UserInformationSRO getUserInformationByUserAndName;

    public GetUserInformationByUserAndNameResponse() {
    }

    public GetUserInformationByUserAndNameResponse(UserInformationSRO getUserInformationByUserAndName) {
        super();
        this.getUserInformationByUserAndName = getUserInformationByUserAndName;
    }

    public UserInformationSRO getGetUserInformationByUserAndName() {
        return getUserInformationByUserAndName;
    }

    public void setGetUserInformationByUserAndName(UserInformationSRO getUserInformationByUserAndName) {
        this.getUserInformationByUserAndName = getUserInformationByUserAndName;
    }

}
