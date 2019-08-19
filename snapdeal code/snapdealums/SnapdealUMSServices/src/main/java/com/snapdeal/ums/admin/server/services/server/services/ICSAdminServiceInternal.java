
package com.snapdeal.ums.admin.server.services.server.services;

import java.util.List;
import com.snapdeal.ums.core.entity.CsZentrix;
import com.snapdeal.ums.core.entity.User;
import com.snapdeal.ums.core.entity.ZendeskUser;

public interface ICSAdminServiceInternal {

    public List<User> getCSexecutiveUser();

    public ZendeskUser getZendeskUser(int userId);

    public CsZentrix getCsZentrixIdByUser(int userId);

    public void persistCsZentrixId(CsZentrix cs);

    public void updateCsZentrixId(CsZentrix cs);

    public void persistZendeskUser(ZendeskUser zendeskUser);

    public void updateZendeskUser(ZendeskUser zendeskUser);

    List<User> getAllCzentrixUser();

    List<User> getAllZendeskUser();
    
}
