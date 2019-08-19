
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class GetConfirmationLinkResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6768483130405256737L;
	@Tag(5)
    private String getConfirmationLink;

    public GetConfirmationLinkResponse() {
    }

    public GetConfirmationLinkResponse(String getConfirmationLink) {
        super();
        this.getConfirmationLink = getConfirmationLink;
    }

    public String getGetConfirmationLink() {
        return getConfirmationLink;
    }

    public void setConfirmationLink(String getConfirmationLink) {
        this.getConfirmationLink = getConfirmationLink;
    }

}
