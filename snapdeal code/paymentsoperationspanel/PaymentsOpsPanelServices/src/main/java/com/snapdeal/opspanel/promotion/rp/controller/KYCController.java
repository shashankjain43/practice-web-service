package com.snapdeal.opspanel.promotion.rp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.ims.request.CreateOrUpdateKycRequest;
import com.snapdeal.ims.request.GetUserByMobileRequest;
import com.snapdeal.ims.request.InitiateKycRequest;
import com.snapdeal.ims.request.UndoKycRequest;
import com.snapdeal.ims.request.UpdateUserByIdRequest;
import com.snapdeal.opspanel.audit.annotations.Audited;
import com.snapdeal.opspanel.commons.entity.GenericResponse;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.commons.utils.GenericControllerUtils;
import com.snapdeal.opspanel.promotion.constants.KYCConstants;
import com.snapdeal.opspanel.rms.service.TokenService;
import com.snapdeal.opspanel.userpanel.service.KYCService;
import com.snapdeal.payments.roleManagementModel.commons.PreAuthorize;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping( KYCConstants.KYC )
@Slf4j
public class KYCController {
	
	@Autowired
	KYCService kycService;

	@Autowired
	HttpServletRequest servletRequest;
	
	@Autowired
	TokenService tokenService;

	@Audited(context = "USEROPS", searchId = "", skipRequestKeys = {}, skipResponseKeys = {})
	@RequestMapping(value = KYCConstants.GET_KYC_ENUMS, method = RequestMethod.GET )
	public @ResponseBody GenericResponse getKYCEnums() throws OpsPanelException {
		Map< String, List<String>> enumsMap = new HashMap< String, List<String>>(); 
		try {
			for( Class classToParse : KYCConstants.enumsToParse ){
				List<String> enumList= new ArrayList<String>();
				for( Object obj : classToParse.getEnumConstants() ){
					enumList.add(obj.toString());
				}
				enumsMap.put( classToParse.getSimpleName(), enumList );
			}
			return  GenericControllerUtils.getGenericResponse( enumsMap );
		} catch( Exception e ) {
			throw new OpsPanelException( "MT-1202", "Could not parse enums." );
		}		
	}

//	@Audited(context = "USEROPS", searchId = "request.username", skipRequestKeys = {"request.password"}, skipResponseKeys = {"response.data.token"})
//	@PreAuthorize("(hasPermission('OPS_CORPACCOUNT_CORPTOCORP'))")	
//	@RequestMapping(value = KYCConstants.GET_USER_KYC_DETAILS, method = RequestMethod.POST )
//	public @ResponseBody GenericResponse getUserKYCDetails( @RequestBody @Valid GetKycDetailsRequest getKycDetailsRequest,
//			BindingResult bindingResult ) throws OpsPanelException {
//		GenericControllerUtils.checkBindingResult(bindingResult, " getUserKYCDetails in KYCController");
//		return GenericControllerUtils.getGenericResponse( kycService.getUserKYCDetails(getKycDetailsRequest) );
//	}

	@Audited(context = "USEROPS", searchId = "initiateKycRequest.kycStatusDTO.userId", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_USEROPS_FULL_KYC'))")
	@RequestMapping(value = KYCConstants.INITIATE_KYC_PROCESS, method = RequestMethod.POST )
	public @ResponseBody GenericResponse initiateKYCProcess( @RequestBody @Valid InitiateKycRequest initiateKycRequest,
			BindingResult bindingResult ) throws OpsPanelException {
		GenericControllerUtils.checkBindingResult(bindingResult, " initiateKycRequest in KYCController");
		initiateKycRequest.getKycStatusDTO().setKycInitiatedBy( KYCConstants.kycInitiatedBy );
		initiateKycRequest.getKycStatusDTO().setAuditedBy( getEmailFromSession() );
		return GenericControllerUtils.getGenericResponse( kycService.initiateKYCProcess(initiateKycRequest) );
	}

	@Audited(context = "USEROPS", searchId = "updateUserByIdRequest.userId", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_USEROPS_FULL_KYC'))")
	@RequestMapping(value = KYCConstants.UPDATE_USER_BY_ID, method = RequestMethod.POST )
	public @ResponseBody GenericResponse updateUserById( @RequestBody @Valid UpdateUserByIdRequest updateUserByIdRequest,
			BindingResult bindingResult ) throws OpsPanelException {

		GenericControllerUtils.checkBindingResult(bindingResult, " updateUserById in KYCController");
		return GenericControllerUtils.getGenericResponse( kycService.updateUserById(updateUserByIdRequest) );
	}

	@Audited(context = "USEROPS", searchId = "createOrUpdateKycRequest.kycDetailsDTO.identityURN", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_USEROPS_FULL_KYC'))")
	@RequestMapping(value = KYCConstants.CREATE_OR_UPDATE_KYC_USER, method = RequestMethod.POST )
	public @ResponseBody GenericResponse createorUpdateKYCUser( @RequestBody @Valid CreateOrUpdateKycRequest createOrUpdateKycRequest,
			BindingResult bindingResult ) throws OpsPanelException {
		GenericControllerUtils.checkBindingResult(bindingResult, " createorUpdateKYCUser in KYCController");
		createOrUpdateKycRequest.getKycDetailsDTO().setAuditedBy( getEmailFromSession() );
		return GenericControllerUtils.getGenericResponse( kycService.createorUpdateKYCUser(createOrUpdateKycRequest) );
	}

	@Audited(context = "USEROPS", searchId = "undoKycRequest.userId", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_USEROPS_FULL_KYC'))")
	@RequestMapping(value = KYCConstants.UNDO_FULL_KYC_FOR_USER, method = RequestMethod.POST )
	public @ResponseBody GenericResponse undoFullKYCForUser( @RequestBody @Valid UndoKycRequest undoKycRequest,
			BindingResult bindingResult ) throws OpsPanelException {

		GenericControllerUtils.checkBindingResult(bindingResult, " undoFullKYCForUser in KYCController");
		undoKycRequest.setAuditorName( getEmailFromSession() );
		return GenericControllerUtils.getGenericResponse( kycService.undoFullKYCForUser(undoKycRequest) );
	}
	@Audited(context = "USEROPS", searchId = "request.mobileNumber", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_USEROPS_FULL_KYC'))")
	@RequestMapping( value = KYCConstants.GET_USER_BY_VERIFIED_MOBILE, method=RequestMethod.POST )
	public @ResponseBody GenericResponse getUserByVerifiedMobile( @RequestBody @Valid GetUserByMobileRequest request,
			BindingResult bindingResult ) throws OpsPanelException {
		GenericControllerUtils.checkBindingResult( bindingResult, " getUserByVerifiedMobile in KYCController." );
		
		return GenericControllerUtils.getGenericResponse( kycService.getUserByVerifiedMobile( request ) );
	}

	private String getEmailFromSession() throws OpsPanelException {
		String token = servletRequest.getHeader("token");
		String emailId = tokenService.getEmailFromToken(token);

		return emailId;	
	}
}
