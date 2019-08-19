/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 21-Nov-2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.services.user;

import mockit.Injectable;
import mockit.NonStrictExpectations;
import mockit.Tested;

import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.snapdeal.ums.dao.users.IUsersDao;
import com.snapdeal.ums.server.services.impl.UserServiceInternalImpl;

public class TestUserServiceInternalImpl {

    @Tested
    private UserServiceInternalImpl umsInternalUserService;

    @Injectable
    private IUsersDao               userDao;

    @BeforeMethod
    public void setUp() {

    }

    @Test
    public void isMobileExistTest() {

        new NonStrictExpectations() {
            {
                userDao.isMobileExist(anyString);
                result = true;
                result = false;
            }
        };

        AssertJUnit.assertTrue(umsInternalUserService.isMobileExist("9999999999"));
        AssertJUnit.assertFalse(umsInternalUserService.isMobileExist("9999999999"));
    }
}
