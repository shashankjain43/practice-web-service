package com.snapdeal.ims.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.snapdeal.ims.authorize.annotations.AuthorizeRequest;
import com.snapdeal.ims.cache.service.ITokenCacheService;
import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.constants.RestURIConstants;
import com.snapdeal.ims.entity.UserEntity;
import com.snapdeal.ims.entity.UserSearchEnteredEntity;
import com.snapdeal.ims.enums.DiscrepencyCase;
import com.snapdeal.ims.enums.UserOtpDetailsSearchField;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.exception.AuthorizationException;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.request.GetDiscrepencyCountServiceRequest;
import com.snapdeal.ims.request.GetEmailForDiscrepencyCasesServiceRequest;
import com.snapdeal.ims.request.GetUserByEmailRequest;
import com.snapdeal.ims.request.GetUserHistoryDetailsRequest;
import com.snapdeal.ims.request.GetUserOtpDetailsRequest;
import com.snapdeal.ims.request.UserStatusRequest;
import com.snapdeal.ims.response.BlacklistEmailResponse;
import com.snapdeal.ims.response.DiscrepencyCasesEmailResponse;
import com.snapdeal.ims.response.GetDiscrepencyCountResponse;
import com.snapdeal.ims.response.GetUserHistoryDetailsResponse;
import com.snapdeal.ims.response.GetUserOtpDetailsResponse;
import com.snapdeal.ims.response.GtokenSizeResponse;
import com.snapdeal.ims.response.UserSearchResponse;
import com.snapdeal.ims.response.UserStatusResponse;
import com.snapdeal.ims.service.IBlacklistEmailService;
import com.snapdeal.ims.service.IDiscrepencyCountService;
import com.snapdeal.ims.service.IUserDetailsService;
import com.snapdeal.ims.service.IUserOtpService;
import com.snapdeal.ims.service.IUserSearchService;
import com.snapdeal.ims.service.IUserService;
import com.snapdeal.ims.utils.DateUtils;
import com.snapdeal.ims.utils.UserSearchUtil;
import com.snapdeal.payments.metrics.annotations.ExceptionMetered;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

@RestController
@RequestMapping(RestURIConstants.USER)
public class DashboardManagementController extends AbstractController {

	@Autowired
	IBlacklistEmailService blacklistEmailService;

	@Autowired
	IUserDetailsService checkUserDetailsByEmailIdService;

	@Autowired
	IDiscrepencyCountService discrepencyCountService;

	@Autowired
	IUserService userService;

	@Autowired
	private ITokenCacheService tokenCacheService;

	@Autowired
	IUserOtpService userOtpService;

	@Autowired
	IUserSearchService userSearchService;

	@RequestMapping(value = RestURIConstants.GET_BLACKLIST, produces = "application/json", method = RequestMethod.GET)
	@AuthorizeRequest
	@Timed
	@Marked
	@ExceptionMetered
	@ResponseBody
	public BlacklistEmailResponse getBlacklistEmails(
			HttpServletRequest httpRequest) {
		List<String> blacklistEmails = blacklistEmailService
				.getBlacklistEmail();
		BlacklistEmailResponse blacklistEmailResponse = new BlacklistEmailResponse();
		blacklistEmailResponse.setBlacklistEmails(blacklistEmails);
		return blacklistEmailResponse;
	}

	@RequestMapping(value = RestURIConstants.GET_STATUS, produces = "application/json", method = RequestMethod.GET)
	@AuthorizeRequest
	@Timed
	@Marked
	@ExceptionMetered
	@ResponseBody
	public UserStatusResponse getstatus(
			@PathVariable("emailId") String encodeEmailId,
			HttpServletRequest httpRequest) {
		String emailId = new String(Base64.decodeBase64(encodeEmailId
				.getBytes()));
		if (StringUtils.isBlank(emailId)) {
			throw new ValidationException(
					IMSRequestExceptionCodes.EMPTY_SEARCH_FILTER.errCode(),
					IMSRequestExceptionCodes.EMPTY_SEARCH_FILTER.errMsg());
		}
		UserStatusRequest request = new UserStatusRequest();
		request.setEmailId(emailId);
		UserStatusResponse response = new UserStatusResponse();
		response = checkUserDetailsByEmailIdService
				.getUserStateByEmail(request);

		return response;
	}

	@RequestMapping(value = RestURIConstants.GET_DISCREPENCY_COUNT, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.GET)
	@AuthorizeRequest
	@Timed
	@Marked
	@ExceptionMetered
	@ResponseBody
	public GetDiscrepencyCountResponse getDiscrepencyCountForUsers(
			@RequestParam(value = "fromDate") String fromDate,
			@RequestParam(value = "toDate") String toDate,
			HttpServletRequest httpRequest) throws ValidationException, ParseException {
		GetDiscrepencyCountServiceRequest request = new GetDiscrepencyCountServiceRequest();
		if (StringUtils.isBlank(toDate) || StringUtils.isBlank(fromDate)) {
			throw new IMSServiceException(
					IMSRequestExceptionCodes.DATE_IS_BLANK.errCode(),
					IMSRequestExceptionCodes.DATE_IS_BLANK.errMsg());
		}
		

		fromDate = new String(Base64.decodeBase64(fromDate.getBytes()));
		toDate = new String(Base64.decodeBase64(toDate.getBytes()));

		if(DateUtils.invalidDateFormat(toDate)||DateUtils.invalidDateFormat(fromDate))
		{
			throw new IMSServiceException(
					IMSRequestExceptionCodes.INVALID_DATE_FORMAT.errCode(),
					IMSRequestExceptionCodes.INVALID_DATE_FORMAT.errMsg());
		}
		
		if(DateUtils.invalidToDate(toDate))
		{
			throw new IMSServiceException(
					IMSRequestExceptionCodes.INVALID_TO_DATE.errCode(),
					IMSRequestExceptionCodes.INVALID_TO_DATE.errMsg());
		}

		Timestamp fromTimeStamp = DateUtils
				.convertToTimestamp(fromDate, "from");
		Timestamp toTimeStamp = DateUtils.convertToTimestamp(toDate, "to");
		if (DateUtils.TimestampCompare(fromTimeStamp, toTimeStamp)) {
			throw new IMSServiceException(
					IMSRequestExceptionCodes.INVALID_TO_DATE_OR_FROM_DATE
							.errCode(),
					IMSRequestExceptionCodes.INVALID_TO_DATE_OR_FROM_DATE
							.errMsg());
		}
		//allowing to extract Discrepency of at max 3 days
		
		if (toTimeStamp.getTime() - fromTimeStamp.getTime() > Long.parseLong(
				Configuration.getGlobalProperty(ConfigurationConstants.TIME_INTERVAL_VALIDATE_DISCREPENCY)))
		{
			throw new IMSServiceException(
					IMSRequestExceptionCodes.INVALID_TIMEINTERVAL
							.errCode(),
					IMSRequestExceptionCodes.INVALID_TIMEINTERVAL
							.errMsg());
			
		}
		request.setFromDate(fromTimeStamp);
		request.setToDate(toTimeStamp);
		return discrepencyCountService.getDiscrepencyCountForUsers(request);
	}

	@RequestMapping(value = RestURIConstants.GET_DISCREPENCY_LIST, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.GET )
	@AuthorizeRequest
	@Timed
	@Marked
	@ExceptionMetered
	@ResponseBody
	public DiscrepencyCasesEmailResponse getAllEmailForDiscrepenceycases(
			@RequestParam(value = "fromDate") String fromDate,
			@RequestParam(value = "toDate") String toDate,
			@RequestParam(value = "dcase") String dcase,
			HttpServletRequest httprequest) throws ValidationException, ParseException {
		GetEmailForDiscrepencyCasesServiceRequest request = new GetEmailForDiscrepencyCasesServiceRequest();
		if (StringUtils.isBlank(toDate) || StringUtils.isBlank(fromDate)) {
			throw new IMSServiceException(
					IMSRequestExceptionCodes.DATE_IS_BLANK.errCode(),
					IMSRequestExceptionCodes.DATE_IS_BLANK.errMsg());
		}
		if (StringUtils.isBlank(dcase)) {
			throw new IMSServiceException(
					IMSRequestExceptionCodes.DCASE_IS_BLANK.errCode(),
					IMSRequestExceptionCodes.DCASE_IS_BLANK.errMsg());
		}

		fromDate = new String(Base64.decodeBase64(fromDate.getBytes()));
		toDate = new String(Base64.decodeBase64(toDate.getBytes()));
		dcase = new String(Base64.decodeBase64(dcase.getBytes()));
		
		if(DateUtils.invalidDateFormat(toDate)||DateUtils.invalidDateFormat(fromDate))
		{
			throw new IMSServiceException(
					IMSRequestExceptionCodes.INVALID_DATE_FORMAT.errCode(),
					IMSRequestExceptionCodes.INVALID_DATE_FORMAT.errMsg());
		}
		
		if(DateUtils.invalidToDate(toDate))
		{
			throw new IMSServiceException(
					IMSRequestExceptionCodes.INVALID_TO_DATE.errCode(),
					IMSRequestExceptionCodes.INVALID_TO_DATE.errMsg());
		}
		
		Timestamp fromTimeStamp = DateUtils
				.convertToTimestamp(fromDate, "from");
		Timestamp toTimeStamp = DateUtils.convertToTimestamp(toDate, "to");

		if (DateUtils.TimestampCompare(fromTimeStamp, toTimeStamp)) {
			throw new IMSServiceException(
					IMSRequestExceptionCodes.INVALID_TO_DATE_OR_FROM_DATE
							.errCode(),
					IMSRequestExceptionCodes.INVALID_TO_DATE_OR_FROM_DATE
							.errMsg());
		}
		//allowing to extract Discrepency of at max 3 days
		if (toTimeStamp.getTime() - fromTimeStamp.getTime() > Long.parseLong(
				Configuration.getGlobalProperty(ConfigurationConstants.TIME_INTERVAL_VALIDATE_DISCREPENCY)))
		{
			throw new IMSServiceException(
					IMSRequestExceptionCodes.INVALID_TIMEINTERVAL
							.errCode(),
					IMSRequestExceptionCodes.INVALID_TIMEINTERVAL
							.errMsg());
			
		}
		DiscrepencyCase dCase = DiscrepencyCase.valueOf(dcase);
		request.setDCase(dCase);
		request.setFromDate(fromTimeStamp);
		request.setToDate(toTimeStamp);
		return discrepencyCountService.getAllEmailForDiscrepencyCases(request);
	}

	@RequestMapping(value = RestURIConstants.GET_TOKEN_SIZE, produces = "application/json", method = RequestMethod.GET)
	@AuthorizeRequest
	@Timed
	@Marked
	@ExceptionMetered
	@ResponseBody
	public GtokenSizeResponse getGtokenCount(
			@PathVariable("email") String encodedEmail,
			HttpServletRequest request) throws IOException, JSONException {
		GetUserByEmailRequest getUserByEmailRequest = new GetUserByEmailRequest();
		String decodedEmail = new String(Base64.decodeBase64(encodedEmail
				.getBytes()));
		if (StringUtils.isBlank(decodedEmail)) {
			throw new ValidationException(
					IMSRequestExceptionCodes.EMPTY_SEARCH_FILTER.errCode(),
					IMSRequestExceptionCodes.EMPTY_SEARCH_FILTER.errMsg());
		}
		getUserByEmailRequest.setEmailId(decodedEmail);
		GtokenSizeResponse gtokenResponse = userService
				.getGlobalTokenSizeByEmailId(getUserByEmailRequest);
		return gtokenResponse;
	}

	@RequestMapping(value = RestURIConstants.GET_OTP_DETAILS, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.GET)
	@AuthorizeRequest
	@Timed
	@Marked
	@ExceptionMetered
	@ResponseBody
	public GetUserOtpDetailsResponse getUserOtpDetails(
			@RequestParam(value = "searchField") String searchField,
			@RequestParam(value = "value") String encodedValue,
			HttpServletRequest httprequest) throws ValidationException {
		if (StringUtils.isBlank(searchField)
				|| StringUtils.isBlank(encodedValue)) {
			throw new ValidationException(
					IMSRequestExceptionCodes.EMPTY_SEARCH_FILTER.errCode(),
					IMSRequestExceptionCodes.EMPTY_SEARCH_FILTER.errMsg());
		}
		String value = new String(Base64.decodeBase64(encodedValue.getBytes()));
		UserOtpDetailsSearchField field = UserOtpDetailsSearchField
				.valueOf(searchField);
		if (field == null) {
			throw new ValidationException(
					IMSRequestExceptionCodes.SEARCHFIELD_IS_INVALID.errCode(),
					IMSRequestExceptionCodes.SEARCHFIELD_IS_INVALID.errMsg());
		}
		GetUserOtpDetailsRequest request = new GetUserOtpDetailsRequest();
		request.setValue(value);
		request.setSearchField(field);
		return userOtpService.getUserOtpDetails(request);

	}

	@RequestMapping(value = RestURIConstants.USER_SEARCH, produces = "application/json", method = RequestMethod.GET)
	@AuthorizeRequest
	@Timed
	@Marked
	@ExceptionMetered
	@ResponseBody
	public UserSearchResponse userSearch(
			@RequestParam("userId") String encodeduserId,
			@RequestParam("name") String encodedname,
			@RequestParam("email") String encodedemail,
			@RequestParam("mobile") String encodedmobile,
			@RequestParam("fromDate") String encodedfromDate,
			@RequestParam("toDate") String encodedtoDate,
			HttpServletRequest request) throws IOException, ParseException {
		UserSearchEnteredEntity userEnteredValue = UserSearchUtil
				.userSearchFilter(encodeduserId, encodedname, encodedemail,
						encodedmobile, encodedfromDate, encodedtoDate);
		if (userEnteredValue == null
				|| (userEnteredValue.getUserId() == null
						&& userEnteredValue.getSdfc_user_id() == -1
						&& userEnteredValue.getName() == null
						&& userEnteredValue.getMobile() == null
						&& userEnteredValue.getEmail() == null )) {
			throw new ValidationException(
					IMSRequestExceptionCodes.EMPTY_SEARCH_FILTER.errCode(),
					IMSRequestExceptionCodes.EMPTY_SEARCH_FILTER.errMsg());
		}
		List<UserEntity> user = userSearchService
				.getUserByBasicSearch(userEnteredValue);
		UserSearchResponse userSearchResponse = new UserSearchResponse();
		userSearchResponse.setUsers(user);
		return userSearchResponse;
	}

	@RequestMapping(value = RestURIConstants.GET_USER_HISTORY, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.GET)
	@AuthorizeRequest
	@Timed
	@Marked
	@ExceptionMetered
	@ResponseBody
	public GetUserHistoryDetailsResponse getUserHistoryDetails(
			@RequestParam("userId") String encodedUserId,
			HttpServletRequest httpRequest) throws ValidationException,
			AuthorizationException {
		if (StringUtils.isBlank(encodedUserId)) {
			throw new IMSServiceException(
					IMSRequestExceptionCodes.EMPTY_SEARCH_FILTER.errCode(),
					IMSRequestExceptionCodes.EMPTY_SEARCH_FILTER.errMsg());
		}

		String userId = new String(
				Base64.decodeBase64(encodedUserId.getBytes()));
		GetUserHistoryDetailsRequest request = new GetUserHistoryDetailsRequest();
		request.setUserId(userId);
		return userSearchService.getUserHistoryDetails(request);
	}
}
