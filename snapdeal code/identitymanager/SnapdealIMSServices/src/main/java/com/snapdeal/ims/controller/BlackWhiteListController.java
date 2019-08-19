package com.snapdeal.ims.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.snapdeal.ims.activity.annotations.CollectActivity;
import com.snapdeal.ims.authorize.annotations.AuthorizeRequest;
import com.snapdeal.ims.constants.RestURIConstants;
import com.snapdeal.ims.enums.EntityType;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.exception.AuthorizationException;
import com.snapdeal.ims.exception.RequestParameterException;
import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.request.BlacklistEntityRequest;
import com.snapdeal.ims.request.WhitelistEmailRequest;
import com.snapdeal.ims.response.BlacklistEntityResponse;
import com.snapdeal.ims.response.WhitelistEmailResponse;
import com.snapdeal.ims.service.IBlackWhiteListService;
import com.snapdeal.ims.utility.EmailUtils;
import com.snapdeal.payments.metrics.annotations.ExceptionMetered;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.RequestAware;
import com.snapdeal.payments.metrics.annotations.Timed;

@Slf4j
@RestController
@RequestMapping(RestURIConstants.USER)
public class BlackWhiteListController extends AbstractController {

	@Autowired
	private IBlackWhiteListService blackWhiteListService;

	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@CollectActivity
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.WHITELIST_EMAIL, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public WhitelistEmailResponse WhitelistEmail(
			@RequestBody @Valid WhitelistEmailRequest request,
			BindingResult results, HttpServletRequest httpRequest)
			throws ValidationException, AuthorizationException {

		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while whiltelist email");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}
		request.setEmailId(EmailUtils.toLowerCaseEmail(request.getEmailId()));
		return blackWhiteListService.WhitelistEmail(request);
	}

	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@CollectActivity
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.BLACKLIST_ENTITY, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public BlacklistEntityResponse addBlacklistEntity(
			@RequestBody @Valid BlacklistEntityRequest request,
			BindingResult results, HttpServletRequest httpRequest) {
		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while blacklisting an entity");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}
		request.setEntity(EmailUtils.toLowerCaseEmail(request.getEntity()));
		return blackWhiteListService.addBlacklistEntity(request);
	}

	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@CollectActivity
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.BLACKLIST_ENTITY_REMOVE, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.DELETE)
	public BlacklistEntityResponse removeBlacklistEntity(
			@PathVariable("entityType") String entityType,
			@PathVariable("entity") String entity,
			HttpServletRequest httpRequest) {
		if (StringUtils.isBlank(entityType)) {
			throw new RequestParameterException(
					IMSRequestExceptionCodes.BLACKLIST_ENTITY_TYPE_IS_BLANK
							.errCode(),
					IMSRequestExceptionCodes.BLACKLIST_ENTITY_TYPE_IS_BLANK
							.errMsg());
		}

		if (StringUtils.isBlank(entity)) {
			throw new RequestParameterException(
					IMSRequestExceptionCodes.BLACKLIST_ENTITY_IS_BLANK
							.errCode(),
					IMSRequestExceptionCodes.BLACKLIST_ENTITY_IS_BLANK.errMsg());
		}
		String decodedEntity = new String(
				Base64.decodeBase64(entity.getBytes()));
		BlacklistEntityRequest request = new BlacklistEntityRequest();
		request.setBlackListType(EntityType.forName(entityType));
		request.setEntity(EmailUtils.toLowerCaseEmail(decodedEntity));
		return blackWhiteListService.removeBlacklistEntity(request);
	}
}
