package com.snapdeal.ims.otp.email.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.errorcodes.IMSDefaultExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.otp.constants.OtpConstants;
import com.snapdeal.ims.otp.email.IEmailSender;
import com.snapdeal.ims.otp.email.ISnapdealEmailSender;
import com.snapdeal.ims.otp.entity.UserOTPEntity;
import com.snapdeal.ims.otp.internal.request.EmailInfo;
import com.snapdeal.ims.otp.service.provider.EmailNotiferServiceDelegater;
import com.snapdeal.ims.otp.util.OTPUtility;
import com.snapdeal.notifier.email.request.EmailMessage;
import com.snapdeal.notifier.service.Notifier;

@Service
@Slf4j
public class EmailSenderImpl implements IEmailSender {

	@Autowired
	private ISnapdealEmailSender emailSender;
	
	@Autowired
	private OTPUtility  otpUtility;
	
	@Autowired
	private Notifier notifier;
	
	@Autowired
	private EmailNotiferServiceDelegater emailNotifer ;

	@Override
	public void send(UserOTPEntity otp,Merchant merchant,String name) {
		emailNotifer.sendEmail(otp,merchant,name);
	}
	/*public String getEmailTemplateKey(String merchant,
            ConfigurationConstants configConstants) {
      
      String template =  Configuration.getClientProperty(merchant, configConstants);  
      if(!StringUtils.isBlank(template)){
         return merchant + "." + configConstants.getKey();
      }
      return "global." + configConstants.getKey();
   }*/
	//sending email 
}
