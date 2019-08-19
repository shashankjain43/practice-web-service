package com.snapdeal.ims.service.impl;

import static org.mockito.Matchers.any;

import com.google.common.base.Optional;
import com.snapdeal.ims.cache.CacheManager;
import com.snapdeal.ims.cache.ConfigCache;
import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.dao.IUserLockDao;
import com.snapdeal.ims.dbmapper.entity.UserLockDetails;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.service.provider.UmsServiceProvider;

import org.junit.Before;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginUserServiceImplTest {

   @InjectMocks
   LoginUserServiceImpl loginUserService;

   @Mock
   FCUMSServiceImpl fcUmsService;

   @Mock
   SnapdealUMSServiceImpl snapdealUmsService;

   @Mock
   UmsServiceProvider serviceProvider;

   @Mock
   private IUserLockDao userLockDao;

   @Before
   public void setup() {
      MockitoAnnotations.initMocks(this);
      UserLockDetails userLockDetails = new UserLockDetails();
      userLockDetails.setStatus(ServiceCommonConstants.USER_LOCKED);
      userLockDetails.setExpiryTime(new Date(10000));
      Mockito.when(userLockDao.getLockUserEntry(any(String.class))).thenReturn(
               Optional.of(userLockDetails));

      userLockDetails.setCreatedTime(new Date(System.currentTimeMillis()));
      Mockito.when(userLockDao.getLockUserEntry(any(String.class))).thenReturn(
               Optional.of(userLockDetails));
      
      
      CacheManager cacheManager = CacheManager.getInstance();
      final ConfigCache configCache = new ConfigCache();
      Map<String, String> map1 = new HashMap<String, String>();
      map1.put("user.locked.for.time", "90000");
      map1.put("user.locking.window.frame", "6000");
      map1.put("maximum.number.of.login.attempts", "3");
      configCache.put("global", map1);
      cacheManager.setCache(configCache);

   }

   @Test
   public void isUserLockedTest() {
      loginUserService.isUserLocked("asdads");
   }

   @Test(expected = IMSServiceException.class)
   public void isUserLockedTestException() {
      UserLockDetails userLockDetails = new UserLockDetails();
      userLockDetails.setStatus(ServiceCommonConstants.USER_LOCKED);
      userLockDetails.setExpiryTime(new Date(System.currentTimeMillis() - 100));
      Mockito.when(userLockDao.getLockUserEntry(any(String.class))).thenReturn(
               Optional.of(userLockDetails));
      loginUserService.isUserLocked("adsasd");
   }

   @Test
   public void updateUserLockInfo_testing() {
      UserLockDetails userLockDetails = new UserLockDetails();
      userLockDetails.setCreatedTime(new Date(System.currentTimeMillis() - 6000));
      Mockito.when(userLockDao.getLockUserEntry(any(String.class))).thenReturn(
               Optional.of(userLockDetails));
      loginUserService.updateUserLockInfo("");
   }
   
   @Test
   public void updateUserLockInfo_Locked() {
      //Mockito.when(userLockDetails.getLoginAttempts()).thenReturn(new Integer(2));
      loginUserService.updateUserLockInfo("");
   }


   @Test
   public void deleteUserLockInfo() {
      loginUserService.deleteUserLockInfo("");
   }

}
