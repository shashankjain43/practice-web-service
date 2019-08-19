package com.snapdeal.ims.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.snapdeal.ims.activity.annotations.CollectActivity;
import com.snapdeal.ims.authorize.annotations.AuthorizeRequest;
import com.snapdeal.ims.common.constant.CommonConstants;
import com.snapdeal.ims.constants.RestURIConstants;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.exception.AuthorizationException;
import com.snapdeal.ims.exception.RequestParameterException;
import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.request.UpgradeUserByEmailRequest;
import com.snapdeal.ims.response.UpgradeUserByEmailResponse;
import com.snapdeal.ims.service.IJobSchedularService;
import com.snapdeal.ims.utility.EmailUtils;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.RequestAware;
import com.snapdeal.payments.metrics.annotations.Timed;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;


@RestController
@RequestMapping(RestURIConstants.USER)

public class JobSchedularController  extends AbstractController {

	@Autowired
	private IJobSchedularService jobschedularService;
	
	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@CollectActivity
	
		
	@RequestMapping(value = RestURIConstants.UPGRADE_USER_BY_EMAIL,
	produces = RestURIConstants.APPLICATION_JSON,
	method = RequestMethod.POST)
	public UpgradeUserByEmailResponse upgradeUser(
			@RequestBody @Valid UpgradeUserByEmailRequest upgradeUserByEmailRequest,
			BindingResult results, HttpServletRequest httpRequest)
					throws ValidationException, AuthorizationException {
		String emailId = new String(Base64.decodeBase64(upgradeUserByEmailRequest.getEmailId().getBytes()));
		emailId = EmailUtils.toLowerCaseEmail(emailId);
		upgradeUserByEmailRequest.setEmailId(emailId);
		return jobschedularService.upgradeUser(upgradeUserByEmailRequest);
	}
}
