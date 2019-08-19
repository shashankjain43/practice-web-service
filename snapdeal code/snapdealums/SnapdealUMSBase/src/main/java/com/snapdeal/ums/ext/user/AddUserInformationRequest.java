
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.user.UserInformationSRO;

public class AddUserInformationRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -4328188523722810263L;
	@Tag(3)
    private UserInformationSRO information;

    public AddUserInformationRequest() {
    }

    public UserInformationSRO getInformation() {
        return information;
    }

    public void setInformation(UserInformationSRO information) {
        this.information = information;
    }

    public AddUserInformationRequest(UserInformationSRO information) {
        this.information = information;
    }

}
