
package com.snapdeal.ums.admin.server.services.server.services;

import com.snapdeal.ums.admin.server.services.ext.csadmin.CSexecutiveUserRequest;
import com.snapdeal.ums.admin.server.services.ext.csadmin.CSexecutiveUserResponse;
import com.snapdeal.ums.admin.server.services.ext.csadmin.CsZentrixUserRequest;
import com.snapdeal.ums.admin.server.services.ext.csadmin.CsZentrixUserResponse;
import com.snapdeal.ums.admin.server.services.ext.csadmin.ZendeskUserRequest;
import com.snapdeal.ums.admin.server.services.ext.csadmin.ZendeskUserResponse;

public interface ICSAdminService {

    public CSexecutiveUserResponse getCSexecutiveUser(CSexecutiveUserRequest request);
    
    public CsZentrixUserResponse getCsZentrixIdByUser(CsZentrixUserRequest request);

    public CsZentrixUserResponse persistCsZentrixId(CsZentrixUserRequest request);

    public CsZentrixUserResponse updateCsZentrixId(CsZentrixUserRequest request);

    public ZendeskUserResponse getZendeskUser(ZendeskUserRequest request);

    public ZendeskUserResponse persistZendeskUser(ZendeskUserRequest request);

    public ZendeskUserResponse updateZendeskUser(ZendeskUserRequest request);

    CSexecutiveUserResponse getAllCzentrixUser(CSexecutiveUserRequest request);

    CSexecutiveUserResponse getAllZendeskUser(CSexecutiveUserRequest request);

}
