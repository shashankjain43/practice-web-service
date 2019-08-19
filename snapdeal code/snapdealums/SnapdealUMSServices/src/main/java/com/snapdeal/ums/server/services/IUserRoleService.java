package com.snapdeal.ums.server.services;

import java.util.List;

import com.snapdeal.ums.core.entity.Role;


public interface IUserRoleService {

    public Role getRoleByCode(String code);

    public List<Role> getRoleByUserEmail(String email);
    
    public Role addRole(String code,String description);
}
