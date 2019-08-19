/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Sep 16, 2010
 *  @author singla
 */
package com.snapdeal.web.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.snapdeal.base.utils.StringUtils;
import com.snapdeal.ums.core.sro.user.UserRoleSRO;
import com.snapdeal.ums.core.sro.user.UserSRO;

public class SnapdealUser implements UserDetails, CredentialsContainer {

    /**
     * 
     */
    private static final long              serialVersionUID  = -1685770825144689799L;
    private UserSRO                           user;
    private Map<String, SnapdealAuthority> roleToAuthorities = new HashMap<String, SnapdealAuthority>();
    private List<GrantedAuthority>         authorities       = new ArrayList<GrantedAuthority>();
    private String                         trackingUID;
    private String                         trackingEmail;
    private List<Integer>                  subscribedCities  = new ArrayList<Integer>();
    private int                            cartQty;

    public SnapdealUser(UserSRO user) {
        this.user = user;
        for (UserRoleSRO role : user.getUserRoles()) {
            SnapdealAuthority authority = new SnapdealAuthority(role);
            roleToAuthorities.put(role.getRole().toLowerCase(), authority);
            authorities.add(authority);
        }
    }

    public List<Integer> getZonesForRole(String role) {
        SnapdealAuthority authority = roleToAuthorities.get(role.toLowerCase());
        if (authority == null) {
            return Collections.emptyList();
        } else {
            return authority.getApplicableZones();
        }
    }

    public boolean hasRole(String role) {
        return roleToAuthorities.containsKey(role.toLowerCase());
    }

    public boolean hasAnyRole(String roles) {
        return hasAnyRole(StringUtils.split(roles));
    }

    public boolean hasAnyRole(List<String> roles) {
        for (String role : roles) {
            if (hasRole(role.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAllRoles(List<String> roles) {
        for (String role : roles) {
            if (!hasRole(role)) {
                return false;
            }
        }
        return true;
    }

    public List<Integer> getAllApplicableZones() {
        Set<Integer> allZones = new HashSet<Integer>();
        for (GrantedAuthority authority : authorities) {
            allZones.addAll(((SnapdealAuthority) authority).getApplicableZones());
        }
        return new ArrayList<Integer>(allZones);
    }

    public List<Integer> getApplicableZones(List<String> roles) {
        Set<Integer> allZones = new HashSet<Integer>();
        for (String role : roles) {
            if (roleToAuthorities.containsKey(role.toLowerCase())) {
                allZones.addAll(roleToAuthorities.get(role.toLowerCase()).getApplicableZones());
            }
        }
        return new ArrayList<Integer>(allZones);
    }

    public UserSRO getUser() {
        return this.user;
    }

    @Override
    public void eraseCredentials() {
        this.user.setPassword(null);
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return this.user.isEnabled();
    }

    public void setTrackingUID(String trackingUID) {
        this.trackingUID = trackingUID;
    }

    public String getTrackingUID() {
        return trackingUID;
    }

    public List<Integer> getSubscribedCities() {
        return subscribedCities;
    }

    public void setSubscribedCities(List<Integer> subscribedCities) {
        this.subscribedCities = subscribedCities;
    }

    public int getCartQty() {
        return cartQty;
    }

    public void setCartQty(int cartQty) {
        this.cartQty = cartQty;
    }

    public void setTrackingEmail(String trackingEmail) {
        this.trackingEmail = trackingEmail;
    }

    public String getTrackingEmail() {
        return trackingEmail;
    }

    public String getName() {

        StringBuilder name = new StringBuilder("");
        if (StringUtils.isNotEmpty(this.user.getFirstName()))
            name.append(this.user.getFirstName());
        if (StringUtils.isNotEmpty(this.user.getMiddleName()))
            name.append(" " + this.user.getMiddleName());
        if (StringUtils.isNotEmpty(this.user.getLastName()))
            name.append(" " + this.user.getLastName());
        return name.toString();
    }

}
