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
import com.snapdeal.ums.admin.sdCashBulkCredit.SDCashBulkCreditUploadRequest;
import com.snapdeal.ums.admin.sdCashBulkCredit.SDCashBulkCreditUploadResponse;
import com.snapdeal.ums.admin.sdwallet.server.services.ISDWalletServiceInternal;
import com.snapdeal.ums.core.entity.SDWalletActivityType;
import com.snapdeal.ums.server.services.impl.EmailTypeTemplateMappingService;
import com.snapdeal.ums.services.sdCashBulkUpdate.SDCashBulkCreditService;
import com.snapdeal.web.utils.WebContextUtils;

@Controller
@RequestMapping("/admin/sdCash")
public class SDCashBulkCreditController {
	@Autowired
	private ISDWalletServiceInternal sdWalletService;

	private static final String UMS_ADMIN_SOURCE = "UMSAdmin_SDCashExcelUpload";
	
	private static final String EMAIL_TYPE_TEMPLATE="sdCashBulkCreditCustomerTemplate";

	@Autowired
	private SDCashBulkCreditService sdCashBulkCreditService;
	
	@Autowired
	private EmailTypeTemplateMappingService emailTypeTemplateMappingService;

	private static final Logger log = LoggerFactory
			.getLogger(SDCashBulkCreditController.class);

	@RequestMapping("/sdCashBulkCreditUpload")
	public String showSdWalletActivity(ModelMap model) {
		List<SDWalletActivityType> activityTypeList = sdWalletService
				.getAllActivityTypeData();
		model.addAttribute("activityTypeList", activityTypeList);
		//Calling the service to set drop down of list of email templates mapped by email type
		List<String> emailTemplateNameList= emailTypeTemplateMappingService.getListOfEmailTemplateName(EMAIL_TYPE_TEMPLATE);
		model.addAttribute("emailTemplateNameList", emailTemplateNameList);
		return "admin/sdCash/sdCashBulkCredit";

	}

	@RequestMapping(value = "processSDCashBulkCreditUpload", method = RequestMethod.POST)
	public String uploadFormActivityType(
			@RequestParam("uploadedFile") CommonsMultipartFile file,
			@ModelAttribute("sdWalletActivityTypeId") Integer id,
			ModelMap model, @RequestParam("templateName") String templateName) {

		SDCashBulkCreditUploadRequest sdCashBulkCreditUploadRequest = new SDCashBulkCreditUploadRequest();
		if (id == null || id.toString().isEmpty()) {
			SDCashBulkCreditUploadResponse sdCashUploadResponse = new SDCashBulkCreditUploadResponse();

		} else {
			sdCashBulkCreditUploadRequest.setActivityType(id);
		}
		sdCashBulkCreditUploadRequest.setFileContent(file.getBytes());
		sdCashBulkCreditUploadRequest.setFileName(file.getFileItem().getName());
		sdCashBulkCreditUploadRequest.setEmailTemplateName(templateName);
		sdCashBulkCreditUploadRequest.setUploadersEmail(WebContextUtils
				.getCurrentUserEmail());
		RequestContextSRO contextSRO = new RequestContextSRO();
		contextSRO.setAppIdent(AppEnvironmentContext.getAppIdentifier());
		contextSRO.setAppIP(AppEnvironmentContext.getServerIPAddr());
		sdCashBulkCreditUploadRequest.setContextSRO(contextSRO);
		

		SDCashBulkCreditUploadResponse sdCashBulkCreditUploadResponse = sdCashBulkCreditService
				.processSDCashUploadRequest(sdCashBulkCreditUploadRequest,
						UMS_ADMIN_SOURCE);

		log.info("Activity Type Id: " + id);
		log.info("emailTemplateName: " + templateName);
		
		model.addAttribute("id", id);
		model.addAttribute("uploadedFile", file.getFileItem().getName());
		log.info("Uploaded FileName: " + file.getFileItem().getName()
				+ " of size:" + file.getSize());
		
		model.addAttribute("TemplateName", templateName);
		model.addAttribute("sdCashUploadResponse",
				sdCashBulkCreditUploadResponse);
		
		List<String> emailTemplateNameList= emailTypeTemplateMappingService.getListOfEmailTemplateName(EMAIL_TYPE_TEMPLATE);
		model.addAttribute("emailTemplateNameList", emailTemplateNameList);
		
		List<SDWalletActivityType> activityTypeList = sdWalletService
				.getAllActivityTypeData();
		model.addAttribute("activityTypeList", activityTypeList);
	

		return "admin/sdCash/sdCashBulkCredit";
	}
}