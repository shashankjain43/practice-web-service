/*
 *  Copyright 2011 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Apr 20, 2011
 *  @author laptop
 */
package com.snapdeal.ums.services.accesscontrol;

import java.util.List;

import com.snapdeal.ums.core.entity.AccessControl;

public interface IAccessControlService {
    public String getUserRoleByPattern(String URI);

    public AccessControl addControl(String pattern, String roles);

    public List<AccessControl> getAllAccessControls();
}
