package com.snapdeal.ims.service.impl;

import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.common.constant.CommonConstants;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.exception.RequestParameterException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IMSServiceUtil {

   protected static void validateEmail(String emailId) {

      if (!EmailValidator.getInstance().isValid(emailId)) {

         IMSRequestExceptionCodes code = IMSRequestExceptionCodes.EMAIL_FORMAT_INCORRECT;
         throw new RequestParameterException(code.errCode(), code.errMsg());
      }
   }

   // Temporary code to validate DOB, in new release dob check is done in
   // request parameter.
   protected static Date getAndValidateDOB(String dob, boolean manadatory) {

      if (!manadatory && StringUtils.isBlank(dob)) {
         return null;
      }

      SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
      sdf.setLenient(false);
      Date dateOfBirth;
      try {
         dateOfBirth = sdf.parse(dob);
      } catch (ParseException e) {
         log.error("DOB is a invalid date:" + dob + ", default date format: "
                  + CommonConstants.DATE_FORMAT);
         throw new RequestParameterException(IMSRequestExceptionCodes.INVALID_DOB.errCode(),
                  IMSRequestExceptionCodes.INVALID_DOB.errMsg());
      }

      if (dateOfBirth.getTime() > Calendar.getInstance().getTimeInMillis()) {
         throw new RequestParameterException(IMSRequestExceptionCodes.INVALID_DOB.errCode(),
                  IMSRequestExceptionCodes.INVALID_DOB.errMsg());
      }

      Calendar calendar18YrsBefore = Calendar.getInstance();
      calendar18YrsBefore.add(Calendar.YEAR, -18);

      if (dateOfBirth.after(calendar18YrsBefore.getTime())) {
         throw new RequestParameterException(IMSRequestExceptionCodes.UNDER_AGED.errCode(),
                  IMSRequestExceptionCodes.UNDER_AGED.errMsg());
      }
      return dateOfBirth;
   }

   public static Date getAndValidateDOB(String dob) {

      if (null == dob) {
         return null;
      }
      SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
      sdf.setLenient(false);
      Date dateOfBirth = null;
      try {
         dateOfBirth = sdf.parse(dob);
      } catch (ParseException e) {
         log.error("DOB is a invalid date:" + dob + ", default date format: "
                  + CommonConstants.DATE_FORMAT);
         throw new RequestParameterException(IMSRequestExceptionCodes.INVALID_DOB.errCode(),
                  IMSRequestExceptionCodes.INVALID_DOB.errMsg());
      }

      if (dateOfBirth.getTime() > Calendar.getInstance().getTimeInMillis()) {
         throw new RequestParameterException(IMSRequestExceptionCodes.INVALID_DOB.errCode(),
                  IMSRequestExceptionCodes.INVALID_DOB.errMsg());
      }

      Calendar calendar18YrsBefore = Calendar.getInstance();
      calendar18YrsBefore.add(Calendar.YEAR, -18);

      if (dateOfBirth.after(calendar18YrsBefore.getTime())) {
         throw new RequestParameterException(IMSRequestExceptionCodes.UNDER_AGED.errCode(),
                  IMSRequestExceptionCodes.UNDER_AGED.errMsg());
      }
      return dateOfBirth;
   }

   public static Date getAndValidateDOBForSocialUser(String dob) {

      if (StringUtils.isBlank(dob)) {
         return null;
      }
      SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
      sdf.setLenient(false);
      Date dateOfBirth = null;
      try {
         dateOfBirth = sdf.parse(dob);
      } catch (ParseException e) {
         log.error("DOB is a invalid date:" + dob + ", default date format: "
                  + CommonConstants.DATE_FORMAT);
         throw new RequestParameterException(IMSRequestExceptionCodes.INVALID_DOB.errCode(),
                  IMSRequestExceptionCodes.INVALID_DOB.errMsg());
      }

      if (dateOfBirth.getTime() > Calendar.getInstance().getTimeInMillis()) {
         throw new RequestParameterException(IMSRequestExceptionCodes.INVALID_DOB.errCode(),
                  IMSRequestExceptionCodes.INVALID_DOB.errMsg());
      }

      Calendar calendar18YrsBefore = Calendar.getInstance();
      calendar18YrsBefore.add(Calendar.YEAR, -18);

      if (dateOfBirth.after(calendar18YrsBefore.getTime())) {
         /*
          * Note: For social user, since user is already verified using social source, 
          * putting age null to avoid un-necessary exceptions
          * */
         return null;
      }
      return dateOfBirth;
   }
   
   public static String getEmailTemplateString(String clientId,
            ConfigurationConstants configConstants) {

      String templateHTMLStr = Configuration.getClientProperty(clientId, configConstants);
      if (StringUtils.isBlank(templateHTMLStr)) {
         templateHTMLStr = Configuration.getGlobalProperty(configConstants);
      }
      return templateHTMLStr;
   }

   protected static void printUMSObject(String type, Object sro) {

      log.debug("UMS " + type + " :");

      if (log.isDebugEnabled()) {
         if (sro == null) {
            log.debug("sro is null ");
            return;
         }
         StringBuilder sb = new StringBuilder();

         Field[] fields = sro.getClass().getDeclaredFields();
         sb.append(sro.getClass().getSimpleName() + " [ ");

         for (Field f : fields) {
            String fName = f.getName();
            f.setAccessible(true);
            try {
               if (fName == "serialVersionUID" || fName == "password")
                  continue;
               sb.append(fName + " = " + f.get(sro) + ", ");
            } catch (IllegalArgumentException e) {
               log.debug(e.getMessage());
            } catch (IllegalAccessException e) {
               log.debug(e.getMessage());
            }
         }
         sb.append("]");

         log.debug(sro.toString());
         log.debug(sb.toString());
      }
   }   
}
