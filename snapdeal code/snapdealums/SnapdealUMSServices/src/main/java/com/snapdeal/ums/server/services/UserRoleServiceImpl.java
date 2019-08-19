package com.snapdeal.ums.server.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.ums.core.entity.Role;
import com.snapdeal.ums.core.entity.User;
import com.snapdeal.ums.core.entity.UserRole;
import com.snapdeal.ums.cache.RoleCache;
import com.snapdeal.ums.dao.IUserRoleDao;

@Transactional
@Service("umsRoleService")
public class UserRoleServiceImpl implements IUserRoleService {

    @Autowired
    private IUserRoleDao userRoleDao;

    @Autowired
    IUserServiceInternal         userService;

    @Override
    public Role getRoleByCode(String code) {
        Role r = CacheManager.getInstance().getCache(RoleCache.class).getRoleByCode(code);
        if (r == null) {
            r = userRoleDao.getRoleByCode(code);
        }
        return r;
    }

    @Override
    public List<Role> getRoleByUserEmail(String email) {
        User user = userService.getUserByEmail(email);
        Set<Role> roles = new HashSet<Role>();
        if (user != null) {
            Set<UserRole> userRoles = user.getUserRoles();
            for (UserRole uRole : userRoles) {
                roles.add(uRole.getRole());
            }
        }
        return new ArrayList<Role>(roles);
    }

    @Override
    public Role addRole(String code, String description) {
        return userRoleDao.addRole(code,description);
    }}
