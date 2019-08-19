
package com.snapdeal.ums.ext.user;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.user.UserInformationSRO;

public class GetUserInformationsByUserResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1529598042261560321L;
	@Tag(5)
    private List<UserInformationSRO> getUserInformationsByUser = new ArrayList<UserInformationSRO>();

    public GetUserInformationsByUserResponse() {
    }

    public GetUserInformationsByUserResponse(List<UserInformationSRO> getUserInformationsByUser) {
        super();
        this.getUserInformationsByUser = getUserInformationsByUser;
    }

    public List<UserInformationSRO> getGetUserInformationsByUser() {
        return getUserInformationsByUser;
    }

    public void setGetUserInformationsByUser(List<UserInformationSRO> getUserInformationsByUser) {
        this.getUserInformationsByUser = getUserInformationsByUser;
    }

}
