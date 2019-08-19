
package com.snapdeal.ums.admin.server.services.ext.csadmin;

import java.util.ArrayList;
import java.util.List;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.user.UserSRO;

public class CSexecutiveUserResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -4875646958663967650L;
    @Tag(5)
    private List<UserSRO> getCSexecutiveUser = new ArrayList<UserSRO>();

    public CSexecutiveUserResponse() {
    }

    public CSexecutiveUserResponse(List<UserSRO> getCSexecutiveUser) {
        super();
        this.getCSexecutiveUser = getCSexecutiveUser;
    }

    public List<UserSRO>  getGetCSexecutiveUser() {
        return getCSexecutiveUser;
    }

    public void setGetCSexecutiveUser(List<UserSRO>  getCSexecutiveUser) {
        this.getCSexecutiveUser = getCSexecutiveUser;
    }

}
