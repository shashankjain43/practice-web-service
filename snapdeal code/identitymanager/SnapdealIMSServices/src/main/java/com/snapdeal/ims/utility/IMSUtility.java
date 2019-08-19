package com.snapdeal.ims.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.common.constant.CommonConstants;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.exception.RequestParameterException;

@Service
@Slf4j
public class IMSUtility {
	
	public Date getAndValidateDOB(String dob) {

		if(StringUtils.isBlank(dob))
			return null;
		
		SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
		sdf.setLenient(false);
		Date dateOfBirth;
		try {
			dateOfBirth = sdf.parse(dob);
		} catch (ParseException e) {
			log.error("DOB is a invalid date:"
					+ dob + ", default date format: "
					+ CommonConstants.DATE_FORMAT);
			throw new RequestParameterException(IMSRequestExceptionCodes.INVALID_DOB.errCode(), 
												IMSRequestExceptionCodes.INVALID_DOB.errMsg());
		}
		
		if(dateOfBirth.getTime()>Calendar.getInstance().getTimeInMillis()){
			throw new RequestParameterException(IMSRequestExceptionCodes.INVALID_DOB.errCode(), 
					IMSRequestExceptionCodes.INVALID_DOB.errMsg());
		}
		return dateOfBirth;
	}
	
	 public String getEmailTemplateKey(String merchant,
	            ConfigurationConstants configConstants) {
	      
	      String template =  Configuration.getClientProperty(merchant, configConstants);  
	      if(!StringUtils.isBlank(template)){
	         return merchant + "." + configConstants.getKey();
	      }
	      return "global." + configConstants.getKey();
	   }
	   
	   public String getEmailSubject(String merchant,
	            ConfigurationConstants configConstants) {
	      
	      String subject =  Configuration.getClientProperty(merchant, configConstants);  
	      if(StringUtils.isBlank(subject)){
	         subject = Configuration.getGlobalProperty(configConstants);
	      }
	      return subject;
	   }
}
