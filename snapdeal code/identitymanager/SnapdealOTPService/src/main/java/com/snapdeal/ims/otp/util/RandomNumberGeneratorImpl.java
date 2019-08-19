package com.snapdeal.ims.otp.util;

import net.logstash.logback.encoder.org.apache.commons.lang.RandomStringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RandomNumberGeneratorImpl implements RandomNumberGenerator {

	@Value("${otp.length}")
	private Integer length;
	
	@Autowired
	private OTPUtility otpUtility ;

	@Override
	public String get() {
		if(!otpUtility.isOtpNumberFix()){
			return RandomStringUtils.randomNumeric(length);
		}
		else{
			//for testing purposes;
			return "1234" ;
		}
	}

}
