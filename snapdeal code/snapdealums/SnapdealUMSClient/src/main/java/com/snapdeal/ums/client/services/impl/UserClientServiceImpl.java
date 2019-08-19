package com.snapdeal.ums.client.services.impl;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.base.exception.TransportException.TransportErrorCode;
import com.snapdeal.base.transport.service.ITransportService;
import com.snapdeal.ums.client.services.IUMSClientService;
import com.snapdeal.ums.client.services.IUserClientService;
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
import com.snapdeal.ums.ext.user.CreditSDCashRequest;
import com.snapdeal.ums.ext.user.CreditSDCashResponse;
import com.snapdeal.ums.ext.user.CreditSDCashToUserAccountRequest;
import com.snapdeal.ums.ext.user.CreditSDCashToUserAccountResponse;
import com.snapdeal.ums.ext.user.CustomerScoreRequest;
import com.snapdeal.ums.ext.user.CustomerScoreResponse;
import com.snapdeal.ums.ext.user.DebitSdCashRequest;
import com.snapdeal.ums.ext.user.DebitSdCashResponse;
import com.snapdeal.ums.ext.user.DeleteUserRoleByIdRequest;
import com.snapdeal.ums.ext.user.DeleteUserRoleByIdResponse;
import com.snapdeal.ums.ext.user.GetAllUsersFromSDWalletHistoryRequest;
import com.snapdeal.ums.ext.user.GetAllUsersFromSDWalletHistoryResponse;
import com.snapdeal.ums.ext.user.GetConfirmationLinkRequest;
import com.snapdeal.ums.ext.user.GetConfirmationLinkResponse;
import com.snapdeal.ums.ext.user.GetEmailVerificationCodeRequest;
import com.snapdeal.ums.ext.user.GetEmailVerificationCodeResponse;
import com.snapdeal.ums.ext.user.GetLastCreatedTimestampFromSDCashHistoryRequest;
import com.snapdeal.ums.ext.user.GetLastCreatedTimestampFromSDCashHistoryResponse;
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
import com.snapdeal.ums.ext.user.GetUsersByRoleAndZoneRequest;
import com.snapdeal.ums.ext.user.GetUsersByRoleAndZoneResponse;
import com.snapdeal.ums.ext.user.GetZendeskUserRequest;
import com.snapdeal.ums.ext.user.GetZendeskUserResponse;
import com.snapdeal.ums.ext.user.IsMobileExistRequest;
import com.snapdeal.ums.ext.user.IsMobileExistResponse;
import com.snapdeal.ums.ext.user.IsUserActivityPresentInHistoryRequest;
import com.snapdeal.ums.ext.user.IsUserActivityPresentInHistoryResponse;
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

@Service("UserClientService")
public class UserClientServiceImpl implements IUserClientService {

    private final static String CLIENT_SERVICE_URL = "/user";
    private String              webServiceURL;
    @Autowired
    private IUMSClientService   umsClientService;
    @Autowired
    private ITransportService   transportService;
    private final static Logger LOG                = (org.slf4j.LoggerFactory.getLogger(UserClientServiceImpl.class));

    @PostConstruct
    public void init() {
        transportService.registerService("/service/ums/user/", "umsserver.");
    }

    private String getWebServiceURL() throws TransportException {
        if (umsClientService.getWebServiceBaseURL() == null) {
            throw new TransportException(TransportErrorCode.BASE_URL_MISSING_EXCEPTION);
        }
        webServiceURL = umsClientService.getWebServiceBaseURL() + CLIENT_SERVICE_URL;
        return webServiceURL;
    }

    public IsUserExistsResponse isUserExists(IsUserExistsRequest request)

    {
        IsUserExistsResponse response = new IsUserExistsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/isUserExists";
            response = (IsUserExistsResponse) transportService.executeRequest(url, request, null, IsUserExistsResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public AddUserResponse addUser(AddUserRequest request)

    {
        AddUserResponse response = new AddUserResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/addUser";
            response = (AddUserResponse) transportService.executeRequest(url, request, null, AddUserResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    public GetUserByEmailResponse getUserByEmail(GetUserByEmailRequest request)

    {
        GetUserByEmailResponse response = new GetUserByEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getUserByEmail";
            response = (GetUserByEmailResponse) transportService.executeRequest(url, request, null, GetUserByEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    public UpdateUserResponse updateUser(UpdateUserRequest request)

    {
        UpdateUserResponse response = new UpdateUserResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/updateUser";
            response = (UpdateUserResponse) transportService.executeRequest(url, request, null, UpdateUserResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    public VerifyUserResponse verifyUser(VerifyUserRequest request)

    {
        VerifyUserResponse response = new VerifyUserResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/verifyUser";
            response = (VerifyUserResponse) transportService.executeRequest(url, request, null, VerifyUserResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    public GetOpenIdUserResponse getOpenIdUser(GetOpenIdUserRequest request)

    {
        GetOpenIdUserResponse response = new GetOpenIdUserResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getOpenIdUser";
            response = (GetOpenIdUserResponse) transportService.executeRequest(url, request, null, GetOpenIdUserResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    public CreateUserRoleResponse createUserRole(CreateUserRoleRequest request)

    {
        CreateUserRoleResponse response = new CreateUserRoleResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/createUserRole";
            response = (CreateUserRoleResponse) transportService.executeRequest(url, request, null, CreateUserRoleResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    public DeleteUserRoleByIdResponse deleteUserRoleById(DeleteUserRoleByIdRequest request) {
        DeleteUserRoleByIdResponse response = new DeleteUserRoleByIdResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/deleteUserRoleById";
            response = (DeleteUserRoleByIdResponse) transportService.executeRequest(url, request, null, DeleteUserRoleByIdResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    public GetOrCreateVendorUserResponse getOrCreateVendorUser(GetOrCreateVendorUserRequest request)

    {
        GetOrCreateVendorUserResponse response = new GetOrCreateVendorUserResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getOrCreateVendorUser";
            response = (GetOrCreateVendorUserResponse) transportService.executeRequest(url, request, null, GetOrCreateVendorUserResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    public GetUserByIdResponse getUserById(GetUserByIdRequest request)

    {
        GetUserByIdResponse response = new GetUserByIdResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getUserById";
            response = (GetUserByIdResponse) transportService.executeRequest(url, request, null, GetUserByIdResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    //    public CreateRoleZoneMapResponse createRoleZoneMap(CreateRoleZoneMapRequest request)
    //
    //    {
    //        CreateRoleZoneMapResponse response = new CreateRoleZoneMapResponse();
    //        response.setSuccessful(false);
    //        try {
    //            String url = getWebServiceURL() + "/createRoleZoneMap";
    //            response = (CreateRoleZoneMapResponse) transportService.executeRequest(url, request, null, CreateRoleZoneMapResponse.class);
    //            return response;
    //        } catch (com.snapdeal.base.exception.TransportException e) {
    //              LOG.error("Error Message:", e);
    //        }
    //        return response;
    //    }

    @Deprecated
    public GetReferralResponse getReferral(GetReferralRequest request)

    {
        GetReferralResponse response = new GetReferralResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getReferral";
            response = (GetReferralResponse) transportService.executeRequest(url, request, null, GetReferralResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public GetUserInformationsByUserResponse getUserInformationsByUser(GetUserInformationsByUserRequest request)

    {
        GetUserInformationsByUserResponse response = new GetUserInformationsByUserResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getUserInformationsByUser";
            response = (GetUserInformationsByUserResponse) transportService.executeRequest(url, request, null, GetUserInformationsByUserResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    public GetUserInformationByUserAndNameResponse getUserInformationByUserAndName(GetUserInformationByUserAndNameRequest request)

    {
        GetUserInformationByUserAndNameResponse response = new GetUserInformationByUserAndNameResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getUserInformationByUserAndName";
            response = (GetUserInformationByUserAndNameResponse) transportService.executeRequest(url, request, null, GetUserInformationByUserAndNameResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public AddUserInformationResponse addUserInformation(AddUserInformationRequest request)

    {
        AddUserInformationResponse response = new AddUserInformationResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/addUserInformation";
            response = (AddUserInformationResponse) transportService.executeRequest(url, request, null, AddUserInformationResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    public CreditSDCashResponse creditSDCash(CreditSDCashRequest request)

    {
        CreditSDCashResponse response = new CreditSDCashResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/creditSDCash";
            response = (CreditSDCashResponse) transportService.executeRequest(url, request, null, CreditSDCashResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    public DebitSdCashResponse debitSdCash(DebitSdCashRequest request)

    {
        DebitSdCashResponse response = new DebitSdCashResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/debitSdCash";
            response = (DebitSdCashResponse) transportService.executeRequest(url, request, null, DebitSdCashResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    public CreateUserResponse createUser(CreateUserRequest request)

    {
        CreateUserResponse response = new CreateUserResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/createUser";
            response = (CreateUserResponse) transportService.executeRequest(url, request, null, CreateUserResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    public CreateEmailVerificationCodeResponse createEmailVerificationCode(CreateEmailVerificationCodeRequest request)

    {
        CreateEmailVerificationCodeResponse response = new CreateEmailVerificationCodeResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/createEmailVerificationCode";
            response = (CreateEmailVerificationCodeResponse) transportService.executeRequest(url, request, null, CreateEmailVerificationCodeResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    public GetEmailVerificationCodeResponse getEmailVerificationCode(GetEmailVerificationCodeRequest request)

    {
        GetEmailVerificationCodeResponse response = new GetEmailVerificationCodeResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getEmailVerificationCode";
            response = (GetEmailVerificationCodeResponse) transportService.executeRequest(url, request, null, GetEmailVerificationCodeResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    public ClearEmailVerificationCodeResponse clearEmailVerificationCode(ClearEmailVerificationCodeRequest request)

    {
        ClearEmailVerificationCodeResponse response = new ClearEmailVerificationCodeResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/clearEmailVerificationCode";
            response = (ClearEmailVerificationCodeResponse) transportService.executeRequest(url, request, null, ClearEmailVerificationCodeResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    public GetUsersByRoleAndZoneResponse getUsersByRoleAndZone(GetUsersByRoleAndZoneRequest request)

    {
        GetUsersByRoleAndZoneResponse response = new GetUsersByRoleAndZoneResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getUsersByRoleAndZone";
            response = (GetUsersByRoleAndZoneResponse) transportService.executeRequest(url, request, null, GetUsersByRoleAndZoneResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    //    public GetZonesForUserRolesResponse getZonesForUserRoles(GetZonesForUserRolesRequest request)
    //
    //    {
    //        GetZonesForUserRolesResponse response = new GetZonesForUserRolesResponse();
    //        response.setSuccessful(false);
    //        try {
    //            String url = getWebServiceURL() + "/getZonesForUserRoles";
    //            response = (GetZonesForUserRolesResponse) transportService.executeRequest(url, request, null, GetZonesForUserRolesResponse.class);
    //            return response;
    //        } catch (com.snapdeal.base.exception.TransportException e) {
    //              LOG.error("Error Message:", e);
    //        }
    //        return response;
    //    }

    @Deprecated
    public GetZendeskUserResponse getZendeskUser(GetZendeskUserRequest request)

    {
        GetZendeskUserResponse response = new GetZendeskUserResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getZendeskUser";
            response = (GetZendeskUserResponse) transportService.executeRequest(url, request, null, GetZendeskUserResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    public PersistUserResponse persistUser(PersistUserRequest request)

    {
        PersistUserResponse response = new PersistUserResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/persistUser";
            response = (PersistUserResponse) transportService.executeRequest(url, request, null, PersistUserResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    public CreditSDCashToUserAccountResponse creditSDCashToUserAccount(CreditSDCashToUserAccountRequest request)

    {
        CreditSDCashToUserAccountResponse response = new CreditSDCashToUserAccountResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/creditSDCashToUserAccount";
            response = (CreditSDCashToUserAccountResponse) transportService.executeRequest(url, request, null, CreditSDCashToUserAccountResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    public IsUserActivityPresentInHistoryResponse isUserActivityPresentInHistory(IsUserActivityPresentInHistoryRequest request)

    {
        IsUserActivityPresentInHistoryResponse response = new IsUserActivityPresentInHistoryResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/isUserActivityPresentInHistory";
            response = (IsUserActivityPresentInHistoryResponse) transportService.executeRequest(url, request, null, IsUserActivityPresentInHistoryResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    public GetLastCreatedTimestampFromSDCashHistoryResponse getLastCreatedTimestampFromSDCashHistory(GetLastCreatedTimestampFromSDCashHistoryRequest request)

    {
        GetLastCreatedTimestampFromSDCashHistoryResponse response = new GetLastCreatedTimestampFromSDCashHistoryResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getLastCreatedTimestampFromSDCashHistory";
            response = (GetLastCreatedTimestampFromSDCashHistoryResponse) transportService.executeRequest(url, request, null,
                    GetLastCreatedTimestampFromSDCashHistoryResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    public GetAllUsersFromSDWalletHistoryResponse getAllUsersFromSDCashHistory(GetAllUsersFromSDWalletHistoryRequest request)

    {
        GetAllUsersFromSDWalletHistoryResponse response = new GetAllUsersFromSDWalletHistoryResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getAllUsersFromSDCashHistory";
            response = (GetAllUsersFromSDWalletHistoryResponse) transportService.executeRequest(url, request, null, GetAllUsersFromSDWalletHistoryResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public GetUserPreferenceByMobileResponse getUserPreferenceByMobile(GetUserPreferenceByMobileRequest request)

    {
        GetUserPreferenceByMobileResponse response = new GetUserPreferenceByMobileResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getUserPreferenceByMobile";
            response = (GetUserPreferenceByMobileResponse) transportService.executeRequest(url, request, null, GetUserPreferenceByMobileResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public AddUserPreferenceResponse addUserPreference(AddUserPreferenceRequest request)

    {
        AddUserPreferenceResponse response = new AddUserPreferenceResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/addUserPreference";
            response = (AddUserPreferenceResponse) transportService.executeRequest(url, request, null, AddUserPreferenceResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    public GetConfirmationLinkResponse getConfirmationLink(GetConfirmationLinkRequest request)

    {
        GetConfirmationLinkResponse response = new GetConfirmationLinkResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getConfirmationLink";
            response = (GetConfirmationLinkResponse) transportService.executeRequest(url, request, null, GetConfirmationLinkResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    
    @Override
    public GetUserByIdResponse getUserByIdWithoutRoles(GetUserByIdRequest request) {
        GetUserByIdResponse response = new GetUserByIdResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getUserByIdWithoutRoles";
            response = (GetUserByIdResponse) transportService.executeRequest(url, request, null, GetUserByIdResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    @Override
    public CustomerScoreResponse getCustomerScoreByEmail(CustomerScoreRequest request) {
        CustomerScoreResponse response = new CustomerScoreResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getCustomerEmailScore";
            response = (CustomerScoreResponse) transportService.executeRequest(url, request, null, CustomerScoreResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public CustomerScoreResponse getCustomerScoreByMobile(CustomerScoreRequest request) {
        CustomerScoreResponse response = new CustomerScoreResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getCustomerMobileScore";
            response = (CustomerScoreResponse) transportService.executeRequest(url, request, null, CustomerScoreResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public CustomerScoreResponse mergeCustomerEmailScore(CustomerScoreRequest request) {
        CustomerScoreResponse response = new CustomerScoreResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/addCustomerEmailScore";
            response = (CustomerScoreResponse) transportService.executeRequest(url, request, null, CustomerScoreResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public CustomerScoreResponse mergeCustomerMobileScore(CustomerScoreRequest request) {
        CustomerScoreResponse response = new CustomerScoreResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/addCustomerMobileScore";
            response = (CustomerScoreResponse) transportService.executeRequest(url, request, null, CustomerScoreResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Override
    public CreateOrUpdateReferralSentResponse createOrUpdateReferralSent(CreateOrUpdateReferralSentRequest request) {
        CreateOrUpdateReferralSentResponse response = new CreateOrUpdateReferralSentResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/createOrUpdateReferralSent";
            response = (CreateOrUpdateReferralSentResponse) transportService.executeRequest(url, request, null, CustomerScoreResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public UpdateReferralClickResponse updateReferralClick(UpdateReferralClickRequest request) {
        UpdateReferralClickResponse response = new UpdateReferralClickResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/updateReferralClick";
            response = (UpdateReferralClickResponse) transportService.executeRequest(url, request, null, CustomerScoreResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public IsVisaBenefitAvailedResponse isVisaBenefitAvailed(IsVisaBenefitAvailedRequest request) {
        IsVisaBenefitAvailedResponse response = new IsVisaBenefitAvailedResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/isVisaBenefitAvailed";
            response = (IsVisaBenefitAvailedResponse) transportService.executeRequest(url, request, null, CustomerScoreResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }
    
    @Override
    public IsMobileExistResponse isMobileExist(IsMobileExistRequest request) throws TransportException{
        String url = getWebServiceURL() + "/isMobileExist";
        IsMobileExistResponse response = (IsMobileExistResponse) transportService.executeRequest(url, request, null, IsMobileExistResponse.class);
        return response;
    }

	@Override
	public CreateUserWithDetailsResponse createUserWithDetails(
			CreateUserWithDetailsRequest request) {
		
		CreateUserWithDetailsResponse response = new CreateUserWithDetailsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/createUserWithDetails";
            response = (CreateUserWithDetailsResponse) transportService.executeRequest(url, request, null, CreateUserWithDetailsResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
		

	}
}
