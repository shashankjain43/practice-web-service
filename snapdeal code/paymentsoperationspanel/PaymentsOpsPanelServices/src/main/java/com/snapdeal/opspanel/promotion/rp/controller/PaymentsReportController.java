package com.snapdeal.opspanel.promotion.rp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.opspanel.audit.annotations.Audited;
import com.snapdeal.opspanel.promotion.Response.GenericResponse;
import com.snapdeal.opspanel.promotion.Response.OPSPaymentsReportGenerationResponse;
import com.snapdeal.opspanel.promotion.Response.OPSPaymentsReportGetResponse;
import com.snapdeal.opspanel.promotion.request.OPSPaymentsReportGenerationRequest;
import com.snapdeal.opspanel.promotion.request.OPSPaymentsReportGetRequest;
import com.snapdeal.opspanel.promotion.service.OPSPaymentsReportService;
import com.snapdeal.payments.roleManagementModel.commons.PreAuthorize;
import com.snapdeal.payments.sdmoneyreport.api.model.GetAllJobsRequest;
import com.snapdeal.payments.sdmoneyreport.api.model.GetJobNamesResponse;
import com.snapdeal.payments.sdmoneyreport.client.PaymentsReportClient;

@Controller
@RequestMapping("/reports/")
public class PaymentsReportController {

	@Autowired
	PaymentsReportClient reportClient;
	
	@Autowired
	OPSPaymentsReportService opsPaymentsReportService;


	@Autowired
	HttpServletRequest servletRequest;
	
	// ---------------------------------- 
	// Methods for GENERATING the reports 
	
	@Audited(context = "Reports", searchId = "request.opsPaymentsReportType", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_REPORTPANEL_CorpAccountReport'))")
	@RequestMapping(value = "/generateCorpAccountReport", method = RequestMethod.POST)
	public @ResponseBody GenericResponse generateCorpAccountReport(@RequestBody OPSPaymentsReportGenerationRequest request,
			HttpServletRequest httpServletRequest) throws Exception {	
		

		OPSPaymentsReportGenerationResponse opsPaymentsReportGenerationResponse = opsPaymentsReportService.generateReport(request,"CorpAccountReport");
		GenericResponse genericResponse = getGenericResponse(opsPaymentsReportGenerationResponse);

		return genericResponse;
	}

		
	@Audited(context = "Reports", searchId = "request.opsPaymentsReportType", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_REPORTPANEL_ERPReport'))")
	@RequestMapping(value = "/generateERPReport", method = RequestMethod.POST)
	public @ResponseBody GenericResponse generateErpReport(@RequestBody OPSPaymentsReportGenerationRequest request,
			HttpServletRequest httpServletRequest) throws Exception {
		

		OPSPaymentsReportGenerationResponse opsPaymentsReportGenerationResponse = opsPaymentsReportService.generateReport(request,"ERPReport");
		GenericResponse genericResponse = getGenericResponse(opsPaymentsReportGenerationResponse);

		return genericResponse;
	}

	@Audited(context = "Reports", searchId = "request.opsPaymentsReportType", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_REPORTPANEL_MirrorAccountReport'))")
	@RequestMapping(value = "/generateMirrorAccountReport", method = RequestMethod.POST)
	public @ResponseBody GenericResponse generateMirrorAccountReport(@RequestBody OPSPaymentsReportGenerationRequest request,
			HttpServletRequest httpServletRequest) throws Exception {

		OPSPaymentsReportGenerationResponse opsPaymentsReportGenerationResponse = opsPaymentsReportService.generateReport(request,"MirrorAccountReport");
		GenericResponse genericResponse = getGenericResponse(opsPaymentsReportGenerationResponse);

		return genericResponse;
	}

	@Audited(context = "Reports", searchId = "request.opsPaymentsReportType", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_REPORTPANEL_IMPSReport'))")
	@RequestMapping(value = "/generateIMPSReport", method = RequestMethod.POST)
	public @ResponseBody GenericResponse generateImpsReport(@RequestBody OPSPaymentsReportGenerationRequest request,
			HttpServletRequest httpServletRequest) throws Exception {
		

		OPSPaymentsReportGenerationResponse opsPaymentsReportGenerationResponse = opsPaymentsReportService.generateReport(request,"IMPSReport");
		GenericResponse genericResponse = getGenericResponse(opsPaymentsReportGenerationResponse);

		return genericResponse;
	}

	@Audited(context = "Reports", searchId = "request.opsPaymentsReportType", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_REPORTPANEL_WalletReport'))")
	@RequestMapping(value = "/generateWalletReport", method = RequestMethod.POST)
	public @ResponseBody GenericResponse generateWalletReport(@RequestBody OPSPaymentsReportGenerationRequest request,
			HttpServletRequest httpServletRequest) throws Exception {
		

		OPSPaymentsReportGenerationResponse opsPaymentsReportGenerationResponse = opsPaymentsReportService.generateReport(request,"WalletReport");
		GenericResponse genericResponse = getGenericResponse(opsPaymentsReportGenerationResponse);

		return genericResponse;
	}

	@Audited(context = "Reports", searchId = "request.opsPaymentsReportType", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_REPORTPANEL_RBIReport'))")
	@RequestMapping(value = "/generateRBIReport", method = RequestMethod.POST)
	public @ResponseBody GenericResponse generateRbiReport(@RequestBody OPSPaymentsReportGenerationRequest request,
			HttpServletRequest httpServletRequest) throws Exception {
		

		OPSPaymentsReportGenerationResponse opsPaymentsReportGenerationResponse = opsPaymentsReportService.generateReport(request,"RBIReport");
		GenericResponse genericResponse = getGenericResponse(opsPaymentsReportGenerationResponse);

		return genericResponse;
	}


	// ---------------------------------- 

	// ---------------------------------- 
	// Methods for GETTING the GENERATED reports 
	
	@Audited(context = "Reports", searchId = "request.opsPaymentsReportType", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_REPORTPANEL_CorpAccountReport'))")
	@RequestMapping(value = "/getCorpAccountReport", method = RequestMethod.POST)
	public @ResponseBody GenericResponse getCorpAccountReport(@RequestBody OPSPaymentsReportGetRequest request,
			HttpServletRequest httpServletRequest) throws Exception {	


		OPSPaymentsReportGetResponse opsPaymentsReportGetResponse = opsPaymentsReportService.getReport(request,"CorpAccountReport"); 
		GenericResponse genericResponse = getGenericResponse(opsPaymentsReportGetResponse);
		return genericResponse;
	}

	@Audited(context = "Reports", searchId = "request.opsPaymentsReportType", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_REPORTPANEL_ERPReport'))")
	@RequestMapping(value = "/getERPReport", method = RequestMethod.POST)
	public @ResponseBody GenericResponse getErpReport(@RequestBody OPSPaymentsReportGetRequest request,
			HttpServletRequest httpServletRequest) throws Exception {		
		

		OPSPaymentsReportGetResponse opsPaymentsReportGetResponse = opsPaymentsReportService.getReport(request,"ERPReport"); 
		GenericResponse genericResponse = getGenericResponse(opsPaymentsReportGetResponse);
		return genericResponse;
	}

	@Audited(context = "Reports", searchId = "request.opsPaymentsReportType", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_REPORTPANEL_MirrorAccountReport'))")
	@RequestMapping(value = "/getMirrorAccountReport", method = RequestMethod.POST)
	public @ResponseBody GenericResponse getMirrorAccountReport(@RequestBody OPSPaymentsReportGetRequest request,
			HttpServletRequest httpServletRequest) throws Exception {
		

		OPSPaymentsReportGetResponse opsPaymentsReportGetResponse = opsPaymentsReportService.getReport(request,"MirrorAccountReport"); 
		GenericResponse genericResponse = getGenericResponse(opsPaymentsReportGetResponse);
		return genericResponse;
	}

	@Audited(context = "Reports", searchId = "request.opsPaymentsReportType", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_REPORTPANEL_IMPSReport'))")
	@RequestMapping(value = "/getIMPSReport", method = RequestMethod.POST)
	public @ResponseBody GenericResponse getImpsReport(@RequestBody OPSPaymentsReportGetRequest request,
			HttpServletRequest httpServletRequest) throws Exception {
		
		OPSPaymentsReportGetResponse opsPaymentsReportGetResponse = opsPaymentsReportService.getReport(request,"IMPSReport"); 
		GenericResponse genericResponse = getGenericResponse(opsPaymentsReportGetResponse);
		return genericResponse;
	}

	@Audited(context = "Reports", searchId = "request.opsPaymentsReportType", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_REPORTPANEL_WalletReport'))")
	@RequestMapping(value = "/getWalletReport", method = RequestMethod.POST)
	public @ResponseBody GenericResponse getWalletReport(@RequestBody OPSPaymentsReportGetRequest request,
			HttpServletRequest httpServletRequest) throws Exception {
		
		OPSPaymentsReportGetResponse opsPaymentsReportGetResponse = opsPaymentsReportService.getReport(request,"WalletReport"); 
		GenericResponse genericResponse = getGenericResponse(opsPaymentsReportGetResponse);
		return genericResponse;
	}

	@Audited(context = "Reports", searchId = "request.opsPaymentsReportType", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_REPORTPANEL_RBIReport'))")
	@RequestMapping(value = "/getRBIReport", method = RequestMethod.POST)
	public @ResponseBody GenericResponse getRbiReport(@RequestBody OPSPaymentsReportGetRequest request,
			HttpServletRequest httpServletRequest) throws Exception {
		

		OPSPaymentsReportGetResponse opsPaymentsReportGetResponse = opsPaymentsReportService.getReport(request,"RBIReport"); 
		GenericResponse genericResponse = getGenericResponse(opsPaymentsReportGetResponse);
		return genericResponse;
	}
	

	// ----------------------------------
	
	// ----------------------------------
	// Controller to test whether Reports Server is down or up

	@RequestMapping(value = "/getAllJobs", method = RequestMethod.POST)
	public @ResponseBody GenericResponse getAllJobs(@RequestBody GetAllJobsRequest request,
			HttpServletRequest httpServletRequest) throws Exception {
		
		GetJobNamesResponse response = null;
		response = reportClient.getAllJobs(request);
		GenericResponse genericResponse = getGenericResponse(response);
		return genericResponse;
	}
	
	
	
	// ----------------------------------
	
	GenericResponse getGenericResponse(Object reportsResponse) {
		GenericResponse genericResponse = new GenericResponse();
		genericResponse.setError(null);
		genericResponse.setData(reportsResponse);
		return genericResponse;
	}

}
