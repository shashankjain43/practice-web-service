package com.snapdeal.ums.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.ums.dao.users.impl.UsersDaoImpl;
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
import com.snapdeal.ums.server.services.convertor.IUserBrandPreferenceService;

@Controller
@RequestMapping("/service/ums/user/")
public class UserServiceController {

	private static final Logger  LOG = LoggerFactory.getLogger(UserServiceController.class);

    @Autowired
    private IUserService userService;
    
    @Autowired
    private IUserBrandPreferenceService userBrandService;

    @RequestMapping(value = "isUserExists", produces = "application/sd-service")
    @ResponseBody
    public IsUserExistsResponse isUserExists(@RequestBody IsUserExistsRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        IsUserExistsResponse response = userService.isUserExists(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "addUser", produces = "application/sd-service")
    @ResponseBody
    public AddUserResponse addUser(@RequestBody AddUserRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        AddUserResponse response = userService.addUser(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getUserByEmail", produces = "application/sd-service")
    @ResponseBody

    public GetUserByEmailResponse getUserByEmail(@RequestBody GetUserByEmailRequest request, HttpServletRequest httpServletRequest  , 
            HttpServletResponse httpServletResponse ) throws TransportException {
        GetUserByEmailResponse response = userService.getUserByEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getUserByEmailWithRoles", produces = "application/sd-service")
    @ResponseBody
    public GetUserByEmailResponse getUserByEmailWithRoles(@RequestBody GetUserByEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetUserByEmailResponse response = userService.getUserByEmailWithRoles(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "updateUser", produces = "application/sd-service")
    @ResponseBody
    public UpdateUserResponse updateUser(@RequestBody UpdateUserRequest request, HttpServletRequest httpServletRequest  , 
            HttpServletResponse httpServletResponse) throws TransportException {
        UpdateUserResponse response = userService.updateUser(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "verifyUser", produces = "application/sd-service")
    @ResponseBody
    public VerifyUserResponse verifyUser(@RequestBody VerifyUserRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        VerifyUserResponse response = userService.verifyUser(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getOpenIdUser", produces = "application/sd-service")
    @ResponseBody
    public GetOpenIdUserResponse getOpenIdUser(@RequestBody GetOpenIdUserRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetOpenIdUserResponse response = userService.getOpenIdUser(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "createUserRole", produces = "application/sd-service")
    @ResponseBody
    public CreateUserRoleResponse createUserRole(@RequestBody CreateUserRoleRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        CreateUserRoleResponse response = userService.createUserRole(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "deleteUserRoleById", produces = "application/sd-service")
    @ResponseBody
    public DeleteUserRoleByIdResponse deleteUserRoleById(@RequestBody DeleteUserRoleByIdRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        DeleteUserRoleByIdResponse response = userService.deleteUserRoleById(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getOrCreateVendorUser", produces = "application/sd-service")
    @ResponseBody
    public GetOrCreateVendorUserResponse getOrCreateVendorUser(@RequestBody GetOrCreateVendorUserRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetOrCreateVendorUserResponse response = userService.getOrCreateVendorUser(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getUserById", produces = "application/sd-service")
    @ResponseBody
    public GetUserByIdResponse getUserById(@RequestBody GetUserByIdRequest request, HttpServletRequest httpServletRequest  , 
            HttpServletResponse httpServletResponse) throws TransportException {
        GetUserByIdResponse response = userService.getUserById(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getUserByIdWithRoles", produces = "application/sd-service")
    @ResponseBody
    public GetUserByIdResponse getUserByIdWithRoles(@RequestBody GetUserByIdRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetUserByIdResponse response = userService.getUserByIdWithRoles(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    /*    @RequestMapping(value = "createRoleZoneMap", produces = "application/sd-service")
        @ResponseBody
        public CreateRoleZoneMapResponse createRoleZoneMap(@RequestBody CreateRoleZoneMapRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
            CreateRoleZoneMapResponse response = userService.createRoleZoneMap(request);
            response.setProtocol(request.getResponseProtocol());
            return response;
        }*/

    @RequestMapping(value = "getReferral", produces = "application/sd-service")
    @ResponseBody
    public GetReferralResponse getReferral(@RequestBody GetReferralRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetReferralResponse response = userService.getReferral(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getUserInformationsByUser", produces = "application/sd-service")
    @ResponseBody
    public GetUserInformationsByUserResponse getUserInformationsByUser(@RequestBody GetUserInformationsByUserRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetUserInformationsByUserResponse response = userService.getUserInformationsByUser(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getUserInformationByUserAndName", produces = "application/sd-service")
    @ResponseBody
    public GetUserInformationByUserAndNameResponse getUserInformationByUserAndName(@RequestBody GetUserInformationByUserAndNameRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetUserInformationByUserAndNameResponse response = userService.getUserInformationByUserAndName(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "addUserInformation", produces = "application/sd-service")
    @ResponseBody
    public AddUserInformationResponse addUserInformation(@RequestBody AddUserInformationRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        AddUserInformationResponse response = userService.addUserInformation(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "createUser", produces = "application/sd-service")
    @ResponseBody
    public CreateUserResponse createUser(@RequestBody CreateUserRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        CreateUserResponse response = userService.createUser(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "createEmailVerificationCode", produces = "application/sd-service")
    @ResponseBody
    public CreateEmailVerificationCodeResponse createEmailVerificationCode(@RequestBody CreateEmailVerificationCodeRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        CreateEmailVerificationCodeResponse response = userService.createEmailVerificationCode(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getEmailVerificationCode", produces = "application/sd-service")
    @ResponseBody
    public GetEmailVerificationCodeResponse getEmailVerificationCode(@RequestBody GetEmailVerificationCodeRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetEmailVerificationCodeResponse response = userService.getEmailVerificationCode(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "clearEmailVerificationCode", produces = "application/sd-service")
    @ResponseBody
    public ClearEmailVerificationCodeResponse clearEmailVerificationCode(@RequestBody ClearEmailVerificationCodeRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        ClearEmailVerificationCodeResponse response = userService.clearEmailVerificationCode(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    /*
        @RequestMapping(value = "getUsersByRoleAndZone", produces = "application/sd-service")
        @ResponseBody
        public GetUsersByRoleAndZoneResponse getUsersByRoleAndZone(@RequestBody GetUsersByRoleAndZoneRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
            GetUsersByRoleAndZoneResponse response = userService.getUsersByRoleAndZone(request);
            response.setProtocol(request.getResponseProtocol());
            return response;
        }*/

    //    @RequestMapping(value = "getZonesForUserRoles", produces = "application/sd-service")
    //    @ResponseBody
    //    public GetZonesForUserRolesResponse getZonesForUserRoles(@RequestBody GetZonesForUserRolesRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
    //        GetZonesForUserRolesResponse response = userService.getZonesForUserRoles(request);
    //        response.setProtocol(request.getResponseProtocol());
    //        return response;
    //    }

    @RequestMapping(value = "getZendeskUser", produces = "application/sd-service")
    @ResponseBody
    public GetZendeskUserResponse getZendeskUser(@RequestBody GetZendeskUserRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetZendeskUserResponse response = userService.getZendeskUser(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "persistUser", produces = "application/sd-service")
    @ResponseBody
    public PersistUserResponse persistUser(@RequestBody PersistUserRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        PersistUserResponse response = userService.persistUser(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getUserPreferenceByMobile", produces = "application/sd-service")
    @ResponseBody
    public GetUserPreferenceByMobileResponse getUserPreferenceByMobile(@RequestBody GetUserPreferenceByMobileRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetUserPreferenceByMobileResponse response = userService.getUserPreferenceByMobile(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "addUserPreference", produces = "application/sd-service")
    @ResponseBody
    public AddUserPreferenceResponse addUserPreference(@RequestBody AddUserPreferenceRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        AddUserPreferenceResponse response = userService.addUserPreference(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getConfirmationLink", produces = "application/sd-service")
    @ResponseBody
    public GetConfirmationLinkResponse getConfirmationLink(@RequestBody GetConfirmationLinkRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetConfirmationLinkResponse response = userService.getConfirmationLink(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getUserByIdWithoutRoles", produces = "application/sd-service")
    @ResponseBody
    public GetUserByIdResponse getUserByIdWithoutRoles(@RequestBody GetUserByIdRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetUserByIdResponse response = userService.getUserByIdWithoutRoles(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "addCustomerEmailScore", produces = "application/sd-service")
    @ResponseBody
    public CustomerScoreResponse mergeCustomerEmailScore(@RequestBody CustomerScoreRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        CustomerScoreResponse response = userService.mergeCustomerEmailScore(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "addCustomerMobileScore", produces = "application/sd-service")
    @ResponseBody
    public CustomerScoreResponse mergeCustomerMobileScore(@RequestBody CustomerScoreRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        CustomerScoreResponse response = userService.mergeCustomerMobileScore(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getCustomerEmailScore", produces = "application/sd-service")
    @ResponseBody
    public CustomerScoreResponse getCustomerScoreByEmail(@RequestBody CustomerScoreRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        CustomerScoreResponse response = userService.getCustomerScoreByEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getCustomerMobileScore", produces = "application/sd-service")
    @ResponseBody
    public CustomerScoreResponse getCustomerScoreByMobile(@RequestBody CustomerScoreRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        CustomerScoreResponse response = userService.getCustomerScoreByMobile(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "createOrUpdateReferralSent", produces = "application/sd-service")
    @ResponseBody
    public CreateOrUpdateReferralSentResponse createOrUpdateReferralSent(@RequestBody CreateOrUpdateReferralSentRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        CreateOrUpdateReferralSentResponse response = userService.createOrUpdateReferralSent(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "updateReferralClick", produces = "application/sd-service")
    @ResponseBody
    public UpdateReferralClickResponse createOrUpdateReferralSent(@RequestBody UpdateReferralClickRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        UpdateReferralClickResponse response = userService.updateReferralClick(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "isVisaBenefitAvailed", produces = "application/sd-service")
    @ResponseBody
    public IsVisaBenefitAvailedResponse isVisaBenefitAvailed(@RequestBody IsVisaBenefitAvailedRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        IsVisaBenefitAvailedResponse response = userService.isVisaBenefitAvailed(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "isMobileExist", produces = "application/sd-service")
    @ResponseBody
    public IsMobileExistResponse isMobileExist(@RequestBody IsMobileExistRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        IsMobileExistResponse response = new IsMobileExistResponse();
        try {
            response = userService.isMobileExist(request);
        } catch (Exception exp) {
            response.setSuccessful(false);
            response.setMessage(exp.getMessage());
            Log.error("UMS Internal Server error" + exp);
        }
        response.setProtocol(request.getResponseProtocol());
        return response;

    }
    
    @RequestMapping(value = "createUserWithDetails", produces = "application/sd-service")
    @ResponseBody
    public CreateUserWithDetailsResponse createUserWithDetails(@RequestBody CreateUserWithDetailsRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
    	CreateUserWithDetailsResponse response = userService.createUserWithDetails(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }
}

