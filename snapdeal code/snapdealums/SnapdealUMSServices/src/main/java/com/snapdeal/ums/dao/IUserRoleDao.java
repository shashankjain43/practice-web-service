package com.snapdeal.ums.dao;

import com.snapdeal.ums.core.entity.Role;

public interface IUserRoleDao {

    public Role getRoleByCode(String code);

    public Role addRole(String code, String description);
}

