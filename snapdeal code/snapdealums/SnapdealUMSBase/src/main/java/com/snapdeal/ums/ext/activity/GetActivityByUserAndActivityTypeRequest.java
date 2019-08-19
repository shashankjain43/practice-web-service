
package com.snapdeal.ums.ext.activity;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.user.UserSRO;

public class GetActivityByUserAndActivityTypeRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1781169047050421576L;
	@Tag(3)
    private UserSRO user;
    @Tag(4)
    private Integer activity_type_id;

    public GetActivityByUserAndActivityTypeRequest() {
    }

    public UserSRO getUser() {
        return user;
    }

    public void setUser(UserSRO user) {
        this.user = user;
    }

    public Integer getActivity_type_id() {
        return activity_type_id;
    }

    public void setActivity_type_id(Integer activity_type_id) {
        this.activity_type_id = activity_type_id;
    }

    public GetActivityByUserAndActivityTypeRequest(UserSRO user, Integer activity_type_id) {
        this.user = user;
        this.activity_type_id = activity_type_id;
    }

}
