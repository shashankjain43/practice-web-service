
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.user.ZendeskUserSRO;

public class GetZendeskUserResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4976281176701180562L;
	@Tag(5)
    private ZendeskUserSRO getZendeskUser;

    public GetZendeskUserResponse() {
    }

    public GetZendeskUserResponse(ZendeskUserSRO getZendeskUser) {
        super();
        this.getZendeskUser = getZendeskUser;
    }

    public ZendeskUserSRO getGetZendeskUser() {
        return getZendeskUser;
    }

    public void setGetZendeskUser(ZendeskUserSRO getZendeskUser) {
        this.getZendeskUser = getZendeskUser;
    }

}
