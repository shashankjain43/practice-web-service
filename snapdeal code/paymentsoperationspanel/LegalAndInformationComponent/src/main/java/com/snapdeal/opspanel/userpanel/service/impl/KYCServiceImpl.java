package com.snapdeal.opspanel.userpanel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.ims.client.IUserServiceClient;
import com.snapdeal.ims.dto.UserDetailsDTO;
import com.snapdeal.ims.enums.KycCurrentStatus;
import com.snapdeal.ims.exception.HttpTransportException;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.CreateOrUpdateKycRequest;
import com.snapdeal.ims.request.GetKycDetailsRequest;
import com.snapdeal.ims.request.InitiateKycRequest;
import com.snapdeal.ims.request.KycDetailResponseDTO;
import com.snapdeal.ims.request.UndoKycRequest;
import com.snapdeal.ims.request.UpdateUserByIdRequest;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.ims.response.InitiateKycResponse;
import com.snapdeal.ims.response.KYCStatusResponse;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.userpanel.response.GetUserByVerifiedMobileResponse;
import com.snapdeal.opspanel.userpanel.service.KYCService;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;

@Service("KYCService")
@Slf4j
public class KYCServiceImpl implements KYCService {

	@Autowired
	IUserServiceClient iUserServiceClient;

	@Override
	public KycDetailResponseDTO getUserKYCDetails(GetKycDetailsRequest getKycDetailsRequest) throws OpsPanelException {

		try {
			return iUserServiceClient.getUserKYCDetails(getKycDetailsRequest);
		} catch ( ServiceException e) {
			log.info("Exception while calling getUserKYCDetails " + ExceptionUtils.getFullStackTrace(e));
			throw new OpsPanelException("KYC-101", e.getErrMsg(), "KYC-IMS");
		}
	}

	@Override
	public InitiateKycResponse initiateKYCProcess(InitiateKycRequest initiateKycRequest) throws OpsPanelException {

		try {
			return iUserServiceClient.initiateKYCProcess(initiateKycRequest);
		} catch ( ServiceException e) {
			log.info("Exception while calling initiateKYCProcess " + ExceptionUtils.getFullStackTrace(e));
			throw new OpsPanelException("KYC-103", e.getErrMsg(), "KYC-IMS");
		}
	}

	@Override
	public GetUserResponse updateUserById(UpdateUserByIdRequest updateUserByIdRequest) throws OpsPanelException {

		try {
			return iUserServiceClient.updateUserById(updateUserByIdRequest);
		} catch ( ServiceException e) {
			log.info("Exception while calling updateUserById " + ExceptionUtils.getFullStackTrace(e));
			throw new OpsPanelException("KYC-105", e.getErrMsg(), "KYC-IMS");
		}
	}

	@Override
	public KYCStatusResponse createorUpdateKYCUser(CreateOrUpdateKycRequest createOrUpdateKycRequest)
			throws OpsPanelException {

		try {
			return iUserServiceClient.createOrUpdateKYCUser(createOrUpdateKycRequest);
		} catch ( ServiceException e) {
			log.info("Exception while calling createorUpdateKYCUser " + ExceptionUtils.getFullStackTrace(e));
			throw new OpsPanelException("KYC-107", e.getErrMsg(), "KYC-IMS");
		}
	}

	@Override
	public KYCStatusResponse undoFullKYCForUser(UndoKycRequest undoKycRequest) throws OpsPanelException {

		try {
			return iUserServiceClient.undoFullKYCForUser(undoKycRequest);
		} catch ( ServiceException e) {
			log.info("Exception while calling undoFullKYCForUser " + ExceptionUtils.getFullStackTrace(e));
			throw new OpsPanelException("KYC-109", e.getErrMsg(), "KYC-IMS");
		}
	}

	public GetUserByVerifiedMobileResponse getUserByVerifiedMobile(com.snapdeal.ims.request.GetUserByMobileRequest request) throws com.snapdeal.opspanel.commons.exceptions.OpsPanelException {

		GetUserByVerifiedMobileResponse getUserByVerifiedMobileResponse = new GetUserByVerifiedMobileResponse();
		UserDetailsDTO userDetailsDTO = null;
		try {
			GetKycDetailsRequest getKycDetailsRequest = new GetKycDetailsRequest();
			getKycDetailsRequest.setMobileNumber( request.getMobileNumber() );
			KycDetailResponseDTO kycDetailResponseDTO = iUserServiceClient.getUserKYCDetails( getKycDetailsRequest );
			userDetailsDTO = kycDetailResponseDTO.getUserDetailsDTO();
			getUserByVerifiedMobileResponse.setCurrentKycStatus( kycDetailResponseDTO.getKycStatusDTO().getKycCurrentStatus().name() );
			getUserByVerifiedMobileResponse.setKycDetailsDTO( kycDetailResponseDTO.getKycDetailsDTO() );
			getUserByVerifiedMobileResponse.setKycMode( kycDetailResponseDTO.getKycStatusDTO().getKycMode() );
		} catch(  ServiceException e ) {
			log.info( "Exception while calling getUserKYCDetails " + ExceptionUtils.getFullStackTrace( e ) );
			GetUserResponse getUserResponse = null;
			try {
				getUserResponse = iUserServiceClient.getUserByVerifiedMobile( request );
			} catch( ServiceException ex ) {
				log.info( "Exception while getting user by verified mobile number " + ExceptionUtils.getFullStackTrace( e ) );
				throw new OpsPanelException( "KYC-111", ex.getErrMsg(), "KYC-IMS" );
			}
			userDetailsDTO = getUserResponse.getUserDetails();
			getUserByVerifiedMobileResponse.setCurrentKycStatus( KycCurrentStatus.NOT_INITIATED.name() );
		}
		getUserByVerifiedMobileResponse.setFirstName( userDetailsDTO.getFirstName() );
		getUserByVerifiedMobileResponse.setLastName( userDetailsDTO.getLastName() );
		getUserByVerifiedMobileResponse.setDob( userDetailsDTO.getDob() );
		getUserByVerifiedMobileResponse.setGender( userDetailsDTO.getGender() == null ? "" : userDetailsDTO.getGender().name() );
		getUserByVerifiedMobileResponse.setUserId( userDetailsDTO.getUserId() );
		return getUserByVerifiedMobileResponse;

	};
}
