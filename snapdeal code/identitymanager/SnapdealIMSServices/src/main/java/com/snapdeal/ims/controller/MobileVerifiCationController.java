package com.snapdeal.ims.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

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
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.exception.AuthorizationException;
import com.snapdeal.ims.exception.RequestParameterException;
import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.request.CreateUserMobileVerifyRequest;
import com.snapdeal.ims.request.MobileVerificationStatusRequest;
import com.snapdeal.ims.request.UniqueMobileVerificationRequest;
import com.snapdeal.ims.response.CreateUserResponse;
import com.snapdeal.ims.response.MobileVerificationStatusResponse;
import com.snapdeal.ims.response.UniqueMobileVerificationResponse;
import com.snapdeal.ims.service.IMobileVerificationService;
import com.snapdeal.payments.metrics.annotations.ExceptionMetered;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.RequestAware;
import com.snapdeal.payments.metrics.annotations.Timed;

@Slf4j
@RestController
@RequestMapping(value = RestURIConstants.OTP)
public class MobileVerifiCationController extends AbstractController {

   @Autowired
   private IMobileVerificationService mobileVerificationService;

   @Timed
	@Marked
	@RequestAware
   @AuthorizeRequest
   @ExceptionMetered
   @RequestMapping(value = RestURIConstants.MOBILE_VERIFY, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
   public UniqueMobileVerificationResponse verifyUniqueMobile(
            @Valid @RequestBody UniqueMobileVerificationRequest request, BindingResult results,
            HttpServletRequest httpRequest) {
      if (results.hasErrors() && null != results.getAllErrors()) {
         IMSRequestExceptionCodes code = IMSRequestExceptionCodes.valueOf(results.getAllErrors()
                  .get(0).getDefaultMessage());

         log.error("Invalid Request Error occured while verifing the mobile.");
         throw new RequestParameterException(code.errCode(), code.errMsg());
      }

      return mobileVerificationService.verifyUniqueMobile(request);

   }

   @Timed
	@Marked
	@RequestAware
   @AuthorizeRequest
   @ExceptionMetered
   @RequestMapping(value = RestURIConstants.MOBILE_VERIFICATION_STATUS, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.GET)
   public MobileVerificationStatusResponse mobileVerificationStatus(
            @PathVariable("userId") String userId, HttpServletRequest httpRequest) {

      if (StringUtils.isBlank(userId)) {
         IMSRequestExceptionCodes code = IMSRequestExceptionCodes.USER_ID_IS_BLANK;
         log.error("user Id is blank or null");
         throw new RequestParameterException(code.errCode(), code.errMsg());
      }
      if (StringUtils.length(userId) >= 127) {
         IMSRequestExceptionCodes code = IMSRequestExceptionCodes.USER_ID_MAX_LENGTH;
         throw new RequestParameterException(code.errCode(), code.errMsg());
      }

      final MobileVerificationStatusRequest request = new MobileVerificationStatusRequest();
      request.setUserId(userId);
      return mobileVerificationService.isMobileVerified(request);
   }
}
