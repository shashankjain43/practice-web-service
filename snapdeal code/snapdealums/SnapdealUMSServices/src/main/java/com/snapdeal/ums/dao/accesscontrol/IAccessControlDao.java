/*
 *  Copyright 2011 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Apr 20, 2011
 *  @author laptop
 */
package com.snapdeal.ums.dao.accesscontrol;

import java.util.List;

import com.snapdeal.ums.core.entity.AccessControl;

public interface IAccessControlDao {
    public List<AccessControl> getAllAccessControls();
    public AccessControl addControl(String pattern, String roles);
    public AccessControl getAccessControlByPattern(String pattern);
}
