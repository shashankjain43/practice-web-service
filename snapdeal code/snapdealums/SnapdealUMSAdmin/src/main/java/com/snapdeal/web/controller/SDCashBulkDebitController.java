package com.snapdeal.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.snapdeal.base.services.request.context.RequestContextSRO;
import com.snapdeal.base.startup.config.AppEnvironmentContext;
import com.snapdeal.ums.admin.sdCashBulkCredit.SDCashBulkCreditUploadResponse;
import com.snapdeal.ums.admin.sdCashBulkDebit.SDCashBulkDebitUploadRequest;
import com.snapdeal.ums.admin.sdCashBulkDebit.SDCashBulkDebitUploadResponse;
import com.snapdeal.ums.admin.sdwallet.server.services.ISDWalletServiceInternal;
import com.snapdeal.ums.core.entity.SDWalletActivityType;
import com.snapdeal.ums.server.services.impl.EmailTypeTemplateMappingService;
import com.snapdeal.ums.services.sdCashBulkUpdate.SDCashBulkDebitService;
import com.snapdeal.web.utils.WebContextUtils;



@Controller
@RequestMapping("/admin/sdCashDebit")
public class SDCashBulkDebitController {

	
	
	@Autowired
	private ISDWalletServiceInternal sdWalletService;

	private static final String UMS_ADMIN_SOURCE = "UMSAdmin_SDCashExcelUpload";

	@Autowired
	private SDCashBulkDebitService sdCashBulkDebitService;
	
	private static final Logger LOG = LoggerFactory
			.getLogger(SDCashBulkDebitController.class);
	
	@RequestMapping("/sdCashBulkDebitUpload")
	public String showSdWalletActivity(ModelMap model) {
		List<SDWalletActivityType> activityTypeList = sdWalletService
				.getAllActivityTypeData();
		model.addAttribute("activityTypeList", activityTypeList);
		return "admin/sdCashDebit/sdCashBulkDebit";

	}

	@RequestMapping(value = "processSDCashBulkDebitUpload")
	public String uploadFormActivityType(
			@RequestParam("uploadedFile") CommonsMultipartFile file,
			@ModelAttribute("sdWalletActivityTypeId") Integer id,
			ModelMap model) {

		SDCashBulkDebitUploadRequest sdCashBulkDebitUploadRequest = new SDCashBulkDebitUploadRequest();
		sdCashBulkDebitUploadRequest.setActivityType(id);
		sdCashBulkDebitUploadRequest.setFileContent(file.getBytes());
		sdCashBulkDebitUploadRequest.setFileName(file.getFileItem().getName());
		sdCashBulkDebitUploadRequest.setUploadersEmail(WebContextUtils
				.getCurrentUserEmail());
		RequestContextSRO contextSRO = new RequestContextSRO();
		contextSRO.setAppIdent(AppEnvironmentContext.getAppIdentifier());
		contextSRO.setAppIP(AppEnvironmentContext.getServerIPAddr());
		sdCashBulkDebitUploadRequest.setContextSRO(contextSRO);
		SDCashBulkDebitUploadResponse sdCashBulkDebitUploadResponse = sdCashBulkDebitService
				.processSDCashUploadRequest(sdCashBulkDebitUploadRequest,
						UMS_ADMIN_SOURCE);
		LOG.info("Activity Type Id: " + id);
		model.addAttribute("id", id);
		model.addAttribute("uploadedFile", file.getFileItem().getName());
		LOG.info("Uploaded FileName: " + file.getFileItem().getName()
				+ " of size:" + file.getSize());
		model.addAttribute("sdCashUploadResponse",
				sdCashBulkDebitUploadResponse);
		List<SDWalletActivityType> activityTypeList = sdWalletService
				.getAllActivityTypeData();
		model.addAttribute("activityTypeList", activityTypeList);
	    LOG.info("Bulk Debit Processing was Succesful.Following email id's were not processed: " + sdCashBulkDebitUploadResponse.getUnProcessedSDUserEmailIDMap());
		return "admin/sdCashDebit/sdCashBulkDebit";
	}

}
