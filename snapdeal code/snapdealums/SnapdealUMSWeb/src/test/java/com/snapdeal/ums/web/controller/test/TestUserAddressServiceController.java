/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 29-Oct-2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.web.controller.test;

import java.util.Date;
import java.util.List;

import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.base.utils.DateUtils;
import com.snapdeal.ums.core.cache.UMSPropertiesCache;
import com.snapdeal.ums.core.entity.User;
import com.snapdeal.ums.core.entity.UserAddress;
import com.snapdeal.ums.core.sro.user.UserAddressSRO;
import com.snapdeal.ums.dao.user.userAddress.IUserAddressDao;
import com.snapdeal.ums.dao.users.IUsersDao;
import com.snapdeal.ums.ext.userAddress.AddUserAddressRequest;
import com.snapdeal.ums.ext.userAddress.AddUserAddressResponse;
import com.snapdeal.ums.ext.userAddress.AddUserAddressTagRequest;
import com.snapdeal.ums.ext.userAddress.AddUserAddressTagResponse;
import com.snapdeal.ums.ext.userAddress.DeleteUserAddressByIdRequest;
import com.snapdeal.ums.ext.userAddress.DeleteUserAddressByIdResponse;
import com.snapdeal.ums.ext.userAddress.GetUserAddressByIdRequest;
import com.snapdeal.ums.ext.userAddress.GetUserAddressByIdResponse;
import com.snapdeal.ums.ext.userAddress.GetUserAddressesByUserIdRequest;
import com.snapdeal.ums.ext.userAddress.GetUserAddressesByUserIdResponse;
import com.snapdeal.ums.ext.userAddress.SetDefaultUserAddressRequest;
import com.snapdeal.ums.ext.userAddress.SetDefaultUserAddressResponse;
import com.snapdeal.ums.ext.userAddress.UpdateUserAddressRequest;
import com.snapdeal.ums.ext.userAddress.UpdateUserAddressResponse;
import com.snapdeal.ums.server.services.convertor.IUMSConvertorService;
import com.snapdeal.ums.utils.EncryptionUtils;
import com.snapdeal.ums.web.controller.UserAddressServiceController;

@ContextConfiguration(locations = { "/hsql-applicationContext-test.xml" })
public class TestUserAddressServiceController extends AbstractTransactionalTestNGSpringContextTests {
//
//    @Autowired
//    private UserAddressServiceController userAddressWebServiceController;
//
//    @Autowired
//    private IUMSConvertorService         mockUMSConvertorService;
//
//    @Autowired
//    private IUsersDao                    userDao;
//
//    @Autowired
//    private IUserAddressDao              userAddressDao;
//
//    @Mocked
//    private UMSPropertiesCache           umsPropertyCache;
//
//    private User                         testUser;
//
//    @BeforeMethod
//    public void setUp() {
//        testUser = new User();
//        testUser.setEmail("test_email@test.com");
//        testUser.setPassword("testPassword");
//        testUser.setEnabled(true);
//        testUser.setEmailVerified(true);
//        testUser.setMobileVerified(true);
//        testUser.setReferralCount(0);
//        testUser.setCreated(DateUtils.getCurrentTime());
//        testUser.setModified(DateUtils.getCurrentTime());
//        testUser.setAutocreated(false);
//
//        userDao.persistUser(testUser);
//
//    }
//
//    @Test
//    public void addUserAddressTagTest() {
//
//        UserAddress testUserAddress1 = createUserAddressInstance("testAddress1 1", "testAddress2 1", "HOME", "testCity", "testCountry", null, false, "testLandline", "testMobile",
//                "testName", "testPost", "testState", null, testUser);
//
//        userAddressDao.persistUserAddress(testUserAddress1);
//
//        AddUserAddressTagRequest request = new AddUserAddressTagRequest(testUserAddress1.getId(), testUserAddress1.getUser().getId(), "HOME",
//                EncryptionUtils.encrypt(testUserAddress1.getUser().getId()));
//
//        AddUserAddressTagResponse response = userAddressWebServiceController.addUserAddressTag(request);
//
//        AssertJUnit.assertTrue(response.isSuccessful());
//
//    }
//
//    @Test
//    public void addUserAddressTagAutorizationTest() {
//
//        UserAddress testUserAddress1 = createUserAddressInstance("testAddress1 1", "testAddress2 1", "HOME", "testCity", "testCountry", null, false, "testLandline", "testMobile",
//                "testName", "testPost", "testState", null, testUser);
//
//        userAddressDao.persistUserAddress(testUserAddress1);
//
//        AddUserAddressTagRequest request = new AddUserAddressTagRequest(testUserAddress1.getId(), testUserAddress1.getUser().getId(), "HOME", "UnAuthotizeKey");
//
//        AddUserAddressTagResponse response = userAddressWebServiceController.addUserAddressTag(request);
//
//        AssertJUnit.assertFalse(response.isSuccessful());
//        AssertJUnit.assertEquals(response.getValidationErrors().get(0).getCode(), 505);
//
//    }
//
//    @Test
//    public void addUserAddressTagMissingParamTest() {
//
//        UserAddress testUserAddress1 = createUserAddressInstance("testAddress1 1", "testAddress2 1", "HOME", "testCity", "testCountry", null, false, "testLandline", "testMobile",
//                "testName", "testPost", "testState", null, testUser);
//
//        userAddressDao.persistUserAddress(testUserAddress1);
//
//        AddUserAddressTagRequest request = new AddUserAddressTagRequest(0, testUserAddress1.getUser().getId(), "HOME", EncryptionUtils.encrypt(testUserAddress1.getUser().getId()));
//        AddUserAddressTagResponse response = userAddressWebServiceController.addUserAddressTag(request);
//        AssertJUnit.assertFalse(response.isSuccessful());
//        AssertJUnit.assertEquals(response.getValidationErrors().get(0).getCode(), 504);
//
//        AddUserAddressTagRequest request1 = new AddUserAddressTagRequest(testUserAddress1.getUser().getId(), 0, "HOME", EncryptionUtils.encrypt(testUserAddress1.getUser().getId()));
//        AddUserAddressTagResponse response1 = userAddressWebServiceController.addUserAddressTag(request1);
//        AssertJUnit.assertFalse(response1.isSuccessful());
//        AssertJUnit.assertEquals(response1.getValidationErrors().get(0).getCode(), 504);
//
//        AddUserAddressTagRequest request2 = new AddUserAddressTagRequest(null, testUserAddress1.getUser().getId(), "HOME",
//                EncryptionUtils.encrypt(testUserAddress1.getUser().getId()));
//        AddUserAddressTagResponse response2 = userAddressWebServiceController.addUserAddressTag(request2);
//        AssertJUnit.assertFalse(response2.isSuccessful());
//        AssertJUnit.assertEquals(response2.getValidationErrors().get(0).getCode(), 504);
//
//        AddUserAddressTagRequest request3 = new AddUserAddressTagRequest(testUserAddress1.getUser().getId(), null, "HOME",
//                EncryptionUtils.encrypt(testUserAddress1.getUser().getId()));
//        AddUserAddressTagResponse response3 = userAddressWebServiceController.addUserAddressTag(request3);
//        AssertJUnit.assertFalse(response3.isSuccessful());
//        AssertJUnit.assertEquals(response3.getValidationErrors().get(0).getCode(), 504);
//
//        AddUserAddressTagRequest request4 = new AddUserAddressTagRequest(null, null, "HOME", EncryptionUtils.encrypt(testUserAddress1.getUser().getId()));
//        AddUserAddressTagResponse response4 = userAddressWebServiceController.addUserAddressTag(request4);
//        AssertJUnit.assertFalse(response4.isSuccessful());
//        AssertJUnit.assertEquals(response4.getValidationErrors().get(0).getCode(), 504);
//
//    }
//
//    @Test
//    public void deleteUserAddressByIdTest() {
//
//        UserAddress testUserAddress1 = createUserAddressInstance("testAddress1 1", "testAddress2 1", "HOME", "testCity", "testCountry", null, false, "testLandline", "testMobile",
//                "testName", "testPost", "testState", null, testUser);
//        userAddressDao.persistUserAddress(testUserAddress1);
//        UserAddress userAddressToBeDeleted = userAddressDao.getUserAddressesByUserId(testUser.getId()).get(0);
//
//        DeleteUserAddressByIdRequest request = new DeleteUserAddressByIdRequest(userAddressToBeDeleted.getId(), userAddressToBeDeleted.getUser().getId(),
//                EncryptionUtils.encrypt(userAddressToBeDeleted.getUser().getId()));
//        DeleteUserAddressByIdResponse response = userAddressWebServiceController.deleteUserAddressById(request);
//
//        AssertJUnit.assertTrue(response.isSuccessful());
//    }
//
//    @Test
//    public void deleteUserAddressByIdAuthorizationTest() {
//
//        UserAddress testUserAddress1 = createUserAddressInstance("testAddress1 1", "testAddress2 1", "HOME", "testCity", "testCountry", null, false, "testLandline", "testMobile",
//                "testName", "testPost", "testState", null, testUser);
//        userAddressDao.persistUserAddress(testUserAddress1);
//        UserAddress userAddressToBeDeleted = userAddressDao.getUserAddressesByUserId(testUser.getId()).get(0);
//
//        DeleteUserAddressByIdRequest request = new DeleteUserAddressByIdRequest(userAddressToBeDeleted.getId(), userAddressToBeDeleted.getUser().getId(), "UnAuthotizeKey");
//        DeleteUserAddressByIdResponse response = userAddressWebServiceController.deleteUserAddressById(request);
//
//        AssertJUnit.assertFalse(response.isSuccessful());
//        AssertJUnit.assertEquals(response.getValidationErrors().get(0).getCode(), 505);
//    }
//
//    @Test
//    public void deleteUserAddressByIdMissingParamTest() {
//
//        UserAddress testUserAddress1 = createUserAddressInstance("testAddress1 1", "testAddress2 1", "HOME", "testCity", "testCountry", null, false, "testLandline", "testMobile",
//                "testName", "testPost", "testState", null, testUser);
//        userAddressDao.persistUserAddress(testUserAddress1);
//        UserAddress userAddressToBeDeleted = userAddressDao.getUserAddressesByUserId(testUser.getId()).get(0);
//
//        DeleteUserAddressByIdRequest request1 = new DeleteUserAddressByIdRequest(null, userAddressToBeDeleted.getUser().getId(),
//                EncryptionUtils.encrypt(testUserAddress1.getUser().getId()));
//        DeleteUserAddressByIdResponse response1 = userAddressWebServiceController.deleteUserAddressById(request1);
//        AssertJUnit.assertFalse(response1.isSuccessful());
//        AssertJUnit.assertEquals(response1.getValidationErrors().get(0).getCode(), 504);
//
//        DeleteUserAddressByIdRequest request2 = new DeleteUserAddressByIdRequest(0, userAddressToBeDeleted.getUser().getId(),
//                EncryptionUtils.encrypt(testUserAddress1.getUser().getId()));
//        DeleteUserAddressByIdResponse response2 = userAddressWebServiceController.deleteUserAddressById(request2);
//        AssertJUnit.assertFalse(response2.isSuccessful());
//        AssertJUnit.assertEquals(response2.getValidationErrors().get(0).getCode(), 504);
//
//        DeleteUserAddressByIdRequest request3 = new DeleteUserAddressByIdRequest(userAddressToBeDeleted.getId(), null, EncryptionUtils.encrypt(testUserAddress1.getUser().getId()));
//        DeleteUserAddressByIdResponse response3 = userAddressWebServiceController.deleteUserAddressById(request3);
//        AssertJUnit.assertFalse(response3.isSuccessful());
//        AssertJUnit.assertEquals(response3.getValidationErrors().get(0).getCode(), 504);
//
//        DeleteUserAddressByIdRequest request4 = new DeleteUserAddressByIdRequest(userAddressToBeDeleted.getId(), 0, EncryptionUtils.encrypt(testUserAddress1.getUser().getId()));
//        DeleteUserAddressByIdResponse response4 = userAddressWebServiceController.deleteUserAddressById(request4);
//        AssertJUnit.assertFalse(response4.isSuccessful());
//        AssertJUnit.assertEquals(response4.getValidationErrors().get(0).getCode(), 504);
//
//        DeleteUserAddressByIdRequest request5 = new DeleteUserAddressByIdRequest(null, null, EncryptionUtils.encrypt(testUserAddress1.getUser().getId()));
//        DeleteUserAddressByIdResponse response5 = userAddressWebServiceController.deleteUserAddressById(request5);
//        AssertJUnit.assertFalse(response5.isSuccessful());
//        AssertJUnit.assertEquals(response5.getValidationErrors().get(0).getCode(), 504);
//
//        DeleteUserAddressByIdRequest request6 = new DeleteUserAddressByIdRequest(0, 0, EncryptionUtils.encrypt(testUserAddress1.getUser().getId()));
//        DeleteUserAddressByIdResponse response6 = userAddressWebServiceController.deleteUserAddressById(request6);
//        AssertJUnit.assertFalse(response6.isSuccessful());
//        AssertJUnit.assertEquals(response6.getValidationErrors().get(0).getCode(), 504);
//
//    }
//
//    @Test
//    public void persitUserAddressTest() {
//
//        new NonStrictExpectations() {
//            {
//                umsPropertyCache = new UMSPropertiesCache();
//
//                CacheManager.getInstance().setCache(umsPropertyCache);
//                CacheManager.getInstance().getCache(UMSPropertiesCache.class).getUserAddressCountLimit();
//                result = 2;
//            }
//        };
//
//        UserAddress testUserAddress1 = createUserAddressInstance("testAddress1 1", "testAddress2 1", "HOME", "testCity", "testCountry", null, false, "testLandline", "testMobile",
//                "testName", "testPost", "testState", null, testUser);
//        UserAddressSRO userAddressSRO = mockUMSConvertorService.getUserAddressSROFromEntity(testUserAddress1);
//
//        AddUserAddressRequest request = new AddUserAddressRequest(userAddressSRO, EncryptionUtils.encrypt(userAddressSRO.getUserId()));
//        AddUserAddressResponse response = userAddressWebServiceController.addUserAddress(request);
//        AssertJUnit.assertTrue(response.isSuccessful());
//
//    }
//
//    @Test
//    public void persitUserAddressLimitCountTest() {
//        new NonStrictExpectations() {
//            {
//                umsPropertyCache = new UMSPropertiesCache();
//
//                CacheManager.getInstance().setCache(umsPropertyCache);
//                CacheManager.getInstance().getCache(UMSPropertiesCache.class).getUserAddressCountLimit();
//                result = 2;
//            }
//        };
//
//        UserAddress testUserAddress1 = createUserAddressInstance("testAddress1 2", "testAddress2 2", "HOME", "testCity", "testCountry", null, false, "testLandline", "testMobile1",
//                "testName", "testPost", "testState", null, testUser);
//        userAddressDao.persistUserAddress(testUserAddress1);
//        UserAddress testUserAddress2 = createUserAddressInstance("testAddress1 2", "testAddress2 2", "HOME", "testCity", "testCountry", null, false, "testLandline", "testMobile2",
//                "testName", "testPost", "testState", null, testUser);
//        userAddressDao.persistUserAddress(testUserAddress2);
//
//        UserAddress testUserAddress3 = createUserAddressInstance("testAddress1 3", "testAddress2 3", "HOME", "testCity", "testCountry", null, false, "testLandline", "testMobile3",
//                "testName", "testPost", "testState", null, testUser);
//        UserAddressSRO userAddressSRO = mockUMSConvertorService.getUserAddressSROFromEntity(testUserAddress3);
//
//        AddUserAddressRequest request = new AddUserAddressRequest(userAddressSRO, EncryptionUtils.encrypt(userAddressSRO.getUserId()));
//        AddUserAddressResponse response = userAddressWebServiceController.addUserAddress(request);
//        AssertJUnit.assertFalse(response.isSuccessful());
//        AssertJUnit.assertEquals(response.getValidationErrors().get(0).getCode(), 502);
//    }
//
//    @Test
//    public void persitUserAddressUniqueTest() {
//        new NonStrictExpectations() {
//            {
//                umsPropertyCache = new UMSPropertiesCache();
//
//                CacheManager.getInstance().setCache(umsPropertyCache);
//                CacheManager.getInstance().getCache(UMSPropertiesCache.class).getUserAddressCountLimit();
//                result = 2;
//            }
//        };
//
//        UserAddress testUserAddress1 = createUserAddressInstance("testAddress1 1", "testAddress2 1", "HOME", "testCity", "testCountry", null, false, "testLandline", "testMobile",
//                "testName", "testPost", "testState", null, testUser);
//        userAddressDao.persistUserAddress(testUserAddress1);
//
//        UserAddress testUserAddress3 = createUserAddressInstance("testAddress1 1", "testAddress2 1", "HOME", "testCity", "testCountry", null, false, "testLandline", "testMobile",
//                "testName", "testPost", "testState", null, testUser);
//        UserAddressSRO userAddressSRO = mockUMSConvertorService.getUserAddressSROFromEntity(testUserAddress3);
//
//        AddUserAddressRequest request = new AddUserAddressRequest(userAddressSRO, EncryptionUtils.encrypt(userAddressSRO.getUserId()));
//        AddUserAddressResponse response = userAddressWebServiceController.addUserAddress(request);
//        AssertJUnit.assertFalse(response.isSuccessful());
//        AssertJUnit.assertEquals(response.getValidationErrors().get(0).getCode(), 501);
//    }
//
//    @Test
//    public void persitUserAddressAuthorizationTest() {
//        new NonStrictExpectations() {
//            {
//                umsPropertyCache = new UMSPropertiesCache();
//
//                CacheManager.getInstance().setCache(umsPropertyCache);
//                CacheManager.getInstance().getCache(UMSPropertiesCache.class).getUserAddressCountLimit();
//                result = 2;
//            }
//        };
//
//        UserAddress testUserAddress1 = createUserAddressInstance("testAddress1 1", "testAddress2 1", "HOME", "testCity", "testCountry", null, false, "testLandline", "testMobile",
//                "testName", "testPost", "testState", null, testUser);
//        UserAddressSRO userAddressSRO = mockUMSConvertorService.getUserAddressSROFromEntity(testUserAddress1);
//
//        AddUserAddressRequest request = new AddUserAddressRequest(userAddressSRO, "UnAuthotizeKey");
//        AddUserAddressResponse response = userAddressWebServiceController.addUserAddress(request);
//        AssertJUnit.assertFalse(response.isSuccessful());
//        AssertJUnit.assertEquals(response.getValidationErrors().get(0).getCode(), 505);
//    }
//
//    @Test
//    public void persitUserAddressMissingParameterTest() {
//        new NonStrictExpectations() {
//            {
//                umsPropertyCache = new UMSPropertiesCache();
//
//                CacheManager.getInstance().setCache(umsPropertyCache);
//                CacheManager.getInstance().getCache(UMSPropertiesCache.class).getUserAddressCountLimit();
//                result = 2;
//            }
//        };
//
//        UserAddress testUserAddress1 = createUserAddressInstance("testAddress1 1", "testAddress2 1", "HOME", "testCity", "testCountry", null, false, "testLandline", "testMobile",
//                "testName", "testPost", "testState", null, testUser);
//        UserAddressSRO userAddressSRO = mockUMSConvertorService.getUserAddressSROFromEntity(testUserAddress1);
//
//        userAddressSRO.setUserId(null);
//        AddUserAddressRequest request1 = new AddUserAddressRequest(userAddressSRO, "UnAuthotizeKey");
//        AddUserAddressResponse response1 = userAddressWebServiceController.addUserAddress(request1);
//        AssertJUnit.assertFalse(response1.isSuccessful());
//        AssertJUnit.assertEquals(response1.getValidationErrors().get(0).getCode(), 504);
//
//        userAddressSRO.setUserId(0);
//        AddUserAddressRequest request2 = new AddUserAddressRequest(userAddressSRO, "UnAuthotizeKey");
//        AddUserAddressResponse response2 = userAddressWebServiceController.addUserAddress(request2);
//        AssertJUnit.assertFalse(response2.isSuccessful());
//        AssertJUnit.assertEquals(response2.getValidationErrors().get(0).getCode(), 504);
//
//        AddUserAddressRequest request3 = new AddUserAddressRequest(null, "UnAuthotizeKey");
//        AddUserAddressResponse response3 = userAddressWebServiceController.addUserAddress(request3);
//        AssertJUnit.assertFalse(response3.isSuccessful());
//        AssertJUnit.assertEquals(response3.getValidationErrors().get(0).getCode(), 504);
//
//    }
//
//    @Test
//    public void updateUserAddressTest() {
//
//        new NonStrictExpectations() {
//            {
//                umsPropertyCache = new UMSPropertiesCache();
//
//                CacheManager.getInstance().setCache(umsPropertyCache);
//                CacheManager.getInstance().getCache(UMSPropertiesCache.class).getUserAddressCountLimit();
//                result = 2;
//            }
//        };
//
//        UserAddress testUserAddress1 = createUserAddressInstance("testAddress1 1", "testAddress2 1", "HOME", "testCity", "testCountry", null, false, "testLandline", "testMobile",
//                "testName", "testPost", "testState", null, testUser);
//        userAddressDao.persistUserAddress(testUserAddress1);
//
//        UserAddress addressNeedToUpdate = userAddressDao.getUserAddressesByUserId(testUserAddress1.getUser().getId()).get(0);
//        UserAddressSRO addressSRONeedToUpdate = mockUMSConvertorService.getUserAddressSROFromEntity(addressNeedToUpdate);
//        UpdateUserAddressRequest request = new UpdateUserAddressRequest(addressSRONeedToUpdate, EncryptionUtils.encrypt(addressSRONeedToUpdate.getUserId()));
//        UpdateUserAddressResponse response = userAddressWebServiceController.updateUserAddress(request);
//        AssertJUnit.assertTrue(response.isSuccessful());
//
//        addressNeedToUpdate.setDefault(true);
//        UpdateUserAddressRequest request1 = new UpdateUserAddressRequest(addressSRONeedToUpdate, EncryptionUtils.encrypt(addressSRONeedToUpdate.getUserId()));
//        UpdateUserAddressResponse response1 = userAddressWebServiceController.updateUserAddress(request1);
//        AssertJUnit.assertTrue(response1.isSuccessful());
//
//    }
//
//    @Test
//    public void updateUserAddressAuthotizationTest() {
//
//        new NonStrictExpectations() {
//            {
//                umsPropertyCache = new UMSPropertiesCache();
//
//                CacheManager.getInstance().setCache(umsPropertyCache);
//                CacheManager.getInstance().getCache(UMSPropertiesCache.class).getUserAddressCountLimit();
//                result = 2;
//            }
//        };
//
//        UserAddress testUserAddress1 = createUserAddressInstance("testAddress1 1", "testAddress2 1", "HOME", "testCity", "testCountry", null, false, "testLandline", "testMobile",
//                "testName", "testPost", "testState", null, testUser);
//        userAddressDao.persistUserAddress(testUserAddress1);
//
//        UserAddress addressNeedToUpdate = userAddressDao.getUserAddressesByUserId(testUserAddress1.getUser().getId()).get(0);
//        UserAddressSRO addressSRONeedToUpdate = mockUMSConvertorService.getUserAddressSROFromEntity(addressNeedToUpdate);
//        UpdateUserAddressRequest request = new UpdateUserAddressRequest(addressSRONeedToUpdate, "UnAuthorized");
//        UpdateUserAddressResponse response = userAddressWebServiceController.updateUserAddress(request);
//        AssertJUnit.assertFalse(response.isSuccessful());
//        AssertJUnit.assertEquals(response.getValidationErrors().get(0).getCode(), 505);
//
//    }
//
//    @Test
//    public void updateUserAddressParamMissngTest() {
//
//        new NonStrictExpectations() {
//            {
//                umsPropertyCache = new UMSPropertiesCache();
//
//                CacheManager.getInstance().setCache(umsPropertyCache);
//                CacheManager.getInstance().getCache(UMSPropertiesCache.class).getUserAddressCountLimit();
//                result = 2;
//            }
//        };
//
//        UserAddress testUserAddress1 = createUserAddressInstance("testAddress1 1", "testAddress2 1", "HOME", "testCity", "testCountry", null, false, "testLandline", "testMobile",
//                "testName", "testPost", "testState", null, testUser);
//        userAddressDao.persistUserAddress(testUserAddress1);
//
//        UserAddress addressNeedToUpdate = userAddressDao.getUserAddressesByUserId(testUserAddress1.getUser().getId()).get(0);
//
//        UserAddressSRO addressSRONeedToUpdate = mockUMSConvertorService.getUserAddressSROFromEntity(addressNeedToUpdate);
//        addressSRONeedToUpdate.setId(null);
//        UpdateUserAddressRequest request = new UpdateUserAddressRequest(addressSRONeedToUpdate, "UnAuthorized");
//        UpdateUserAddressResponse response = userAddressWebServiceController.updateUserAddress(request);
//        AssertJUnit.assertFalse(response.isSuccessful());
//        AssertJUnit.assertEquals(response.getValidationErrors().get(0).getCode(), 504);
//
//        addressSRONeedToUpdate.setId(0);
//        UpdateUserAddressRequest request1 = new UpdateUserAddressRequest(addressSRONeedToUpdate, "UnAuthorized");
//        UpdateUserAddressResponse response1 = userAddressWebServiceController.updateUserAddress(request1);
//        AssertJUnit.assertFalse(response1.isSuccessful());
//        AssertJUnit.assertEquals(response1.getValidationErrors().get(0).getCode(), 504);
//
//        UserAddressSRO addressSRONeedToUpdate1 = mockUMSConvertorService.getUserAddressSROFromEntity(addressNeedToUpdate);
//        addressSRONeedToUpdate1.setUserId(null);
//        UpdateUserAddressRequest request2 = new UpdateUserAddressRequest(addressSRONeedToUpdate, "UnAuthorized");
//        UpdateUserAddressResponse response2 = userAddressWebServiceController.updateUserAddress(request2);
//        AssertJUnit.assertFalse(response2.isSuccessful());
//        AssertJUnit.assertEquals(response2.getValidationErrors().get(0).getCode(), 504);
//
//        addressSRONeedToUpdate1.setUserId(0);
//        UpdateUserAddressRequest request3 = new UpdateUserAddressRequest(addressSRONeedToUpdate, "UnAuthorized");
//        UpdateUserAddressResponse response3 = userAddressWebServiceController.updateUserAddress(request3);
//        AssertJUnit.assertFalse(response3.isSuccessful());
//        AssertJUnit.assertEquals(response3.getValidationErrors().get(0).getCode(), 504);
//
//        addressSRONeedToUpdate1.setUserId(0);
//        addressSRONeedToUpdate1.setId(0);
//        UpdateUserAddressRequest request4 = new UpdateUserAddressRequest(addressSRONeedToUpdate, "UnAuthorized");
//        UpdateUserAddressResponse response4 = userAddressWebServiceController.updateUserAddress(request4);
//        AssertJUnit.assertFalse(response4.isSuccessful());
//        AssertJUnit.assertEquals(response4.getValidationErrors().get(0).getCode(), 504);
//
//        addressSRONeedToUpdate1.setUserId(null);
//        addressSRONeedToUpdate1.setId(null);
//        UpdateUserAddressRequest request5 = new UpdateUserAddressRequest(addressSRONeedToUpdate, "UnAuthorized");
//        UpdateUserAddressResponse response5 = userAddressWebServiceController.updateUserAddress(request5);
//        AssertJUnit.assertFalse(response5.isSuccessful());
//        AssertJUnit.assertEquals(response5.getValidationErrors().get(0).getCode(), 504);
//    }
//
//    @Test
//    public void setDefaultUserAddressTest() {
//        UserAddress testUserAddress1 = createUserAddressInstance("testAddress1 1", "testAddress2 1", "HOME", "testCity", "testCountry", null, false, "testLandline", "testMobile",
//                "testName", "testPost", "testState", null, testUser);
//        userAddressDao.persistUserAddress(testUserAddress1);
//
//        UserAddress testUserAddress2 = createUserAddressInstance("testAddress1 2", "testAddress2 2", "HOME", "testCity", "testCountry", null, false, "testLandline", "testMobile",
//                "testName2", "testPost2", "testState2", null, testUser);
//        userAddressDao.persistUserAddress(testUserAddress2);
//
//        List<UserAddress> userAddressess = userAddressDao.getUserAddressesByUserId(testUser.getId());
//
//        SetDefaultUserAddressRequest request = new SetDefaultUserAddressRequest(userAddressess.get(0).getId(), userAddressess.get(0).getUser().getId(),
//                EncryptionUtils.encrypt(userAddressess.get(0).getUser().getId()));
//        SetDefaultUserAddressResponse response = userAddressWebServiceController.setDefaultUserAddress(request);
//        AssertJUnit.assertTrue(response.isSuccessful());
//    }
//
//    @Test
//    public void setDefaultUserAddressParamMissingTest() {
//        UserAddress testUserAddress1 = createUserAddressInstance("testAddress1 1", "testAddress2 1", "HOME", "testCity", "testCountry", null, false, "testLandline", "testMobile",
//                "testName", "testPost", "testState", null, testUser);
//        userAddressDao.persistUserAddress(testUserAddress1);
//
//        UserAddress testUserAddress2 = createUserAddressInstance("testAddress1 2", "testAddress2 2", "HOME", "testCity", "testCountry", null, false, "testLandline", "testMobile",
//                "testName2", "testPost2", "testState2", null, testUser);
//        userAddressDao.persistUserAddress(testUserAddress2);
//
//        List<UserAddress> userAddressess = userAddressDao.getUserAddressesByUserId(testUser.getId());
//
//        SetDefaultUserAddressRequest request = new SetDefaultUserAddressRequest(null, userAddressess.get(0).getUser().getId(),
//                EncryptionUtils.encrypt(userAddressess.get(0).getUser().getId()));
//        SetDefaultUserAddressResponse response = userAddressWebServiceController.setDefaultUserAddress(request);
//        AssertJUnit.assertFalse(response.isSuccessful());
//        AssertJUnit.assertEquals(response.getValidationErrors().get(0).getCode(), 504);
//
//        SetDefaultUserAddressRequest request1 = new SetDefaultUserAddressRequest(0, userAddressess.get(0).getUser().getId(),
//                EncryptionUtils.encrypt(userAddressess.get(0).getUser().getId()));
//        SetDefaultUserAddressResponse response1 = userAddressWebServiceController.setDefaultUserAddress(request1);
//        AssertJUnit.assertFalse(response1.isSuccessful());
//        AssertJUnit.assertEquals(response1.getValidationErrors().get(0).getCode(), 504);
//
//        SetDefaultUserAddressRequest request2 = new SetDefaultUserAddressRequest(userAddressess.get(0).getId(), null,
//                EncryptionUtils.encrypt(userAddressess.get(0).getUser().getId()));
//        SetDefaultUserAddressResponse response2 = userAddressWebServiceController.setDefaultUserAddress(request2);
//        AssertJUnit.assertFalse(response2.isSuccessful());
//        AssertJUnit.assertEquals(response2.getValidationErrors().get(0).getCode(), 504);
//
//        SetDefaultUserAddressRequest request3 = new SetDefaultUserAddressRequest(userAddressess.get(0).getId(), 0, EncryptionUtils.encrypt(userAddressess.get(0).getUser().getId()));
//        SetDefaultUserAddressResponse response3 = userAddressWebServiceController.setDefaultUserAddress(request3);
//        AssertJUnit.assertFalse(response3.isSuccessful());
//        AssertJUnit.assertEquals(response3.getValidationErrors().get(0).getCode(), 504);
//    }
//
//    @Test
//    public void setDefaultUserAddressAuthorizationTest() {
//        UserAddress testUserAddress1 = createUserAddressInstance("testAddress1 1", "testAddress2 1", "HOME", "testCity", "testCountry", null, false, "testLandline", "testMobile",
//                "testName", "testPost", "testState", null, testUser);
//        userAddressDao.persistUserAddress(testUserAddress1);
//
//        UserAddress testUserAddress2 = createUserAddressInstance("testAddress1 2", "testAddress2 2", "HOME", "testCity", "testCountry", null, false, "testLandline", "testMobile",
//                "testName2", "testPost2", "testState2", null, testUser);
//        userAddressDao.persistUserAddress(testUserAddress2);
//
//        List<UserAddress> userAddressess = userAddressDao.getUserAddressesByUserId(testUser.getId());
//
//        SetDefaultUserAddressRequest request = new SetDefaultUserAddressRequest(userAddressess.get(0).getId(), userAddressess.get(0).getUser().getId(), "UnAuthorized");
//        SetDefaultUserAddressResponse response = userAddressWebServiceController.setDefaultUserAddress(request);
//        AssertJUnit.assertFalse(response.isSuccessful());
//        AssertJUnit.assertEquals(response.getValidationErrors().get(0).getCode(), 505);
//    }
//
//    @Test
//    public void getUserAddressesByUserIdTest() {
//        UserAddress testUserAddress1 = createUserAddressInstance("testAddress1 1", "testAddress2 1", "HOME", "testCity", "testCountry", null, false, "testLandline", "testMobile",
//                "testName", "testPost", "testState", null, testUser);
//        userAddressDao.persistUserAddress(testUserAddress1);
//
//        UserAddress testUserAddress2 = createUserAddressInstance("testAddress1 2", "testAddress2 2", "HOME", "testCity", "testCountry", null, false, "testLandline", "testMobile",
//                "testName2", "testPost2", "testState2", null, testUser);
//        userAddressDao.persistUserAddress(testUserAddress2);
//
//        GetUserAddressesByUserIdRequest request = new GetUserAddressesByUserIdRequest(testUser.getId());
//        GetUserAddressesByUserIdResponse response = userAddressWebServiceController.getUserAddressesByUserId(request);
//        AssertJUnit.assertTrue(response.isSuccessful());
//
//    }
//
//    @Test
//    public void getUserAddressesByUserIdMissingParamTest() {
//        UserAddress testUserAddress1 = createUserAddressInstance("testAddress1 1", "testAddress2 1", "HOME", "testCity", "testCountry", null, false, "testLandline", "testMobile",
//                "testName", "testPost", "testState", null, testUser);
//        userAddressDao.persistUserAddress(testUserAddress1);
//
//        UserAddress testUserAddress2 = createUserAddressInstance("testAddress1 2", "testAddress2 2", "HOME", "testCity", "testCountry", null, false, "testLandline", "testMobile",
//                "testName2", "testPost2", "testState2", null, testUser);
//        userAddressDao.persistUserAddress(testUserAddress2);
//
//        GetUserAddressesByUserIdRequest request = new GetUserAddressesByUserIdRequest(null);
//        GetUserAddressesByUserIdResponse response = userAddressWebServiceController.getUserAddressesByUserId(request);
//        AssertJUnit.assertFalse(response.isSuccessful());
//        AssertJUnit.assertEquals(response.getValidationErrors().get(0).getCode(), 504);
//
//        GetUserAddressesByUserIdRequest request1 = new GetUserAddressesByUserIdRequest(-1);
//        GetUserAddressesByUserIdResponse response1 = userAddressWebServiceController.getUserAddressesByUserId(request1);
//        AssertJUnit.assertFalse(response1.isSuccessful());
//        AssertJUnit.assertEquals(response1.getValidationErrors().get(0).getCode(), 504);
//
//    }
//
//    @Test
//    public void getuserAddressByIdTest() {
//        UserAddress testUserAddress1 = createUserAddressInstance("testAddress1 1", "testAddress2 1", "HOME", "testCity", "testCountry", null, false, "testLandline", "testMobile",
//                "testName", "testPost", "testState", null, testUser);
//        userAddressDao.persistUserAddress(testUserAddress1);
//
//        GetUserAddressByIdRequest request = new GetUserAddressByIdRequest(userAddressDao.getUserAddressesByUserId(testUser.getId()).get(0).getId());
//        GetUserAddressByIdResponse response = userAddressWebServiceController.getUserAddressById(request);
//        AssertJUnit.assertTrue(response.isSuccessful());
//    }
//
//    @Test
//    public void getuserAddressByIdMissingParamTest() {
//        UserAddress testUserAddress1 = createUserAddressInstance("testAddress1 1", "testAddress2 1", "HOME", "testCity", "testCountry", null, false, "testLandline", "testMobile",
//                "testName", "testPost", "testState", null, testUser);
//        userAddressDao.persistUserAddress(testUserAddress1);
//
//        GetUserAddressByIdRequest request = new GetUserAddressByIdRequest(null);
//        GetUserAddressByIdResponse response = userAddressWebServiceController.getUserAddressById(request);
//        AssertJUnit.assertFalse(response.isSuccessful());
//        AssertJUnit.assertEquals(response.getValidationErrors().get(0).getCode(), 504);
//
//        GetUserAddressByIdRequest request1 = new GetUserAddressByIdRequest(null);
//        GetUserAddressByIdResponse response1 = userAddressWebServiceController.getUserAddressById(request1);
//        AssertJUnit.assertFalse(response1.isSuccessful());
//        AssertJUnit.assertEquals(response1.getValidationErrors().get(0).getCode(), 504);
//    }
//
//    private UserAddress createUserAddressInstance(String userAddressLine1, String userAddressLine2, String addressTag, String city, String country, Date created,
//            boolean isDefault, String landLine, String mobile, String name, String postalCode, String state, Date updated, User user) {
//
//        UserAddress testUserAddress = new UserAddress();
//        testUserAddress.setAddress1(userAddressLine1);
//        testUserAddress.setAddress2(userAddressLine2);
//        testUserAddress.setAddressTag(addressTag);
//        testUserAddress.setCity(city);
//        testUserAddress.setCountry(country);
//
//        if (created == null)
//            testUserAddress.setCreated(DateUtils.getCurrentTime());
//        else
//            testUserAddress.setCreated(created);
//
//        testUserAddress.setDefault(isDefault);
//        testUserAddress.setLandline(landLine);
//        testUserAddress.setMobile(mobile);
//        testUserAddress.setName(name);
//        testUserAddress.setPostalCode(postalCode);
//        testUserAddress.setState(state);
//
//        if (updated == null)
//            testUserAddress.setUpdated(DateUtils.getCurrentTime());
//        else
//            testUserAddress.setUpdated(updated);
//
//        testUserAddress.setUser(testUser);
//        return testUserAddress;
//    }
//
}
