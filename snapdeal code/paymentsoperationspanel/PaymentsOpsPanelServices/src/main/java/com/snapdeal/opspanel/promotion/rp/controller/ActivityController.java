package com.snapdeal.opspanel.promotion.rp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.ims.entity.Activity;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.GetActivityRequest;
import com.snapdeal.ims.response.GetActivityResponse;
import com.snapdeal.opspanel.audit.annotations.Audited;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.opspanel.userpanel.request.ActivityRequest;
import com.snapdeal.opspanel.userpanel.response.GenericResponse;
import com.snapdeal.opspanel.userpanel.service.SearchUserServices;
import com.snapdeal.opspanel.userpanel.utils.ActivityLogUtils;
import com.snapdeal.opspanel.userpanel.utils.GenericUtils;
import com.snapdeal.payments.roleManagementModel.commons.PreAuthorize;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("activity/")
@Slf4j
public class ActivityController {

	@Autowired
	SearchUserServices searchUserServices;

	@Audited(context = "Search", searchId = "", reason = "", skipRequestKeys = {}, skipResponseKeys = {},viewable=0)
	@PreAuthorize("(hasPermission('OPS_USERPANEL_view') or hasPermission('OPS_IMS_VIEW'))")
	@RequestMapping(value = "viewactivitylogs", method = RequestMethod.POST)
	public @ResponseBody GenericResponse getActivityLogs(@RequestBody ActivityRequest activityRequest)
			throws OpsPanelException, InfoPanelException, ServiceException {
		if (activityRequest.getFromDate() == null || activityRequest.getToDate() == null) {
			throw new OpsPanelException("ACTL-1001", "Start date or End Date can not be left null");
		}
		ActivityLogUtils.validateDates(activityRequest.getFromDate(), activityRequest.getToDate(), 10);
		GetActivityRequest getActivityRequest = new GetActivityRequest();
		getActivityRequest.setEntityId(activityRequest.getEntityId());
		getActivityRequest.setEntityType(activityRequest.getEntityType());
		getActivityRequest.setFromDate(activityRequest.getFromDate());
		getActivityRequest.setToDate(activityRequest.getToDate());
		log.info("Going to hit getActivityLogs with request : "+ getActivityRequest.toString());
		GetActivityResponse response = searchUserServices.getActivity(getActivityRequest);
		return GenericUtils.getGenericResponse(response);

	}

	@Audited(context = "Search", searchId = "", reason = "", skipRequestKeys = {}, skipResponseKeys = {},viewable=0)
	@PreAuthorize("(hasPermission('OPS_USERPANEL_view') or hasPermission('OPS_IMS_VIEW'))")
	@RequestMapping(value = "downloadactivitylogs", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity downloadActivityLogs(@RequestBody ActivityRequest activityRequest)
			throws OpsPanelException, InfoPanelException, ServiceException {
		if (activityRequest.getFromDate() == null || activityRequest.getToDate() == null) {
			throw new OpsPanelException("ACTL-1001", "Start date or End Date can not be left null");
		}
		ActivityLogUtils.validateDates(activityRequest.getFromDate(), activityRequest.getToDate(), 10);
		GetActivityRequest getActivityRequest = new GetActivityRequest();
		getActivityRequest.setEntityId(activityRequest.getEntityId());
		getActivityRequest.setEntityType(activityRequest.getEntityType());
		getActivityRequest.setFromDate(activityRequest.getFromDate());
		getActivityRequest.setToDate(activityRequest.getToDate());
		log.info("Going to hit getActivityLogs with request : "+ getActivityRequest.toString());
		GetActivityResponse response = searchUserServices.getActivity(getActivityRequest);
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-disposition",
				"attachment; filename=" + activityRequest.getEntityId() + "_activity_info.csv");
		headers.add("Content-Type", "text/csv");
		StringBuffer sb = ActivityLogUtils.getBufferForCSV(response);
		return new ResponseEntity(sb.toString().getBytes(), headers, HttpStatus.OK);

	}

}
