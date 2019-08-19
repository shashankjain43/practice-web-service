package com.snapdeal.ums.admin.server.services.server.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.ums.core.entity.CsZentrix;
import com.snapdeal.ums.core.entity.User;
import com.snapdeal.ums.core.entity.ZendeskUser;
import com.snapdeal.ums.admin.dao.ICSAdminDao;
import com.snapdeal.ums.admin.server.services.server.services.ICSAdminServiceInternal;

@Service("CSAdminServiceInternal")
public class CSAdminServiceInternalImpl implements ICSAdminServiceInternal {

    @Autowired
    private ICSAdminDao csUMSDao;

    @Override
    public List<User> getCSexecutiveUser() {
        return csUMSDao.getCSexecutiveUser();
    }

    @Override
    public List<User> getAllCzentrixUser(){
        return csUMSDao.getAllCZentrixUser();
    }
    
    @Override
    public List<User> getAllZendeskUser(){
        return csUMSDao.getAllZendeskUser();
    }
    @Override
    public ZendeskUser getZendeskUser(int userId) {
        return csUMSDao.getZendeskUser(userId);
    }

    @Override
    public CsZentrix getCsZentrixIdByUser(int userId) {
        return csUMSDao.getCsZentrixIdByUser(userId);
    }

    @Override
    public void persistCsZentrixId(CsZentrix cs) {
        csUMSDao.persistCsZentrixId(cs);
    }

    @Override
    public void updateCsZentrixId(CsZentrix cs) {
        csUMSDao.updateCsZentrixId(cs);
    }

    @Override
    public void persistZendeskUser(ZendeskUser zendeskUser) {
        csUMSDao.persistZendeskUser(zendeskUser);

    }

    @Override
    public void updateZendeskUser(ZendeskUser zendeskUser) {
        csUMSDao.updateZendeskUser(zendeskUser);
    }
}
