/*
 *  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 18-Oct-2012
 *  @author naveen
 */
package com.snapdeal.ums.server.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.base.cache.EmailVerificationCode;
import com.snapdeal.base.utils.StringUtils;
import com.snapdeal.base.validation.ValidationError;
import com.snapdeal.ums.cache.services.IUserCacheService;
import com.snapdeal.ums.aspect.annotation.EnableMonitoring;
import com.snapdeal.ums.constants.ErrorConstants;
import com.snapdeal.ums.constants.StringConstants;
import com.snapdeal.ums.core.entity.CustomerEmailScore;
import com.snapdeal.ums.core.entity.CustomerMobileScore;
import com.snapdeal.ums.core.entity.User;
import com.snapdeal.ums.core.entity.UserInformation;
import com.snapdeal.ums.core.entity.UserPreference;
import com.snapdeal.ums.core.entity.UserReferral;
import com.snapdeal.ums.core.entity.UserRole;
import com.snapdeal.ums.core.entity.ZendeskUser;
import com.snapdeal.ums.core.sro.user.CustomerScoreSRO;
import com.snapdeal.ums.core.sro.user.EmailVerificationCodeSRO;
import com.snapdeal.ums.core.sro.user.UserInformationSRO;
import com.snapdeal.ums.core.sro.user.UserPreferenceSRO;
import com.snapdeal.ums.core.sro.user.UserReferralSRO;
import com.snapdeal.ums.core.sro.user.UserRoleSRO;
import com.snapdeal.ums.core.sro.user.UserSRO;
import com.snapdeal.ums.exception.userAddress.UserAddressException;
import com.snapdeal.ums.ext.user.AddUserInformationRequest;
import com.snapdeal.ums.ext.user.AddUserInformationResponse;
import com.snapdeal.ums.ext.user.AddUserPreferenceRequest;
import com.snapdeal.ums.ext.user.AddUserPreferenceResponse;
import com.snapdeal.ums.ext.user.AddUserRequest;
import com.snapdeal.ums.ext.user.AddUserResponse;
import com.snapdeal.ums.ext.user.ClearEmailVerificationCodeRequest;
import com.snapdeal.ums.ext.user.ClearEmailVerificationCodeResponse;
import com.snapdeal.ums.ext.user.CreateEmailVerificationCodeRequest;
import com.snapdeal.ums.ext.user.CreateEmailVerificationCodeResponse;
import com.snapdeal.ums.ext.user.CreateOrUpdateReferralSentRequest;
import com.snapdeal.ums.ext.user.CreateOrUpdateReferralSentResponse;
import com.snapdeal.ums.ext.user.CreateSubsidieryUserRequest;
import com.snapdeal.ums.ext.user.CreateUserRequest;
import com.snapdeal.ums.ext.user.CreateUserResponse;
import com.snapdeal.ums.ext.user.CreateUserRoleRequest;
import com.snapdeal.ums.ext.user.CreateUserRoleResponse;
import com.snapdeal.ums.ext.user.CreateUserWithDetailsRequest;
import com.snapdeal.ums.ext.user.CreateUserWithDetailsResponse;
import com.snapdeal.ums.ext.user.CustomerScoreRequest;
import com.snapdeal.ums.ext.user.CustomerScoreResponse;
import com.snapdeal.ums.ext.user.DeleteUserRoleByIdRequest;
import com.snapdeal.ums.ext.user.DeleteUserRoleByIdResponse;
import com.snapdeal.ums.ext.user.GetConfirmationLinkRequest;
import com.snapdeal.ums.ext.user.GetConfirmationLinkResponse;
import com.snapdeal.ums.ext.user.GetEmailVerificationCodeRequest;
import com.snapdeal.ums.ext.user.GetEmailVerificationCodeResponse;
import com.snapdeal.ums.ext.user.GetOpenIdUserRequest;
import com.snapdeal.ums.ext.user.GetOpenIdUserResponse;
import com.snapdeal.ums.ext.user.GetOrCreateVendorUserRequest;
import com.snapdeal.ums.ext.user.GetOrCreateVendorUserResponse;
import com.snapdeal.ums.ext.user.GetReferralRequest;
import com.snapdeal.ums.ext.user.GetReferralResponse;
import com.snapdeal.ums.ext.user.GetUserByEmailRequest;
import com.snapdeal.ums.ext.user.GetUserByEmailResponse;
import com.snapdeal.ums.ext.user.GetUserByIdRequest;
import com.snapdeal.ums.ext.user.GetUserByIdResponse;
import com.snapdeal.ums.ext.user.GetUserInformationByUserAndNameRequest;
import com.snapdeal.ums.ext.user.GetUserInformationByUserAndNameResponse;
import com.snapdeal.ums.ext.user.GetUserInformationsByUserRequest;
import com.snapdeal.ums.ext.user.GetUserInformationsByUserResponse;
import com.snapdeal.ums.ext.user.GetUserPreferenceByMobileRequest;
import com.snapdeal.ums.ext.user.GetUserPreferenceByMobileResponse;
import com.snapdeal.ums.ext.user.GetZendeskUserRequest;
import com.snapdeal.ums.ext.user.GetZendeskUserResponse;
import com.snapdeal.ums.ext.user.IsMobileExistRequest;
import com.snapdeal.ums.ext.user.IsMobileExistResponse;
import com.snapdeal.ums.ext.user.IsUserExistsRequest;
import com.snapdeal.ums.ext.user.IsUserExistsResponse;
import com.snapdeal.ums.ext.user.IsVisaBenefitAvailedRequest;
import com.snapdeal.ums.ext.user.IsVisaBenefitAvailedResponse;
import com.snapdeal.ums.ext.user.PersistUserRequest;
import com.snapdeal.ums.ext.user.PersistUserResponse;
import com.snapdeal.ums.ext.user.UpdateReferralClickRequest;
import com.snapdeal.ums.ext.user.UpdateReferralClickResponse;
import com.snapdeal.ums.ext.user.UpdateUserRequest;
import com.snapdeal.ums.ext.user.UpdateUserResponse;
import com.snapdeal.ums.ext.user.VerifyUserRequest;
import com.snapdeal.ums.ext.user.VerifyUserResponse;
import com.snapdeal.ums.server.services.IUserService;
import com.snapdeal.ums.server.services.IUserServiceInternal;
import com.snapdeal.ums.server.services.convertor.IUMSConvertorService;
import com.snapdeal.ums.services.ValidationService;

@Service("umsUserService")
public class UserServiceImpl implements IUserService {

    private static final Logger  LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private IUserServiceInternal umsUserServiceInternal;

    @Autowired
    private IUMSConvertorService umsConvertorService;

    @Autowired
    private IUserCacheService userCacheService;

    @Autowired
    private ValidationService validationService;

    @Override
    public IsUserExistsResponse isUserExists(IsUserExistsRequest request) {

        IsUserExistsResponse response = new IsUserExistsResponse();
        /*
         * add request null check, validation error and request null check test-case
         */
        String email = request.getEmail();
        if (StringUtils.isNotEmpty(email)) {
            boolean exists = umsUserServiceInternal.isUserExists(email);
            response.setIsUserExists(exists);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("emtpy email provided");

        }
        return response;
    }

    /*
     * should be a private method
     */

    @EnableMonitoring
    @Override
    public AddUserResponse addUser(AddUserRequest request) {

        AddUserResponse response = new AddUserResponse();
        UserSRO userSRO = request.getUser();
        if (userSRO != null) {
            User addedUser = umsUserServiceInternal.addUser(umsConvertorService.getUserEntityFromSRO(userSRO));
            UserSRO addedUserSRO = umsConvertorService.getUserSROWithoutRolesfromEntity(addedUser);

            response.setAddUser(addedUserSRO);
            response.setSuccessful(true);

        } else {
            response.setSuccessful(false);
            response.setMessage("userSRO can not be null");

        }
        return response;
    }


    @EnableMonitoring
    @Override
    public GetUserByEmailResponse getUserByEmail(final GetUserByEmailRequest request) {


        /*
         * add request null check, validation error and request null check test-case
         * add validation error corresponding to no user found in database.
         */

        final GetUserByEmailResponse response = new GetUserByEmailResponse();
        if(request==null||request.getEmail()==null)
        {
            validationService.addValidationError(response, ErrorConstants.INVALID_REQUEST);
            response.setSuccessful(false);
        }
        else{
            String email = request.getEmail().trim().toLowerCase();

            if (StringUtils.isNotEmpty(email)) {
                LOG.info(StringConstants.GET_USER_BY_EMAIL_RQ,email);

                // Fetch SRO from cache
                UserSRO userSRO = userCacheService.getUserSROByEmail(email);

                if(userSRO == null){
                    LOG.info("Record of email " + email + " not obtained from cache. Fetching from DB");
                    final User user = umsUserServiceInternal.getUserByEmail(email);
                    userSRO = umsConvertorService.getUserSROWithRolesfromEnity(user);

                    // Save the SRO in cache
                    boolean writeToCache = userCacheService.putUserSROByEmail(email, userSRO);
                    if(!writeToCache){
                        LOG.error("Failed to write to cache for key: " + email);
                    }
                }
                response.setGetUserByEmail(userSRO);
                response.setSuccessful(true);

            } else {
                response.setSuccessful(false);
                response.setMessage("Email can not be empty");
            }

        }
        return response;
    }


    @Override
    public UpdateUserResponse updateUser(UpdateUserRequest request) {

        /*
         * add request null check, validation error and request null check test-case
         */

        UpdateUserResponse response = new UpdateUserResponse();

        if(request==null||request.getUser()==null){
            validationService.addValidationError(response, ErrorConstants.INVALID_REQUEST);
        }
        else{
            UserSRO userSRO = request.getUser();
            if (userSRO != null) {
                final String email = userSRO.getEmail().trim().toLowerCase();
                
                // Evict from cache
                boolean deleteSuccessful = userCacheService.deleteUserSROByEmail(email);
                if(!deleteSuccessful){
                    LOG.error("Unable to delete from cache for key: " + email);
                }
                
                User user = umsUserServiceInternal.getUserByEmail(email);
                umsConvertorService.updateUserEntityFromSRO(userSRO, user);
                User updatedUser = umsUserServiceInternal.updateUser(user);
                UserSRO updatedSRO = null;
                if (userSRO.getUserRoles() == null) {
                    updatedSRO = umsConvertorService.getUserSROWithoutRolesfromEntity(updatedUser);
                } else {
                    updatedSRO = umsConvertorService.getUserSROWithRolesfromEnity(updatedUser);
                }

                // Insert updated UserSRO in cache
                boolean writeToCache = userCacheService.putUserSROByEmail(email, updatedSRO);
                if(!writeToCache){
                    LOG.error("Failed to write to cache for key: " + email);
                }

                response.setUpdateUser(updatedSRO);
                response.setSuccessful(true);
            } else {
                response.setSuccessful(false);
                response.setMessage("userSRO can not be null");
            }
        }
        return response;
    }

    @EnableMonitoring
    @Override
    public VerifyUserResponse verifyUser(VerifyUserRequest request) {
        /*
         * add request null check, validation error and request null check test-case
         */
        VerifyUserResponse response = new VerifyUserResponse();
        String code = request.getCode();
        String email = request.getEmail().trim().toLowerCase();
        
        if (StringUtils.isNotEmpty(email) && StringUtils.isNotEmpty(code)) {
            
            // Evict from cache
            boolean deleteSuccessful = userCacheService.deleteUserSROByEmail(email);
            if(!deleteSuccessful){
                LOG.error("Unable to delete from cache for key: " + email);
            }
            
            boolean verified = umsUserServiceInternal.verifyUser(email, code);
            response.setVerifyUser(verified);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("email and code can not be empty");
        }

        return response;

    }

    @Override
    public GetOpenIdUserResponse getOpenIdUser(GetOpenIdUserRequest request) {
        GetOpenIdUserResponse response = new GetOpenIdUserResponse();
        UserSRO userSRO = request.getRpxUser();
        if (userSRO != null) {
            User user = umsUserServiceInternal.getOpenIdUser(umsConvertorService.getUserEntityFromSRO(userSRO));
            UserSRO openIDUserSRO = umsConvertorService.getUserSROWithoutRolesfromEntity(user);
            response.setopenIdUser(openIDUserSRO);
            response.setSuccessful(true);

        } else {
            response.setSuccessful(false);
            response.setMessage("user sro cannot be null");
        }
        return response;
    }

    @Override
    public CreateUserRoleResponse createUserRole(CreateUserRoleRequest request) {
        CreateUserRoleResponse response = new CreateUserRoleResponse();
        UserRoleSRO userRoleSRO = request.getUserRole();
        if (userRoleSRO != null) {

            UserRole role = umsUserServiceInternal.createUserRole(umsConvertorService.getUserRoleEntityfromSRO(userRoleSRO));
            if(role!=null){
                User user = role.getUser();
                String email = user.getEmail().trim().toLowerCase();
                
                // Evict user details from cache
                boolean deleteSuccessful = userCacheService.deleteUserSROByEmail(email);
                if(!deleteSuccessful){
                    LOG.error("Failed to delete from cache for the key: " + email);
                }
            }
            
            UserRoleSRO createdRoleSRO = umsConvertorService.getUserRoleSROfromEntity(role);
            response.setCreateUserRole(createdRoleSRO);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("userRolesro cannot be null");
        }
        return response;
    }

    @Override
    public GetOrCreateVendorUserResponse getOrCreateVendorUser(GetOrCreateVendorUserRequest request) {
        GetOrCreateVendorUserResponse response = new GetOrCreateVendorUserResponse();
        String email = request.getEmail();

        if (StringUtils.isNotEmpty(email)) {
            User user = umsUserServiceInternal.getOrCreateVendorUser(email);
            UserSRO sro = umsConvertorService.getUserSROWithoutRolesfromEntity(user);
            response.setVendorUser(sro);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("email cannot be empty");
        }
        return response;
    }

    @EnableMonitoring
    @Override
    public GetUserByIdResponse getUserById(final GetUserByIdRequest request) {
        /*
         * add request null check, validation error and request null check test-case
         */
        final  GetUserByIdResponse response = new GetUserByIdResponse();
        UserSRO userSRO;
        if(request==null){
            validationService.addValidationError(response, ErrorConstants.INVALID_REQUEST);
        } else {
            Integer id = request.getId();
            if (id >= 0) {
                LOG.info(StringConstants.GET_USER_BY_ID_RQ,id);

                // Fetch from cache
                userSRO = userCacheService.getUserSROById(id);
                if(userSRO == null){
                    LOG.info("Record of userId " + id + " not obtained from cache. Fetching from DB");
                    final User user = umsUserServiceInternal.getUserById(id);
                    userSRO = umsConvertorService.getUserSROWithRolesfromEnity(user);

                    // Save the SRO in cache
                    boolean writeToCache = userCacheService.putUserSROById(id, userSRO);
                    if(!writeToCache){
                        LOG.error("Failed to write to cache for key: " + id);
                    }
                }

                response.setUserById(userSRO);
                response.setSuccessful(true);
            } else {
                String invalidID = "invalid id =" + id;
                LOG.info(invalidID);
                response.setSuccessful(false);
                response.setMessage(invalidID);
            }
        }

        return response;
    }

    @Deprecated
    @Override
    public GetReferralResponse getReferral(GetReferralRequest request) {

        GetReferralResponse response = new GetReferralResponse();
        UserSRO userSRO = request.getUser();
        if (userSRO != null) {
            List<UserReferral> referrals = umsUserServiceInternal.getReferral(umsConvertorService.getUserEntityFromSRO(userSRO));
            List<UserReferralSRO> referralSROs = new ArrayList<UserReferralSRO>();
            for (UserReferral referral : referrals)
                referralSROs.add(umsConvertorService.getUserReferralSROfromEntity(referral));
            response.setReferral(referralSROs);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("userSRO is null");
        }
        return response;
    }


    @Override
    public GetUserInformationsByUserResponse getUserInformationsByUser(GetUserInformationsByUserRequest request) {
        GetUserInformationsByUserResponse response = new GetUserInformationsByUserResponse();
        UserSRO userSRO = request.getUser();
        if (userSRO != null) {
            List<UserInformation> infos = umsUserServiceInternal.getUserInformationsByUser(umsConvertorService.getUserEntityFromSRO(userSRO));
            List<UserInformationSRO> infoSROs = new ArrayList<UserInformationSRO>();
            for (UserInformation info : infos)
                infoSROs.add(umsConvertorService.getUserInformationSROfromEntity(info));
            response.setGetUserInformationsByUser(infoSROs);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("userSRO is null");
        }
        return response;
    }

    @Override
    public GetUserInformationByUserAndNameResponse getUserInformationByUserAndName(GetUserInformationByUserAndNameRequest request) {
        GetUserInformationByUserAndNameResponse response = new GetUserInformationByUserAndNameResponse();
        String name = request.getName();
        UserSRO sro = request.getUser();
        if (StringUtils.isNotEmpty(name) && (sro != null)) {
            UserInformation information = umsUserServiceInternal.getUserInformationByUserAndName(umsConvertorService.getUserEntityFromSRO(sro), name);
            response.setGetUserInformationByUserAndName(umsConvertorService.getUserInformationSROfromEntity(information));
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("name and/or user sro are empty/null");
        }
        return response;
    }

    @Deprecated
    @Override
    public AddUserInformationResponse addUserInformation(AddUserInformationRequest request) {
        AddUserInformationResponse response = new AddUserInformationResponse();
        UserInformationSRO infotoAdd = request.getInformation();
        if (infotoAdd != null) {
            umsUserServiceInternal.addUserInformation(umsConvertorService.getUserInfomationEntityFromSRO(infotoAdd));
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage(" userInformationSRO is null");
        }
        return response;
    }

    @EnableMonitoring
    @Override
    public CreateUserResponse createUser(CreateUserRequest request) {
        CreateUserResponse response = new CreateUserResponse();
        boolean autocreated = request.getAutocreated();
        String email = request.getEmail().trim().toLowerCase();
        UserRoleSRO.Role initialRole = request.getInitialRole();
        //TODO:Naveen password ??
        String password = request.getPassword();
        String source = request.getSource();
        String targetUrl = request.getTargetUrl();

        if (StringUtils.isNotEmpty(email) && StringUtils.isNotEmpty(password) && StringUtils.isNotEmpty(source) && (initialRole != null)) {
        	
        	User createdUser = null;
        	UserSRO createdUserSRO = null;
        	
        	// Not allow non-snapdeal users to be provided any other role apart from
    		// unverified or registered
        	boolean isNonSnapdealUser = !(email.endsWith("@snapdeal.com") || email.endsWith("@jasperindia.com"));
    		boolean isAuthRoleForNonSnapdealUser = initialRole.name().toLowerCase().equals(UserRoleSRO.Role.REGISTERED.role())
    				|| initialRole.name().toLowerCase().equals(UserRoleSRO.Role.UNVERIFIED.role());
    		if (isNonSnapdealUser) {
    			if (isAuthRoleForNonSnapdealUser) {
    				createdUser = umsUserServiceInternal.createUser(email, password, initialRole.name(), source, targetUrl, autocreated);
    			} else {
    				LOG.info("Attempt of authority breach supressed at createUser, role: " + initialRole.name() + " to user: "
    						+ email);
    				response.setSuccessful(false);
    	            response.setMessage("Not eligible for requested role!");
    	            return response;
    			}
    		} else {
    			createdUser = umsUserServiceInternal.createUser(email, password, initialRole.name(), source, targetUrl, autocreated);
    		}
    		
    		createdUserSRO = umsConvertorService.getUserSROWithRolesfromEnity(createdUser);
            response.setCreateUser(createdUserSRO);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage(" invalid params for create user");
        }
        return response;
    }
    
    @EnableMonitoring
    @Override
    public CreateUserResponse createSubsidieryUser(CreateSubsidieryUserRequest request) {
        CreateUserResponse response = new CreateUserResponse();
        boolean autocreated = request.getAutocreated();
        String email = request.getEmail().trim().toLowerCase();
        UserRoleSRO.Role initialRole = request.getInitialRole();
        //TODO:Naveen password ??
        String password = request.getPassword();
        String source = request.getSource();
        String targetUrl = request.getTargetUrl();
        Boolean isEmaileVerified = request.isEmailVarified();
        String emailVerificationCode = request.getEmailVerificationCode();

        if (StringUtils.isNotEmpty(email) && StringUtils.isNotEmpty(password) 
        		&& StringUtils.isNotEmpty(source) && (initialRole != null)
        		&& isEmaileVerified!=null && StringUtils.isNotEmpty(emailVerificationCode)) {
        	
        	User createdUser = null;
        	
        	// Not allow non-snapdeal users to be provided any other role apart from
    		// unverified or registered
        	boolean isNonSnapdealUser = !(email.endsWith("@snapdeal.com") || email.endsWith("@jasperindia.com"));
    		boolean isAuthRoleForNonSnapdealUser = initialRole.name().toLowerCase().equals(UserRoleSRO.Role.REGISTERED.role())
    				|| initialRole.name().toLowerCase().equals(UserRoleSRO.Role.UNVERIFIED.role());
    		if (isNonSnapdealUser) {
    			if (isAuthRoleForNonSnapdealUser) {
    				createdUser = umsUserServiceInternal.createSubsidieryUser(email, password, initialRole.name(), source, targetUrl, autocreated,
    	            		isEmaileVerified,emailVerificationCode);
    			} else {
    				LOG.info("Attempt of authority breach supressed at createSubsidieryUser, role: " + initialRole.name() + " to user: "
    						+ email);
    				response.setSuccessful(false);
    	            response.setMessage("Not eligible for requested role!");
    	            return response;
    			}
    		} else {
    			createdUser = umsUserServiceInternal.createSubsidieryUser(email, password, initialRole.name(), source, targetUrl, autocreated,
                		isEmaileVerified,emailVerificationCode);
    		}
    		
            UserSRO createdUserSRO = umsConvertorService.getUserSROWithRolesfromEnity(createdUser);
            response.setCreateUser(createdUserSRO);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage(" invalid params for create user");
        }
        return response;
    }

    @Override
    public CreateEmailVerificationCodeResponse createEmailVerificationCode(CreateEmailVerificationCodeRequest request) {
        /*
         * add request null check, validation error and request null check test-case
         */
        CreateEmailVerificationCodeResponse response = new CreateEmailVerificationCodeResponse();
        String email = request.getEmail();
        String source = request.getSource();
        String targetURL = request.getTargetUrl();
        if (StringUtils.isNotEmpty(email)) {
            EmailVerificationCode emailVerificationCode = umsUserServiceInternal.createEmailVerificationCode(email, source, targetURL);
            EmailVerificationCodeSRO emailVerificationCodeSRO = umsConvertorService.getEmailVerificationCodeSROfromEntity(emailVerificationCode);
            response.setEmailVerificationCode(emailVerificationCodeSRO);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage(" invalid params for createEmailVerificationCode ");
        }
        return response;
    }


    @Override
    public GetEmailVerificationCodeResponse getEmailVerificationCode(GetEmailVerificationCodeRequest request) {
        /*
         * add request null check, validation error and request null check test-case
         */
        GetEmailVerificationCodeResponse response = new GetEmailVerificationCodeResponse();
        String email = request.getEmail();
        if (StringUtils.isNotEmpty(email)) {
            EmailVerificationCode emailVerificationCode = umsUserServiceInternal.getEmailVerificationCode(email);
            EmailVerificationCodeSRO emailVerificationCodeSRO = umsConvertorService.getEmailVerificationCodeSROfromEntity(emailVerificationCode);
            response.setEmailVerificationCode(emailVerificationCodeSRO);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage(" invalid params for getEmailVerificationCode ");
        }
        return response;
    }

    @Override
    public ClearEmailVerificationCodeResponse clearEmailVerificationCode(ClearEmailVerificationCodeRequest request) {
        /*
         * add request null check, validation error and request null check test-case
         */
        ClearEmailVerificationCodeResponse response = new ClearEmailVerificationCodeResponse();
        String email = request.getEmail();
        if (StringUtils.isNotEmpty(email)) {
            umsUserServiceInternal.clearEmailVerificationCode(email);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage(" empty email provided ");
        }
        return response;
    }

    @Deprecated
    @Override
    public GetZendeskUserResponse getZendeskUser(GetZendeskUserRequest request) {

        GetZendeskUserResponse response = new GetZendeskUserResponse();
        Integer userId = request.getUserId();
        if (userId > 0) {
            ZendeskUser user = umsUserServiceInternal.getZendeskUser(userId);

            response.setGetZendeskUser(umsConvertorService.getZendeskUserSROfromEntity(user));
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage(" invalid user Id");
        }
        return response;
    }


    @Override
    public PersistUserResponse persistUser(PersistUserRequest request) {
        PersistUserResponse response = new PersistUserResponse();
        UserSRO userSRO = request.getUser();
        if (userSRO != null) {
            final String email = userSRO.getEmail().trim().toLowerCase();
            
            // Evict from cache
            boolean deleteSuccessful = userCacheService.deleteUserSROByEmail(email);
            if(!deleteSuccessful){
                LOG.error("Failed to delete from cache for the key: " + email);
            }

            umsUserServiceInternal.persistUser(umsConvertorService.getUserEntityFromSRO(userSRO));

            // write updated userSRO to cache
            boolean writeToCache = userCacheService.putUserSROByEmail(email, userSRO);
            if(!writeToCache){
                LOG.error("Failed to write to cache for the key: " + email);
            }

            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage(" invalid user Id");
        }
        return response;
    }

    @Deprecated
    @Override
    public GetUserPreferenceByMobileResponse getUserPreferenceByMobile(GetUserPreferenceByMobileRequest request) {
        GetUserPreferenceByMobileResponse response = new GetUserPreferenceByMobileResponse();
        String phoneNo = request.getPhoneNo();
        if (StringUtils.isNotEmpty(phoneNo)) {
            response.setGetUserPreferenceByMobile(umsConvertorService.getUserPreferenceSROFromEntity(umsUserServiceInternal.getUserPreferenceByMobile(phoneNo)));
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request : phoneNo =" + phoneNo);
        }
        return response;
    }

    @Deprecated
    @Override
    public AddUserPreferenceResponse addUserPreference(AddUserPreferenceRequest request) {
        AddUserPreferenceResponse response = new AddUserPreferenceResponse();
        UserPreferenceSRO sro = request.getUserPreference();
        if ((sro != null) && (sro.getPhoneNo() != null)) {
            UserPreference pref = umsUserServiceInternal.addUserPreference(umsConvertorService.getUserPreferenceEntityFromSRO(sro));
            response.setAddUserPreference(umsConvertorService.getUserPreferenceSROFromEntity(pref));
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request : UserPreferenceSRO =" + sro);
        }
        return response;
    }

    @Override
    public GetConfirmationLinkResponse getConfirmationLink(GetConfirmationLinkRequest request) {
        GetConfirmationLinkResponse response = new GetConfirmationLinkResponse();
        String email = request.getEmail();
        if (StringUtils.isNotEmpty(email)) {
            response.setConfirmationLink(umsUserServiceInternal.getConfirmationLink(email));
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request : email =" + email);
        }
        return response;
    }

    @Deprecated
    @Override
    public GetUserByIdResponse getUserByIdWithoutRoles(GetUserByIdRequest request) {
        GetUserByIdResponse response = new GetUserByIdResponse();
        Integer id = request.getId();
        if (id >= 0) {
            User user = umsUserServiceInternal.getUserByIdWithoutRoles(id);
            UserSRO sro = umsConvertorService.getUserSROWithoutRolesfromEntity(user);
            response.setUserById(sro);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("invalid id =" + id);
        }
        return response;
    }

    @Deprecated
    @Override
    public GetUserByIdResponse getUserByIdWithRoles(GetUserByIdRequest request) {
        GetUserByIdResponse response = new GetUserByIdResponse();
        Integer id = request.getId();
        UserSRO userSRO;
        if (id >= 0) {
            // Fetch from cache
            userSRO = userCacheService.getUserSROById(id);
            if(userSRO == null){
                LOG.info("Record of userId " + id + " not obtained from cache. Fetching from DB");
                final User user = umsUserServiceInternal.getUserByIdWithRoles(id);
                userSRO = umsConvertorService.getUserSROWithRolesfromEnity(user);

                // Save the SRO in cache
                boolean writeToCache = userCacheService.putUserSROById(id, userSRO);
                if(!writeToCache){
                    LOG.error("Failed to write to cache for the key: " + id);
                }
            }
            response.setUserById(userSRO);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("invalid id =" + id);
        }
        return response;
    }

    @Deprecated
    @Override
    public GetUserByEmailResponse getUserByEmailWithRoles(GetUserByEmailRequest request) {

        GetUserByEmailResponse response = new GetUserByEmailResponse();
        String email = request.getEmail().trim().toLowerCase();
        UserSRO userSRO;
        if (StringUtils.isNotEmpty(email)) {
            userSRO = userCacheService.getUserSROByEmail(email);
            if(userSRO == null){
                LOG.info("Record of email " + email + " not obtained from cache. Fetching from DB");
                User user = umsUserServiceInternal.getUserByEmailWithRoles(email);
                userSRO = umsConvertorService.getUserSROWithRolesfromEnity(user);

                // Save the SRO in cache
                boolean writeToCache = userCacheService.putUserSROByEmail(email, userSRO);
                if(!writeToCache){
                    LOG.error("Failed to write to cache for the key: " + email);
                }
            }

            response.setGetUserByEmail(userSRO);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("email can not be empty");

        }
        return response;
    }

    @Deprecated
    @Override
    public CustomerScoreResponse getCustomerScoreByEmail(CustomerScoreRequest request) {
        CustomerScoreResponse response = new CustomerScoreResponse();
        String email = request.getSearchString();
        if (StringUtils.isNotEmpty(email)) {
            CustomerEmailScore customerEmailScore = umsUserServiceInternal.getCustomerScoreByEmail(email);
            CustomerScoreSRO customerScoreSRO = umsConvertorService.getCustomerEmailScoreSROFromEntity(customerEmailScore);
            response.setCustomerScoreSRO(customerScoreSRO);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("email can not be empty");
        }
        return response;
    }

    @Deprecated
    @Override
    public CustomerScoreResponse getCustomerScoreByMobile(CustomerScoreRequest request) {
        CustomerScoreResponse response = new CustomerScoreResponse();
        String mobile = request.getSearchString();
        if (StringUtils.isNotEmpty(mobile)) {
            CustomerMobileScore customerMobileScore = umsUserServiceInternal.getCustomerScoreByMobile(mobile);
            CustomerScoreSRO customerScoreSRO = umsConvertorService.getCustomerMobileScoreSROFromEntity(customerMobileScore);
            response.setCustomerScoreSRO(customerScoreSRO);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("mobile can not be empty");
        }
        return response;
    }

    @Deprecated
    @Override
    public CustomerScoreResponse mergeCustomerEmailScore(CustomerScoreRequest request) {
        CustomerScoreResponse response = new CustomerScoreResponse();
        if (request.getCustomerScoreSRO() != null) {
            CustomerEmailScore customerEmailScore = umsConvertorService.getCustomerEmailScoreEntityFromSRO(request.getCustomerScoreSRO());
            CustomerScoreSRO customerScoreSRO = umsConvertorService.getCustomerEmailScoreSROFromEntity(umsUserServiceInternal.mergeCustomerEmailScore(customerEmailScore));
            response.setCustomerScoreSRO(customerScoreSRO);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("CustomerEmailScore can not be null");
        }
        return response;
    }

    @Deprecated
    @Override
    public CustomerScoreResponse mergeCustomerMobileScore(CustomerScoreRequest request) {
        CustomerScoreResponse response = new CustomerScoreResponse();
        if (request.getCustomerScoreSRO() != null) {
            CustomerMobileScore customerMobileScore = umsConvertorService.getCustomerMobileScoreEntityFromSRO(request.getCustomerScoreSRO());
            CustomerScoreSRO customerScoreSRO = umsConvertorService.getCustomerMobileScoreSROFromEntity(umsUserServiceInternal.mergeCustomerMobileScore(customerMobileScore));
            response.setCustomerScoreSRO(customerScoreSRO);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("CustomerMobileScore can not be null");
        }
        return response;
    }

    public CreateOrUpdateReferralSentResponse createOrUpdateReferralSent(CreateOrUpdateReferralSentRequest request) {
        CreateOrUpdateReferralSentResponse response = new CreateOrUpdateReferralSentResponse();
        if (request.getUser() != null && request.getReferralChannel() != null) {
            umsUserServiceInternal.createOrUpdateReferralSent(umsConvertorService.getUserEntityFromSRO(request.getUser()), request.getReferralChannel(), request.getSentCount());
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("CustomerMobileScore can not be null");
        }
        return response;
    }

    @Deprecated
    @Override
    public UpdateReferralClickResponse updateReferralClick(UpdateReferralClickRequest request) {
        UpdateReferralClickResponse response = new UpdateReferralClickResponse();
        if (request.getUser() != null && request.getReferralChannel() != null) {
            umsUserServiceInternal.updateReferralClick(umsConvertorService.getUserEntityFromSRO(request.getUser()), request.getReferralChannel());
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("CustomerMobileScore can not be null");
        }
        return response;
    }

    @Deprecated
    @Override
    public IsVisaBenefitAvailedResponse isVisaBenefitAvailed(IsVisaBenefitAvailedRequest request) {
        IsVisaBenefitAvailedResponse response = new IsVisaBenefitAvailedResponse();
        if (request.getCardNumber() != null) {
            response.setVisaBenefitAvailed(umsUserServiceInternal.isVisaBenefitAvailed(request.getUserId(), request.getCardNumber()));
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("CustomerMobileScore can not be null");
        }
        return response;
    }

    @Override
    public DeleteUserRoleByIdResponse deleteUserRoleById(DeleteUserRoleByIdRequest request) {

        DeleteUserRoleByIdResponse response = new DeleteUserRoleByIdResponse();
        Integer userRoleId = request.getUserRoleId();
        if (userRoleId != null && userRoleId > 0) {

            // Fetch user by userRoleId
            UserRole userRole = umsUserServiceInternal.getUserRoleById(userRoleId);
            if(userRole != null){
                User user = userRole.getUser();
                String email = user.getEmail().trim().toLowerCase();

                // Delete data from DB
                umsUserServiceInternal.deleteUserRoleById(userRoleId);

                // Evict user details from cache
                boolean deleteSuccessful = userCacheService.deleteUserSROByEmail(email);
                if(!deleteSuccessful){
                    LOG.error("Failed to delete from cache for the key: " + email);
                }
            }            
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("UserRoleId can not be null");
        }
        return response;
    }

    @Override
    public IsMobileExistResponse isMobileExist(IsMobileExistRequest request) {

        IsMobileExistResponse response = new IsMobileExistResponse();
        if (request != null && StringUtils.isNotEmpty(request.getMobile())) {
            response.setIsMobileExist(umsUserServiceInternal.isMobileExist(request.getMobile()));
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("InValid isMobileExistRequest");
            LOG.info("Invalid IsMobileExistRequest");
        }
        return response;
    }

    @Override
    public String getUserEmailById(Integer id){
        String email = null;
        if(id!=null){
            email = userCacheService.getEmailByIdMapping(id);
            if(email==null){
                email = umsUserServiceInternal.getUserEmailById(id);
                boolean writeToCache = userCacheService.putEmailByIdMapping(id, email);
                if(!writeToCache){
                    LOG.error("Failed to write to cache for the key: " + id);
                }
            }

        }
        return email;
    }

    @Override
    public Integer getUserIdByEmail(String email){
        if(email!=null){
            return umsUserServiceInternal.getUserIdByEmail(email);
        }
        return null;
    }

    @Override
    public GetUserByEmailResponse getUserByEmailWithoutJoin(
            GetUserByEmailRequest request) {

        GetUserByEmailResponse response = new GetUserByEmailResponse();
        String email = request.getEmail();
        if (StringUtils.isNotEmpty(email)&&email!=null) {
            User user = umsUserServiceInternal.getUserByEmailWithoutJoin(email);
            UserSRO userSRO = umsConvertorService.getUserSROWithRolesfromEnity(user);
            response.setGetUserByEmail(userSRO);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("email can not be empty");

        }

        return response;
    }

	@Override
	public CreateUserWithDetailsResponse createUserWithDetails(CreateUserWithDetailsRequest request) {

		CreateUserWithDetailsResponse response = new CreateUserWithDetailsResponse();

		if (request != null && request.getUserWithPlainPassword() != null
				&& StringUtils.isNotEmpty(request.getUserWithPlainPassword().getEmail())
				&& StringUtils.isNotEmpty(request.getUserWithPlainPassword().getPassword())
				&& StringUtils.isNotEmpty(request.getSource()) && (request.getInitialRole() != null)) {

			String email = request.getUserWithPlainPassword().getEmail().trim().toLowerCase();
			// check if user already exists
			if (!umsUserServiceInternal.isUserExists(email)) {

				try {
					User createdUser = null;
					// Not allow non-snapdeal users to be provided any other
					// role apart from
					// unverified or registered
					boolean isNonSnapdealUser = !(email.endsWith("@snapdeal.com")
							|| email.endsWith("@jasperindia.com"));
					boolean isAuthRoleForNonSnapdealUser = request.getInitialRole().name().toLowerCase()
							.equals(UserRoleSRO.Role.REGISTERED.role())
							|| request.getInitialRole().name().toLowerCase().equals(UserRoleSRO.Role.UNVERIFIED.role());
					if (isNonSnapdealUser) {
						if (isAuthRoleForNonSnapdealUser) {
							createdUser = umsUserServiceInternal.createUserWithDetails(
									request.getUserWithPlainPassword(), request.getInitialRole().name(),
									request.getSource(), request.getTargetUrl(), request.isAutocreated());
						} else {
							LOG.info("Attempt of authority breach supressed at createUserWithDetails, role: "
									+ request.getInitialRole().name() + " to user: " + email);
							response.setSuccessful(false);
							response.setMessage("Not eligible for requested role!");
							return response;
						}
					} else {
						createdUser = umsUserServiceInternal.createUserWithDetails(request.getUserWithPlainPassword(),
								request.getInitialRole().name(), request.getSource(), request.getTargetUrl(),
								request.isAutocreated());
					}

					UserSRO createdUserSRO = umsConvertorService.getUserSROWithRolesfromEnity(createdUser);

					response.setVerificationCode(umsUserServiceInternal.getEmailVerificationCode(email));
					response.setSavedUser(createdUserSRO);
					response.setSuccessful(true);
				} catch (Exception e) {
					response.setSuccessful(false);
					response.addValidationError(new ValidationError(ErrorConstants.UNEXPECTED_ERROR.getCode(),
							ErrorConstants.UNEXPECTED_ERROR.getMsg()));
					LOG.error("UMS Internal Server Error", e);
				}

			} else {
				response.setSuccessful(false);
				response.setMessage("User already exists!");
				response.addValidationError(new ValidationError(ErrorConstants.USER_ALREADY_EXISTS.getCode(),
						ErrorConstants.USER_ALREADY_EXISTS.getMsg()));
			}
		} else {
			response.setSuccessful(false);
			response.addValidationError(
					new ValidationError(ErrorConstants.BAD_REQUEST.getCode(), ErrorConstants.BAD_REQUEST.getMsg()));
		}
		return response;
	}
}
