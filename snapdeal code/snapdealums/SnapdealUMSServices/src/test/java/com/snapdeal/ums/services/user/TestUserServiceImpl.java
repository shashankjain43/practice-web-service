/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 21-Nov-2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.services.user;

import java.util.Date;
import java.util.Set;

import mockit.Injectable;
import mockit.NonStrictExpectations;
import mockit.Tested;

import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.snapdeal.base.cache.EmailVerificationCode;
import com.snapdeal.ums.core.entity.Role;
import com.snapdeal.ums.core.entity.User;
import com.snapdeal.ums.core.entity.UserRole;
import com.snapdeal.ums.core.sro.user.EmailVerificationCodeSRO;
import com.snapdeal.ums.core.sro.user.UserSRO;
import com.snapdeal.ums.dao.users.IUsersDao;
import com.snapdeal.ums.ext.user.ClearEmailVerificationCodeRequest;
import com.snapdeal.ums.ext.user.ClearEmailVerificationCodeResponse;
import com.snapdeal.ums.ext.user.CreateEmailVerificationCodeRequest;
import com.snapdeal.ums.ext.user.CreateEmailVerificationCodeResponse;
import com.snapdeal.ums.ext.user.GetEmailVerificationCodeRequest;
import com.snapdeal.ums.ext.user.GetEmailVerificationCodeResponse;
import com.snapdeal.ums.ext.user.GetUserByEmailRequest;
import com.snapdeal.ums.ext.user.GetUserByEmailResponse;
import com.snapdeal.ums.ext.user.GetUserByIdRequest;
import com.snapdeal.ums.ext.user.GetUserByIdResponse;
import com.snapdeal.ums.ext.user.IsMobileExistRequest;
import com.snapdeal.ums.ext.user.IsMobileExistResponse;
import com.snapdeal.ums.ext.user.IsUserExistsRequest;
import com.snapdeal.ums.ext.user.IsUserExistsResponse;
import com.snapdeal.ums.ext.user.VerifyUserRequest;
import com.snapdeal.ums.ext.user.VerifyUserResponse;
import com.snapdeal.ums.server.services.IUserServiceInternal;
import com.snapdeal.ums.server.services.convertor.IUMSConvertorService;
import com.snapdeal.ums.server.services.impl.UserServiceImpl;

public class TestUserServiceImpl {

    @Tested
    private UserServiceImpl      umsUserService;

    @Injectable
    private IUserServiceInternal userServiceInternal;

    @Injectable
    private IUMSConvertorService umsConvertorService;

    @Injectable
    private IUsersDao            userDao;

    @BeforeMethod
    public void setUp() {

    }

    @Test
    public void isMobileExistFalseResponseTest() {

        new NonStrictExpectations() {
            {
                userServiceInternal.isMobileExist(anyString);
                result = false;
            }
        };

        IsMobileExistResponse response = umsUserService.isMobileExist(new IsMobileExistRequest("9999999999"));
        AssertJUnit.assertTrue(response.isSuccessful());
        AssertJUnit.assertFalse(response.getIsMobileExist());
    }

    @Test
    public void isMobileExistTrueResponseTest() {

        new NonStrictExpectations() {
            {
                userServiceInternal.isMobileExist(anyString);
                result = true;
            }
        };

        IsMobileExistResponse response = umsUserService.isMobileExist(new IsMobileExistRequest("9999999999"));
        AssertJUnit.assertTrue(response.isSuccessful());
        AssertJUnit.assertTrue(response.getIsMobileExist());
    }

    @Test
    public void isMobileExistNullRequesTest() {

        IsMobileExistResponse response = umsUserService.isMobileExist(null);
        AssertJUnit.assertFalse(response.isSuccessful());
    }

    @Test
    public void isMobileExistNullMobileNumberTest() {

        IsMobileExistResponse response = umsUserService.isMobileExist(new IsMobileExistRequest(null));
        AssertJUnit.assertFalse(response.isSuccessful());
    }

    @Test
    public void isMobileExistEmptyMobileNumberTest() {
        IsMobileExistResponse response = umsUserService.isMobileExist(new IsMobileExistRequest(""));
        AssertJUnit.assertFalse(response.isSuccessful());

        IsMobileExistResponse response1 = umsUserService.isMobileExist(new IsMobileExistRequest("  "));
        AssertJUnit.assertFalse(response1.isSuccessful());
    }

    @Test
    public void isUserExistTest() {

        new NonStrictExpectations() {
            {
                userServiceInternal.isUserExists(anyString);
                result = true;
            }
        };

        IsUserExistsResponse response = umsUserService.isUserExists(new IsUserExistsRequest("Test@snapdeal.com"));
        AssertJUnit.assertTrue(response.isSuccessful());
        AssertJUnit.assertTrue(response.getIsUserExists());
    }

    @Test
    public void isUserExistNullEmail() {
        IsUserExistsResponse response = umsUserService.isUserExists(new IsUserExistsRequest(null));
        AssertJUnit.assertFalse(response.isSuccessful());
        AssertJUnit.assertFalse(response.getIsUserExists());
    }

    @Test
    public void isUserExistEmptyEmail() {

        IsUserExistsResponse response = umsUserService.isUserExists(new IsUserExistsRequest(""));
        AssertJUnit.assertFalse(response.isSuccessful());
        AssertJUnit.assertFalse(response.getIsUserExists());

        IsUserExistsResponse response1 = umsUserService.isUserExists(new IsUserExistsRequest(" "));
        AssertJUnit.assertFalse(response1.isSuccessful());
        AssertJUnit.assertFalse(response1.getIsUserExists());
    }

    @Test
    public void getUserByEmailTest() {
        
        new NonStrictExpectations() {
            {
                userServiceInternal.getUserByEmail(anyString);
                result = new User();
                
                umsConvertorService.getUserSROWithRolesfromEnity((User)any);
                result=new UserSRO();
            }
        };
        
        GetUserByEmailResponse response = umsUserService.getUserByEmail(new GetUserByEmailRequest("Test@snapdeal.com"));
        AssertJUnit.assertTrue(response.isSuccessful());
        AssertJUnit.assertNotNull(response.getGetUserByEmail());
        
    }
    
    @Test
    public void getUserByEmailUserNotFoundInDBTest() {
        
        new NonStrictExpectations() {
            {
                userServiceInternal.getUserByEmail(anyString);
                result = null;
                
                umsConvertorService.getUserSROWithRolesfromEnity((User)any);
                result=null;
            }
        };
        
        GetUserByEmailResponse response = umsUserService.getUserByEmail(new GetUserByEmailRequest("Test@snapdeal.com"));
        AssertJUnit.assertTrue(response.isSuccessful());
        AssertJUnit.assertNull(response.getGetUserByEmail());
        
    }
    
    @Test
    public void getUserByEmailNullEmailTest() {
        
        GetUserByEmailResponse response = umsUserService.getUserByEmail(new GetUserByEmailRequest(null));
        AssertJUnit.assertFalse(response.isSuccessful());
        AssertJUnit.assertNull(response.getGetUserByEmail());
        
    }
    
    @Test
    public void getUserByEmailEmptyEmailTest() {
        
        GetUserByEmailResponse response = umsUserService.getUserByEmail(new GetUserByEmailRequest(""));
        AssertJUnit.assertFalse(response.isSuccessful());
        AssertJUnit.assertNull(response.getGetUserByEmail());
        
        GetUserByEmailResponse response1 = umsUserService.getUserByEmail(new GetUserByEmailRequest(" "));
        AssertJUnit.assertFalse(response1.isSuccessful());
        AssertJUnit.assertNull(response1.getGetUserByEmail());
        
    }

    @Test
    public void verifyUserTest(){
        
        new NonStrictExpectations() {
            {
                userServiceInternal.verifyUser(anyString, anyString);
                result = true;
            }
        };
        
        VerifyUserResponse response = umsUserService.verifyUser(new VerifyUserRequest("Test@snapdeal.com", "Test_verificationCode"));
        AssertJUnit.assertTrue(response.isSuccessful());
        AssertJUnit.assertTrue(response.getVerifyUser());
    }
    
    @Test
    public void unVerifyUserTest(){
        
        new NonStrictExpectations() {
            {
                userServiceInternal.verifyUser(anyString, anyString);
                result = false;
            }
        };
        
        VerifyUserResponse response = umsUserService.verifyUser(new VerifyUserRequest("Test@snapdeal.com", "Test_verificationCode"));
        AssertJUnit.assertTrue(response.isSuccessful());
        AssertJUnit.assertFalse(response.getVerifyUser());
    }
    
    @Test
    public void verifyUserNullEmailCodeTest(){
        
        VerifyUserResponse response = umsUserService.verifyUser(new VerifyUserRequest("Test@snapdeal.com", null));
        AssertJUnit.assertFalse(response.isSuccessful());
        AssertJUnit.assertFalse(response.getVerifyUser());
        
        VerifyUserResponse response1 = umsUserService.verifyUser(new VerifyUserRequest(null, "Test_verificationCode"));
        AssertJUnit.assertFalse(response1.isSuccessful());
        AssertJUnit.assertFalse(response1.getVerifyUser());
        
        VerifyUserResponse response2 = umsUserService.verifyUser(new VerifyUserRequest(null, null));
        AssertJUnit.assertFalse(response2.isSuccessful());
        AssertJUnit.assertFalse(response2.getVerifyUser());
    }
    
    @Test
    public void verifyUserEmptyEmailCodeTest(){
        
        VerifyUserResponse response = umsUserService.verifyUser(new VerifyUserRequest("Test@snapdeal.com", ""));
        AssertJUnit.assertFalse(response.isSuccessful());
        AssertJUnit.assertFalse(response.getVerifyUser());
        
        VerifyUserResponse response1 = umsUserService.verifyUser(new VerifyUserRequest("Test@snapdeal.com", "  "));
        AssertJUnit.assertFalse(response1.isSuccessful());
        AssertJUnit.assertFalse(response1.getVerifyUser());
        
        VerifyUserResponse response2 = umsUserService.verifyUser(new VerifyUserRequest("", "Test_verificationCode"));
        AssertJUnit.assertFalse(response2.isSuccessful());
        AssertJUnit.assertFalse(response2.getVerifyUser());
        
        
        VerifyUserResponse response3 = umsUserService.verifyUser(new VerifyUserRequest("  ", "Test_verificationCode"));
        AssertJUnit.assertFalse(response3.isSuccessful());
        AssertJUnit.assertFalse(response3.getVerifyUser());
        
        VerifyUserResponse response4 = umsUserService.verifyUser(new VerifyUserRequest("", ""));
        AssertJUnit.assertFalse(response4.isSuccessful());
        AssertJUnit.assertFalse(response4.getVerifyUser());
        
        VerifyUserResponse response5 = umsUserService.verifyUser(new VerifyUserRequest(" ", " "));
        AssertJUnit.assertFalse(response5.isSuccessful());
        AssertJUnit.assertFalse(response5.getVerifyUser());
    }
    
    @Test
    public void getUserByIdTest(){
        
        new NonStrictExpectations() {
            {
                userServiceInternal.getUserById(anyInt);
                result = new User();
                
                umsConvertorService.getUserSROWithRolesfromEnity((User)any);
                result=new UserSRO();
            }
        };
        
        GetUserByIdResponse response = umsUserService.getUserById(new GetUserByIdRequest(1));
        AssertJUnit.assertTrue(response.isSuccessful());
        AssertJUnit.assertNotNull(response.getGetUserById());
        
    }
    
    @Test
    public void getUserByIdNegativeIdTest(){
        
        GetUserByIdResponse response = umsUserService.getUserById(new GetUserByIdRequest(-1));
        AssertJUnit.assertFalse(response.isSuccessful());
        AssertJUnit.assertNull(response.getGetUserById());
        
    }
    
    
    @Test
    public void createEmailVerificationCodeTest(){
        
        new NonStrictExpectations() {
            {
                userServiceInternal.createEmailVerificationCode(anyString, anyString, anyString);
                result = new EmailVerificationCode();
                
                umsConvertorService.getEmailVerificationCodeSROfromEntity((EmailVerificationCode)any);
                result=new EmailVerificationCodeSRO();
            }
        };
        
        CreateEmailVerificationCodeRequest request = new CreateEmailVerificationCodeRequest("Test@snapdeal.com", "Test_Source", "Test_targetURL");
        CreateEmailVerificationCodeResponse response = umsUserService.createEmailVerificationCode(request);
        AssertJUnit.assertTrue(response.isSuccessful());
        AssertJUnit.assertNotNull(response.getCreateEmailVerificationCode());
    }
    
    @Test
    public void createEmailVerificationCodeNullfromInternalTest(){
        
        new NonStrictExpectations() {
            {
                userServiceInternal.createEmailVerificationCode(anyString, anyString, anyString);
                result = null;
                
                umsConvertorService.getEmailVerificationCodeSROfromEntity((EmailVerificationCode)any);
                result=null;
            }
        };
        
        CreateEmailVerificationCodeRequest request = new CreateEmailVerificationCodeRequest("Test@snapdeal.com", "Test_Source", "Test_targetURL");
        CreateEmailVerificationCodeResponse response = umsUserService.createEmailVerificationCode(request);
        AssertJUnit.assertTrue(response.isSuccessful());
        AssertJUnit.assertNull(response.getCreateEmailVerificationCode());
    }
    
    @Test
    public void createEmailVerificationCodeNullRequestParamTest(){
        
        new NonStrictExpectations() {
            {
                userServiceInternal.createEmailVerificationCode(anyString, anyString, anyString);
                result = new EmailVerificationCode();
                
                umsConvertorService.getEmailVerificationCodeSROfromEntity((EmailVerificationCode)any);
                result=new EmailVerificationCodeSRO();
            }
        };
        
        CreateEmailVerificationCodeRequest request = new CreateEmailVerificationCodeRequest(null, "Test_Source", "Test_targetURL");
        CreateEmailVerificationCodeResponse response = umsUserService.createEmailVerificationCode(request);
        AssertJUnit.assertFalse(response.isSuccessful());
        AssertJUnit.assertNull(response.getCreateEmailVerificationCode());
        
        CreateEmailVerificationCodeRequest request1 = new CreateEmailVerificationCodeRequest("Test@snapdeal.com", null, null);
        CreateEmailVerificationCodeResponse response1 = umsUserService.createEmailVerificationCode(request1);
        AssertJUnit.assertTrue(response1.isSuccessful());
        AssertJUnit.assertNotNull(response1.getCreateEmailVerificationCode());
    }
    
    
    @Test
    public void createEmailVerificationCodeEmptyStringRequestParamTest(){
        
        new NonStrictExpectations() {
            {
                userServiceInternal.createEmailVerificationCode(anyString, anyString, anyString);
                result = new EmailVerificationCode();
                
                umsConvertorService.getEmailVerificationCodeSROfromEntity((EmailVerificationCode)any);
                result=new EmailVerificationCodeSRO();
            }
        };
        
        CreateEmailVerificationCodeRequest request = new CreateEmailVerificationCodeRequest("", "Test_Source", "Test_targetURL");
        CreateEmailVerificationCodeResponse response = umsUserService.createEmailVerificationCode(request);
        AssertJUnit.assertFalse(response.isSuccessful());
        AssertJUnit.assertNull(response.getCreateEmailVerificationCode());
        
        CreateEmailVerificationCodeRequest request1 = new CreateEmailVerificationCodeRequest("Test@snapdeal.com", "", "");
        CreateEmailVerificationCodeResponse response1 = umsUserService.createEmailVerificationCode(request1);
        AssertJUnit.assertTrue(response1.isSuccessful());
        AssertJUnit.assertNotNull(response1.getCreateEmailVerificationCode());
    }
    
    @Test
    public void getEmailVerificationCodeTest(){
        
        new NonStrictExpectations() {
            {
                userServiceInternal.getEmailVerificationCode(anyString);
                result = new EmailVerificationCode();
                
                umsConvertorService.getEmailVerificationCodeSROfromEntity((EmailVerificationCode)any);
                result=new EmailVerificationCodeSRO();
            }
        };
        
        GetEmailVerificationCodeResponse response = umsUserService.getEmailVerificationCode(new GetEmailVerificationCodeRequest("Test@snapdeal.com"));
        AssertJUnit.assertTrue(response.isSuccessful());
        AssertJUnit.assertNotNull(response.getGetEmailVerificationCode());
    }
    
    @Test
    public void getEmailVerificationCodeNullFromInternalServiceTest(){
        
        new NonStrictExpectations() {
            {
                userServiceInternal.getEmailVerificationCode(anyString);
                result = null;
                
                umsConvertorService.getEmailVerificationCodeSROfromEntity((EmailVerificationCode)any);
                result=null;
            }
        };
        
        GetEmailVerificationCodeResponse response = umsUserService.getEmailVerificationCode(new GetEmailVerificationCodeRequest("Test@snapdeal.com"));
        AssertJUnit.assertTrue(response.isSuccessful());
        AssertJUnit.assertNull(response.getGetEmailVerificationCode());
    }
    
    @Test
    public void getEmailVerificationCodeNullRequestParamTest(){
        
        new NonStrictExpectations() {
            {
                userServiceInternal.getEmailVerificationCode(anyString);
                result = new EmailVerificationCode();
                
                umsConvertorService.getEmailVerificationCodeSROfromEntity((EmailVerificationCode)any);
                result=new EmailVerificationCodeSRO();
                
            }
        };
        
        GetEmailVerificationCodeResponse response = umsUserService.getEmailVerificationCode(new GetEmailVerificationCodeRequest(null));
        AssertJUnit.assertFalse(response.isSuccessful());
        AssertJUnit.assertNull(response.getGetEmailVerificationCode());
    }
    
    @Test
    public void getEmailVerificationCodeEmptyStringRequestParamTest(){
        
        new NonStrictExpectations() {
            {
                userServiceInternal.getEmailVerificationCode(anyString);
                result = new EmailVerificationCode();
                
                umsConvertorService.getEmailVerificationCodeSROfromEntity((EmailVerificationCode)any);
                result=new EmailVerificationCodeSRO();
                
            }
        };
        
        GetEmailVerificationCodeResponse response = umsUserService.getEmailVerificationCode(new GetEmailVerificationCodeRequest(""));
        AssertJUnit.assertFalse(response.isSuccessful());
        AssertJUnit.assertNull(response.getGetEmailVerificationCode());
        
        GetEmailVerificationCodeResponse response1 = umsUserService.getEmailVerificationCode(new GetEmailVerificationCodeRequest(" "));
        AssertJUnit.assertFalse(response1.isSuccessful());
        AssertJUnit.assertNull(response1.getGetEmailVerificationCode());
    }
    
    @Test
    public void clearEmailVerificationCode(){
        new NonStrictExpectations() {
            {
                userServiceInternal.clearEmailVerificationCode(anyString);
                result = true;
            }
        };
        
        ClearEmailVerificationCodeResponse response = umsUserService.clearEmailVerificationCode(new ClearEmailVerificationCodeRequest("Test@snapdeal.com"));
        AssertJUnit.assertTrue(response.isSuccessful());
    }
    
    @Test
    public void clearEmailVerificationCodeNullRequestParam(){
        
        ClearEmailVerificationCodeResponse response = umsUserService.clearEmailVerificationCode(new ClearEmailVerificationCodeRequest(null));
        AssertJUnit.assertFalse(response.isSuccessful());
        
    }
    
    @Test
    public void clearEmailVerificationCodeEmptyStringRequestParam(){
        
        ClearEmailVerificationCodeResponse response = umsUserService.clearEmailVerificationCode(new ClearEmailVerificationCodeRequest(""));
        AssertJUnit.assertFalse(response.isSuccessful());
        
        ClearEmailVerificationCodeResponse response1 = umsUserService.clearEmailVerificationCode(new ClearEmailVerificationCodeRequest(""));
        AssertJUnit.assertFalse(response1.isSuccessful());
        
    }
    
    @SuppressWarnings("unused")
    private User getUserInstance(Integer id, String email, String password, boolean enabled, boolean emailVerified, boolean mobileVerified, String displayName, String firstName,
            String middleName, String lastName, String gender, Date birthday, String mobile, String photo, String channelCode, Integer referredBy, int referralCount,
            String mobileVerificationCode, String emailVerificationCode, Date created, Date modified, int sdCash, int sdCashEarned, String source, boolean autocreated,
            Set<UserRole> userRoles, String location, String uid, String friendUids, Integer autocreatedNotificationCount) {

        User user = new User();

        user.setId(id);
        user.setEmail(email);
        user.setPassword(password);
        user.setEnabled(enabled);
        user.setEmailVerified(emailVerified);
        user.setMobileVerified(mobileVerified);
        user.setDisplayName(displayName);
        user.setFirstName(firstName);
        user.setMiddleName(middleName);
        user.setLastName(lastName);
        user.setGender(gender);
        user.setBirthday(birthday);
        user.setMobile(mobile);
        user.setPhoto(photo);
        user.setChannelCode(channelCode);
        user.setReferredBy(referredBy);
        user.setReferralCount(referralCount);
        user.setMobileVerificationCode(mobileVerificationCode);
        user.setEmailVerificationCode(emailVerificationCode);
        user.setCreated(created);
        user.setModified(modified);
        user.setSdCash(sdCash);
        user.setSdCashEarned(sdCashEarned);
        user.setUserRoles(userRoles);
        user.setSource(source);
        user.setAutocreated(autocreated);
        user.setLocation(location);
        user.setUid(uid);
        user.setFriendUids(friendUids);
        user.setAutocreatedNotificationCount(autocreatedNotificationCount);

        return user;
    }

    @SuppressWarnings("unused")
    private UserRole getInstanceOfUserRole(Role role) {

        UserRole userRole = new UserRole();
        userRole.setRole(role);
        return userRole;
    }

    @SuppressWarnings("unused")
    private Role getInstanceOfRole(Integer id, String code, String desCription) {
        Role role = new Role();
        role.setId(id);
        role.setCode(code);
        role.setDescription(desCription);
        return role;
    }
}
