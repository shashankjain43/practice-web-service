package com.snapdeal.ims.cache.aerospike.impl;

import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.snapdeal.ims.cache.service.IIMSCacheServiceProvider;
import com.snapdeal.ims.cache.service.IUserCacheService;
import com.snapdeal.ims.dbmapper.entity.User;
import com.snapdeal.ims.enums.Gender;
import com.snapdeal.ims.enums.Language;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.UserStatus;
import com.snapdeal.ims.main.ExtendedSpringJUnit4ClassRunner;
import com.snapdeal.ims.token.service.impl.BaseTest;

@ContextConfiguration(locations = { "classpath:/spring/application-context.xml" })
@RunWith(ExtendedSpringJUnit4ClassRunner.class)
public class UserCacheServiceImplTest extends BaseTest {

   @Autowired
   IUserCacheService cacheService;

   @Autowired
   IIMSCacheServiceProvider imsCacheService;

   Random r = new Random();

   @Before
   public void setup() {
      initConfig();
      imsCacheService.connectToCacheCluster(null);
   }

   @Test
   public void getUserById() {
      User user = setUser(getNumber());

      cacheService.putUser(user);

      User fetchedUser = cacheService.getUserById(user.getUserId());

      Assert.assertNotNull(fetchedUser);
      Assert.assertEquals(user, fetchedUser);
   }

   @Test
   public void getUserByEmail() {
      User user = setUser(getNumber());

      cacheService.putUser(user);

      User fetchedUser = cacheService.getUserByEmail(user.getEmailId());

      Assert.assertNotNull(fetchedUser);
      Assert.assertEquals(user, fetchedUser);
   }

   @Test
   public void getUserByFcId() {
      User user = setUser(getNumber());

      cacheService.putUser(user);

      User fetchedUser = cacheService.getUserByFcId(user.getFcUserId());

      Assert.assertNotNull(fetchedUser);
      Assert.assertEquals(user, fetchedUser);
   }

   @Test
   public void getUserByMobile() {
      User user = setUser(getNumber());

      cacheService.putUser(user);

      User fetchedUser = cacheService.getUserByMobile(user.getMobileNumber());

      Assert.assertNotNull(fetchedUser);
      Assert.assertEquals(user, fetchedUser);
   }

   @Test
   public void getUserBySdFcId() {
      User user = setUser(getNumber());

      cacheService.putUser(user);

      User fetchedUser = cacheService.getUserBySdFcId(user.getSdFcUserId());

      Assert.assertNotNull(fetchedUser);
      Assert.assertEquals(user, fetchedUser);
   }

   @Test
   public void getUserBySdId() {
      User user = setUser(getNumber());

      cacheService.putUser(user);

      User fetchedUser = cacheService.getUserBySdId(user.getSdUserId());

      Assert.assertNotNull(fetchedUser);
      Assert.assertEquals(user, fetchedUser);
   }

   @Test
   public void invalidateUserById() {
      User user = setUser(getNumber());

      cacheService.putUser(user);

      User fetchedUser = cacheService.getUserById(user.getUserId());

      Assert.assertNotNull(fetchedUser);
      Assert.assertEquals(user, fetchedUser);

      boolean result = cacheService.invalidateUserById(user.getUserId());

      Assert.assertTrue(result);
      
      fetchedUser = cacheService.getUserById(user.getUserId());

      Assert.assertNull(fetchedUser);
   }

   @Test
   public void invalidateUserByEmail() {
      User user = setUser(getNumber());

      cacheService.putUser(user);

      User fetchedUser = cacheService.getUserByEmail(user.getEmailId());

      Assert.assertNotNull(fetchedUser);
      Assert.assertEquals(user, fetchedUser);

      boolean result = cacheService.invalidateUserByEmail(user.getEmailId());

      Assert.assertTrue(result);
      
      fetchedUser = cacheService.getUserByEmail(user.getEmailId());

      Assert.assertNull(fetchedUser);

   }

   @Test
   public void invalidateUserByFcId() {
      User user = setUser(getNumber());

      cacheService.putUser(user);

      User fetchedUser = cacheService.getUserByFcId(user.getFcUserId());

      Assert.assertNotNull(fetchedUser);
      Assert.assertEquals(user, fetchedUser);

      boolean result = cacheService.invalidateUserByFcId(user.getFcUserId());

      Assert.assertTrue(result);
      
      fetchedUser = cacheService.getUserByFcId(user.getFcUserId());

      Assert.assertNull(fetchedUser);

   }

   @Test
   public void invalidateUserByMobile() {
      User user = setUser(getNumber());

      cacheService.putUser(user);

      User fetchedUser = cacheService.getUserByMobile(user.getMobileNumber());

      Assert.assertNotNull(fetchedUser);
      Assert.assertEquals(user, fetchedUser);

      boolean result = cacheService.invalidateUserByMobile(user.getMobileNumber());

      Assert.assertTrue(result);
      
      fetchedUser = cacheService.getUserByMobile(user.getMobileNumber());

      Assert.assertNull(fetchedUser);
   }

   @Test
   public void invalidateUserBySdFcId() {
      User user = setUser(getNumber());

      cacheService.putUser(user);

      User fetchedUser = cacheService.getUserBySdFcId(user.getSdFcUserId());

      Assert.assertNotNull(fetchedUser);
      Assert.assertEquals(user, fetchedUser);

      boolean result = cacheService.invalidateUserBySdFcId(user.getSdFcUserId());

      Assert.assertTrue(result);
      
      fetchedUser = cacheService.getUserBySdFcId(user.getSdFcUserId());

      Assert.assertNull(fetchedUser);
   }

   @Test
   public void invalidateUserBySdId() {
      User user = setUser(getNumber());

      cacheService.putUser(user);

      User fetchedUser = cacheService.getUserBySdId(user.getSdUserId());

      Assert.assertNotNull(fetchedUser);
      Assert.assertEquals(user, fetchedUser);

      boolean result = cacheService.invalidateUserBySdId(user.getSdUserId());

      Assert.assertTrue(result);
      
      fetchedUser = cacheService.getUserBySdId(user.getSdUserId());

      Assert.assertNull(fetchedUser);
   }

   @Test
   public void invalidateUserById_nullUser() {
      User user = setUser(getNumber());

      User fetchedUser = cacheService.getUserById(user.getUserId());

      Assert.assertNull(fetchedUser);

      boolean result = cacheService.invalidateUserById(user.getUserId());

      Assert.assertTrue(result);
   }

   @Test
   public void invalidateUserByEmail_nullUser() {
      User user = setUser(getNumber());

      User fetchedUser = cacheService.getUserByEmail(user.getEmailId());

      Assert.assertNull(fetchedUser);

      boolean result = cacheService.invalidateUserByEmail(user.getEmailId());

      Assert.assertTrue(result);

   }

   @Test
   public void invalidateUserByFcId_nullUser() {
      User user = setUser(getNumber());

      User fetchedUser = cacheService.getUserByFcId(user.getFcUserId());

      Assert.assertNull(fetchedUser);

      boolean result = cacheService.invalidateUserByFcId(user.getFcUserId());

      Assert.assertTrue(result);

   }

   @Test
   public void invalidateUserByMobile_nullUser() {
      User user = setUser(getNumber());

      User fetchedUser = cacheService.getUserByMobile(user.getMobileNumber());

      Assert.assertNull(fetchedUser);

      boolean result = cacheService.invalidateUserByMobile(user.getMobileNumber());

      Assert.assertTrue(result);
   }

   @Test
   public void invalidateUserBySdFcId_nullUser() {
      User user = setUser(getNumber());

      User fetchedUser = cacheService.getUserBySdFcId(user.getSdFcUserId());

      Assert.assertNull(fetchedUser);

      boolean result = cacheService.invalidateUserBySdFcId(user.getSdFcUserId());

      Assert.assertTrue(result);
   }

   @Test
   public void invalidateUserBySdId_nullUser() {
      User user = setUser(getNumber());

      User fetchedUser = cacheService.getUserBySdId(user.getSdUserId());

      Assert.assertNull(fetchedUser);

      boolean result = cacheService.invalidateUserBySdId(user.getSdUserId());

      Assert.assertTrue(result);
   }

   @Test
   @Ignore
   public void insertDuplicateUser() {
      User user = setUser(getNumber());
      boolean result = cacheService.putUser(user);
      Assert.assertTrue(result);
      
      User fetchedUser = cacheService.getUserBySdId(user.getSdUserId());
      Assert.assertNotNull(fetchedUser);
      Assert.assertEquals(user, fetchedUser);
      
      result = cacheService.putUser(user);
      Assert.assertTrue(result);
   }

   @Test
   public void insertUser_tempState() {
      User user = setUser(getNumber());
      user.setStatus(UserStatus.TEMP);
      
      boolean result = cacheService.putUser(user);
      Assert.assertFalse(result);
   }

   @Test
   public void insertNullUser() {
      User user = null;
      boolean result = cacheService.putUser(user);
      Assert.assertFalse(result);
   }

   @Test
   public void insertEmptyUser() {
      User user = new User();
      boolean result = cacheService.putUser(user);
      Assert.assertFalse(result);
   }

   @Test
   public void insertEmptyUser_withId() {
      User user = new User();
      user.setUserId("kjsdfhksdjf");

      boolean result = cacheService.putUser(user);
      Assert.assertTrue(result);

      User fetchedUser = cacheService.getUserById(user.getUserId());
      Assert.assertNotNull(fetchedUser);
      Assert.assertEquals(user, fetchedUser);

   }

   private User setUser(int number) {
      User user = new User();
      user.setCreatedTime(null);
      user.setDisplayName("myDisplayName");
      user.setDob(null);
      user.setEmailId("john.doe"+number+"@any.com");
      user.setEmailVerified(true);
      user.setEnabled(true);
      user.setFacebookUser(true);
      user.setFcUserId(666+number);
      user.setFirstName("John");
      user.setGender(Gender.MALE);
      user.setGoogleUser(false);
      user.setLanguagePref(Language.ENGLISH);
      user.setLastName("doe");
      user.setMiddleName("myMiddleName");
      user.setMobileNumber(String.valueOf(89239428+number));
      user.setMobileVerified(true);
      user.setOriginatingSrc(Merchant.ONECHECK);
      user.setPassword("mypassword");
      user.setPurpose("purpose");
      user.setSdFcUserId(130000999+number);
      user.setSdUserId(999+number);
      user.setStatus(UserStatus.REGISTERED);
      user.setUpdatedTime(null);
      user.setUserId("hdjahs"+number+"gdjshds");
      user.setUserSetPassword(true);
      return user;
   }
   
   private int getNumber(){
      int number = r.nextInt(1000000);
      return number;
   }

}
