package com.snapdeal.ums.cache;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import com.snapdeal.base.annotations.Cache;
import com.snapdeal.ums.core.entity.Role;

@Cache(name = "roleCache")
public class RoleCache {
    private Map<String, Role> code2RoleMap = new LinkedHashMap<String, Role>();

    public void addRole(Role r) {
        code2RoleMap.put(r.getCode(), r);
    }

    public Role getRoleByCode(String code) {
        return code2RoleMap.get(code);
    }

    public Collection<Role> getRoles(){
        return code2RoleMap.values();
    }
}