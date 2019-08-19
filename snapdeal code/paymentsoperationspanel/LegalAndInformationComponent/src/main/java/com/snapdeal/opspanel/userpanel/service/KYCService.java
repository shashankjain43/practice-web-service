package com.snapdeal.opspanel.userpanel.service;

import com.snapdeal.ims.request.CreateOrUpdateKycRequest;
import com.snapdeal.ims.request.GetKycDetailsRequest;
import com.snapdeal.ims.request.GetUserByMobileRequest;
import com.snapdeal.ims.request.InitiateKycRequest;
import com.snapdeal.ims.request.KycDetailResponseDTO;
import com.snapdeal.ims.request.UndoKycRequest;
import com.snapdeal.ims.request.UpdateUserByIdRequest;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.ims.response.InitiateKycResponse;
import com.snapdeal.ims.response.KYCStatusResponse;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.userpanel.response.GetUserByVerifiedMobileResponse;

public interface KYCService {

	KycDetailResponseDTO getUserKYCDetails(GetKycDetailsRequest getKycDetailsRequest) throws OpsPanelException;

	InitiateKycResponse initiateKYCProcess(InitiateKycRequest initiateKycRequest) throws OpsPanelException;

	GetUserResponse updateUserById(UpdateUserByIdRequest updateUserByIdRequest) throws OpsPanelException;

	KYCStatusResponse undoFullKYCForUser(UndoKycRequest undoKycRequest) throws OpsPanelException;

	KYCStatusResponse createorUpdateKYCUser(CreateOrUpdateKycRequest createOrUpdateKycRequest)
			throws OpsPanelException;

	GetUserByVerifiedMobileResponse getUserByVerifiedMobile( GetUserByMobileRequest request ) throws OpsPanelException;

}
