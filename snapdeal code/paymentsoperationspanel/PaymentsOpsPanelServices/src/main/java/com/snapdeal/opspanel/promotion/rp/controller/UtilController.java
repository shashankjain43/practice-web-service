package com.snapdeal.opspanel.promotion.rp.controller;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Joiner;
import com.snapdeal.bulkprocess.utils.BulkProcessorUtils;
import com.snapdeal.opspanel.audit.annotations.Audited;
import com.snapdeal.opspanel.csv.request.LIstToCSVRequest;
import com.snapdeal.opspanel.csv.service.CSVServices;
import com.snapdeal.opspanel.promotion.Response.GenericResponse;
import com.snapdeal.opspanel.promotion.enums.SampleFileLocationMapping;
import com.snapdeal.opspanel.promotion.request.LoginRequest;
import com.snapdeal.opspanel.promotion.utils.OPSUtils;
import com.snapdeal.opspanel.userpanel.p2preversal.constants.P2PReversalConstants;
import com.snapdeal.opspanel.userpanel.p2preversal.enums.ImsIdTypes;
import com.snapdeal.opspanel.userpanel.walletreversal.Enum.InstrumentType;
import com.snapdeal.vanila.bulk.BulkTID.BulkTIDConstants;
import com.snapdeal.vanila.bulk.merchanthierarchy.association.enums.AssociationOperationType;
import com.snapdeal.vanila.bulk.merchanthierarchy.dealer.enums.DealerOperationType;
import com.snapdeal.vanila.enums.BulkMerchantHeirarchyActivityIds;
import com.snapdeal.vanila.utils.MOBUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/util")
public class UtilController {
	
	@Autowired
	CSVServices csvServices;

	public static final String COMMA = ",";

	public static final String LINE_BREAKER = "\r\n";
	
	public static final String REGEX = ",|\n|\r";

	@Audited(context = "Util", searchId = "userId", skipRequestKeys = {}, skipResponseKeys = {})
	@RequestMapping(value = "/getSampleFiles", method = RequestMethod.GET)
	public @ResponseBody GenericResponse getSampleFile(@RequestParam("idtype") String userId) {
		if (userId.equalsIgnoreCase("IMS_ID")) {
			return getGenericResponse("/samplefiles/IMS ID_Sample Data.csv");
		} else if (userId.equalsIgnoreCase("EMAIL_ID")) {
			return getGenericResponse("/samplefiles/Email ID_Sample Data.csv");
		} else if (userId.equalsIgnoreCase("MOBILE_ID")) {
			return getGenericResponse("/samplefiles/Mobile No_Sample Data.csv");
		} else {
			return getGenericResponse("/samplefiles/IMS ID_Sample Data.csv");
		}
	}

	@Audited(context = "Util", searchId = "request", skipRequestKeys = {}, skipResponseKeys = {})
	@RequestMapping(value = "/downloadSampleFiles", method = RequestMethod.GET)
	public @ResponseBody GenericResponse downloadSampleFile(
			@RequestParam("fileType") SampleFileLocationMapping request) {
		return getGenericResponse(request.toString());
	}

	@Audited(context = "Util", searchId = "idType", skipRequestKeys = {}, skipResponseKeys = {})
	@RequestMapping(value = "/getFOSSampleFiles", method = RequestMethod.GET)
	public @ResponseBody GenericResponse getFOSSampleFile(@RequestParam("idtype") String idType) {
		if (idType.equalsIgnoreCase("mobile")) {
			return getGenericResponse("/samplefiles/bulkFOS/BULK_FOS_MOBILE_SAMPLE_FILE.csv");
		} else {
			return getGenericResponse("/samplefiles/bulkFOS/BULK_FOS_EMAIL_SAMPLE_FILE.csv");
		}
	}

	@Audited(context = "Util", skipRequestKeys = {}, skipResponseKeys = {})
	@RequestMapping(value = "/getFCPlusSampleFile", method = RequestMethod.GET)
	public @ResponseBody GenericResponse getFCPlusSampleFile() {
		return getGenericResponse("/samplefiles/bulkFCPlusOnboard/fcPlusOnboardSampleCSV.csv");
	}

	@RequestMapping(value = "/getMock", method = RequestMethod.GET)
	public @ResponseBody GenericResponse getMockForClass(@RequestParam("classpath") String classpath)
			throws ClassNotFoundException {
		Class genericClass = Class.forName(classpath);
		return OPSUtils.getGenericResponse(OPSUtils.constructMock(genericClass));
	}

	@RequestMapping(value = "/getEnum", method = RequestMethod.GET)
	public @ResponseBody GenericResponse getEnumValues(@RequestParam("enumpath") String enumpath)
			throws ClassNotFoundException {

		List<String> enumList = new ArrayList<String>();
		Class genericClass = Class.forName(enumpath);
		for (Object obj : genericClass.getEnumConstants()) {
			enumList.add(obj.toString());
		}
		return OPSUtils.getGenericResponse(enumList);

	}

	@Audited(context = "Util", searchId = "operationt_type", skipRequestKeys = {}, skipResponseKeys = {})
	@RequestMapping(value = "/getMerchantHierarchySampleFiles", method = RequestMethod.GET)
	public @ResponseBody GenericResponse getMHMerchantSampleFile(@RequestParam("activityid") String activityId,
			@RequestParam("operation_type") String operationType) {

		if (activityId.equals(BulkMerchantHeirarchyActivityIds.MH_MERCHANT_ACTIVITY.name())) {
			if (operationType.equalsIgnoreCase("ONBOARD")) {
				return getGenericResponse("/samplefiles/mhBulkMerchant/OnboardMerchantSampleFile.csv");
			} else if (operationType.equalsIgnoreCase("UPDATE")) {
				return getGenericResponse("/samplefiles/mhBulkMerchant/UpdateMerchantDetailsSampleFile.csv");
			}
		}
		if (activityId.equals(BulkMerchantHeirarchyActivityIds.MH_ASSOCIATION_ACTIVITY.toString())) {
			if (operationType.equals(AssociationOperationType.MERCHANT_DEALER.toString())) {
				return getGenericResponse("/samplefiles/bulkAssociation/MDealerAssociationSample.csv");
			} else if (operationType.equals(AssociationOperationType.MERCHANT_PARTNER.toString())) {
				return getGenericResponse("/samplefiles/bulkAssociation/MPartnerAssociationSample.csv");
			} else if (operationType.equals(AssociationOperationType.MERCHANT_PLATFORM.toString())) {
				return getGenericResponse("/samplefiles/bulkAssociation/MPlatformAssociationSample.csv");
			} else if (operationType.equals(AssociationOperationType.PARTNER_PLATFORM.toString())) {
				return getGenericResponse("/samplefiles/bulkAssociation/PartnerPlatformAssociationSample.csv");
			}
		}
		if (activityId.equals(BulkMerchantHeirarchyActivityIds.MH_DEALER_ACTIVITY.toString())) {
			if (operationType.equals(DealerOperationType.ONBOARD.toString())) {
				return getGenericResponse("/samplefiles/bulkDealer/dealer_onboard_sample.csv");
			} else if (operationType.equals(DealerOperationType.UPDATE.toString())) {
				return getGenericResponse("/samplefiles/bulkDealer/dealer_update_sample.csv");
			}
		}
		return null;
	}

	@RequestMapping(value = "/getFileForP2PIdType", method = RequestMethod.GET)
	public @ResponseBody GenericResponse getSampleFileForPartialP2P(@RequestParam("id_type") String idType) {

		if (idType.equalsIgnoreCase(ImsIdTypes.EMAIL.toString())) {
			return getGenericResponse("/samplefiles/PartialReverseP2PEmail.csv");
		}
		if (idType.equalsIgnoreCase(ImsIdTypes.MOBILE.toString())) {
			return getGenericResponse("/samplefiles/PartialReverseP2PMobile.csv");
		}
		if (idType.equalsIgnoreCase(ImsIdTypes.IMS_ID.toString())) {
			return getGenericResponse("/samplefiles/PartialReverseP2PIMSID.csv");

		}
		log.info("No File Found For given IMS ID Type: "  +idType+ "  Please Check ");
		return null;
	}

	@RequestMapping(value = "/getFileForP2PFullReversal", method = RequestMethod.GET)
	public @ResponseBody GenericResponse getSampleFileForFullP2P() {

		return getGenericResponse("/samplefiles/FullReverseP2PSample.csv");

	}

	@RequestMapping(value = "/getFileWalletReversal", method = RequestMethod.GET)
	public @ResponseBody GenericResponse getFileWalletReversal(@RequestParam("txnid_type") String txnid_type,@RequestParam("instrumentType") String instrumentType ) {
		if(instrumentType.equals(InstrumentType.VOUCHER.name())) {
			return getGenericResponse("/samplefiles/Sample_Voucher_Wallet_reversal.csv");
		} else {
			return getGenericResponse("/samplefiles/Sample_Wallet_reversal.csv");
		}
	}

	@RequestMapping(value = "/getBulkTIDSampleFiles", method = RequestMethod.GET)
	public @ResponseBody GenericResponse getBulkTIDSampleFiles(@RequestParam("actionType") String actionType) {
		if(actionType.equals(BulkTIDConstants.ADD_UPDATE)) {
			return getGenericResponse("/samplefiles/bulkTID/Bulk_TID_Add_Update_Sample.csv");
		} else if(actionType.equals(BulkTIDConstants.DELETE)){
			return getGenericResponse("/samplefiles/bulkTID/Bulk_TID_Delete_Sample.csv");
		}
		return null;
	}

	
	@RequestMapping(value = "/listToCSV", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity listToCSV(LIstToCSVRequest request) {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("content-disposition", "attachment; filename=" + "ListToCSV.csv");
		httpHeaders.add("Content-Type", "text/csv");

		/*LIstToCSVRequest request = new LIstToCSVRequest();
		request.setClassName(LoginRequest.class);
		List<Object> list = new ArrayList<Object>();
		for(int i=0; i<100; i++){
			LoginRequest req = new LoginRequest();
			req.setUsername("Aaquib" + i);
			req.setPassword(i+"");
			list.add(req);	
		}
		request.setObjects(list);*/
		StringBuffer sb = new StringBuffer();
		sb = csvServices.getListToCSV(request, ",");
		return new ResponseEntity(sb.toString().getBytes(), httpHeaders, HttpStatus.OK);
	}

	private GenericResponse getGenericResponse(Object walletResponse) {
		GenericResponse genericResponse = new GenericResponse();
		genericResponse.setError(null);
		genericResponse.setData(walletResponse);
		return genericResponse;
	}
}
