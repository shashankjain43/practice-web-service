
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.user.UserSRO;

public class GetUserInformationByUserAndNameRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7357598338677573929L;
	@Tag(3)
    private UserSRO user;
    @Tag(4)
    private String name;

    public GetUserInformationByUserAndNameRequest() {
    }

    public UserSRO getUser() {
        return user;
    }

    public void setUser(UserSRO user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GetUserInformationByUserAndNameRequest(UserSRO user, String name) {
        this.user = user;
        this.name = name;
    }

}
