/*
 *  Copyright 2011 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Apr 20, 2011
 *  @author laptop
 */
package com.snapdeal.ums.services.accesscontrol;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.ums.core.entity.AccessControl;
import com.snapdeal.ums.cache.AccessControlCache;
import com.snapdeal.ums.dao.accesscontrol.IAccessControlDao;


@Service("accessControlService")
public class AccessControlServiceImpl implements IAccessControlService {

    @Autowired
    private IAccessControlDao accessControlDao;

    @Override
    public String getUserRoleByPattern(String URI) {
        return CacheManager.getInstance().getCache(AccessControlCache.class).getRoles(URI);
    }

    @Transactional
    @Override
    public AccessControl addControl(String pattern, String roles) {
        return accessControlDao.addControl(pattern, roles);
    }
    
    @Override
    public List<AccessControl> getAllAccessControls() {
        return CacheManager.getInstance().getCache(AccessControlCache.class).getAccessControls();
    }
}
