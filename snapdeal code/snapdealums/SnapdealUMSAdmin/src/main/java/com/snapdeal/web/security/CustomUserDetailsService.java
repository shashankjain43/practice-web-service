/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Sep 16, 2010
 *  @author singla
 */
package com.snapdeal.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.snapdeal.ums.core.sro.user.UserSRO;
import com.snapdeal.ums.ext.user.GetUserByEmailRequest;
import com.snapdeal.ums.server.services.IUserService;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserService        userService;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        UserSRO user = userService.getUserByEmail(new GetUserByEmailRequest(username)).getGetUserByEmail();
        if (user != null) {
            SnapdealUser snapdealUser = new SnapdealUser(user);
            snapdealUser.setTrackingEmail(user.getEmail());
            return snapdealUser;
        } else {
            throw new UsernameNotFoundException("Invalid email", username);
        }
    }
}
