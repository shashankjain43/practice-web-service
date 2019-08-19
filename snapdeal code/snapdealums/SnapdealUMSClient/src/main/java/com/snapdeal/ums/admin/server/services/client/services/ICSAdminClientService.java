
package com.snapdeal.ums.admin.server.services.client.services;

import com.snapdeal.ums.admin.server.services.ext.csadmin.CSexecutiveUserRequest;
import com.snapdeal.ums.admin.server.services.ext.csadmin.CSexecutiveUserResponse;

public interface ICSAdminClientService {

    
    public CSexecutiveUserResponse getAllCzentrixUser(CSexecutiveUserRequest request);
    
    public CSexecutiveUserResponse getAllZendeskUser(CSexecutiveUserRequest request);
    
}
