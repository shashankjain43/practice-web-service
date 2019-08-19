
package com.snapdeal.ums.server.services;

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

public interface IUserService {


    public IsUserExistsResponse isUserExists(IsUserExistsRequest request);

    public AddUserResponse addUser(AddUserRequest request);

    public GetUserByEmailResponse getUserByEmail(GetUserByEmailRequest request);

    public UpdateUserResponse updateUser(UpdateUserRequest request);

    public VerifyUserResponse verifyUser(VerifyUserRequest request);

    public GetOpenIdUserResponse getOpenIdUser(GetOpenIdUserRequest request);

    public CreateUserRoleResponse createUserRole(CreateUserRoleRequest request);

    public GetOrCreateVendorUserResponse getOrCreateVendorUser(GetOrCreateVendorUserRequest request);

    public GetUserByIdResponse getUserById(GetUserByIdRequest request);

    public GetReferralResponse getReferral(GetReferralRequest request);

    public GetUserInformationsByUserResponse getUserInformationsByUser(GetUserInformationsByUserRequest request);

    public GetUserInformationByUserAndNameResponse getUserInformationByUserAndName(GetUserInformationByUserAndNameRequest request);

    public AddUserInformationResponse addUserInformation(AddUserInformationRequest request);

    public CreateUserResponse createUser(CreateUserRequest request);

    public CreateEmailVerificationCodeResponse createEmailVerificationCode(CreateEmailVerificationCodeRequest request);

    public GetEmailVerificationCodeResponse getEmailVerificationCode(GetEmailVerificationCodeRequest request);

    public ClearEmailVerificationCodeResponse clearEmailVerificationCode(ClearEmailVerificationCodeRequest request);

//    public GetZonesForUserRolesResponse getZonesForUserRoles(GetZonesForUserRolesRequest request);

    public GetZendeskUserResponse getZendeskUser(GetZendeskUserRequest request);

    public PersistUserResponse persistUser(PersistUserRequest request);

    public GetUserPreferenceByMobileResponse getUserPreferenceByMobile(GetUserPreferenceByMobileRequest request);

    public AddUserPreferenceResponse addUserPreference(AddUserPreferenceRequest request);

    public GetConfirmationLinkResponse getConfirmationLink(GetConfirmationLinkRequest request);
    
    public GetUserByIdResponse getUserByIdWithoutRoles(GetUserByIdRequest request);

	public GetUserByIdResponse getUserByIdWithRoles(GetUserByIdRequest request);

	public GetUserByEmailResponse getUserByEmailWithRoles(GetUserByEmailRequest request);
	
	public CustomerScoreResponse getCustomerScoreByEmail(CustomerScoreRequest request);
	
	public CustomerScoreResponse getCustomerScoreByMobile(CustomerScoreRequest request);

	public CustomerScoreResponse mergeCustomerEmailScore(CustomerScoreRequest request);
    
    public CustomerScoreResponse mergeCustomerMobileScore(CustomerScoreRequest request);

    public CreateOrUpdateReferralSentResponse createOrUpdateReferralSent(CreateOrUpdateReferralSentRequest request);

    public UpdateReferralClickResponse updateReferralClick(UpdateReferralClickRequest request);

    public IsVisaBenefitAvailedResponse isVisaBenefitAvailed(IsVisaBenefitAvailedRequest request);

    public DeleteUserRoleByIdResponse deleteUserRoleById(DeleteUserRoleByIdRequest request);

    public IsMobileExistResponse isMobileExist(IsMobileExistRequest request);

	public Integer getUserIdByEmail(String email);

	public String getUserEmailById(Integer id);

	public GetUserByEmailResponse getUserByEmailWithoutJoin(
			GetUserByEmailRequest request);

	public CreateUserResponse createSubsidieryUser(CreateSubsidieryUserRequest request);

	public CreateUserWithDetailsResponse createUserWithDetails(
			CreateUserWithDetailsRequest request);
    
}
