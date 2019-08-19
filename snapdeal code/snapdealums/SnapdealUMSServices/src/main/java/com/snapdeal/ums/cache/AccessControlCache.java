/*
 *  Copyright 2011 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Apr 21, 2011
 *  @author laptop
 */
package com.snapdeal.ums.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.snapdeal.base.annotations.Cache;
import com.snapdeal.ums.core.entity.AccessControl;

@Cache(name = "accessControlCache")
public class AccessControlCache {
    private Map<String, String> accessControlMap = new HashMap<String, String>();
    private List<AccessControl> accessControlList = new ArrayList<AccessControl>();

    public void addAccessControls(AccessControl accessControl) {
        accessControlMap.put(accessControl.getPattern(), accessControl
                .getRoles().replaceAll(" ", ""));
        accessControlList.add(accessControl);
    }

    public String getRoles(String pattern) {
        return accessControlMap.get(pattern);
    }

    public List<AccessControl> getAccessControls() {
        return accessControlList;
    }

}
