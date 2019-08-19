package com.snapdeal.ims.otp.service.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.fcNotifier.client.impl.NotifierServiceClient;
import com.snapdeal.fcNotifier.reponse.CallResponse;
import com.snapdeal.fcNotifier.request.CallNumberNotifierRequest;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.SendOTPByEnum;
import com.snapdeal.ims.errorcodes.IMSDefaultExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.otp.entity.UserOTPEntity;
import com.snapdeal.ims.otp.util.OTPUtility;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CallNotifierServiceDelegetor {

	@Autowired
	private OTPUtility otpUtility;

	@Autowired
	private NotifierServiceClient fcNotifier;
/**
 * send call request to fcNotifier 
 * if it is enabled 
 * @author radhika.dhingra
 * @param otpInfo
 * @param merchant
 */
	public void callNumber(UserOTPEntity otpInfo, Merchant merchant) 
	{
		try {
			boolean isFCNotifierEnabled = otpUtility.isFcNotifierEnabled();
			log.info("SendOTPbyEnum is : " + otpInfo.getSendOTPBy());
			if (SendOTPByEnum.forValue(otpInfo.getSendOTPBy()) == SendOTPByEnum.FREECHARGE) {
				if (isFCNotifierEnabled) {
					callNumberByFCNotifer(otpInfo,merchant);
				} else {
					log.info("fc notifier is disabled");
				}
			} 
		} catch (Exception e) {
			log.error("Sending call failed exception for mobile  : " + otpInfo.getMobileNumber() + " ::  " + e);
			throw new IMSServiceException(
					IMSDefaultExceptionCodes.INTERNAL_SERVER.errCode(),
					IMSDefaultExceptionCodes.INTERNAL_SERVER.errMsg());
		}
	}
/**
 * Perform call via fcNotifier Service
 * @author radhika.dhingra
 * @param otpInfo
 * @param merchant
 * @return CallResponse
 * @throws Exception
 */
	private CallResponse callNumberByFCNotifer(UserOTPEntity otpInfo,Merchant merchant)
			throws Exception {
		CallNumberNotifierRequest callRequest = new CallNumberNotifierRequest();
		callRequest.setMobileNumber(otpInfo.getMobileNumber());
		callRequest.setOtp(otpInfo.getOtp());
		callRequest.setOtpPurpose(otpInfo.getOtpType());


		setMobileNumberAsTestMobileIfApplicableForCall(callRequest);

		CallResponse callResponse = fcNotifier.callNumber(callRequest);
		log.debug("callResponse:" + callResponse);
		return callResponse;

	}
	
	private void setMobileNumberAsTestMobileIfApplicableForCall(CallNumberNotifierRequest callRequest) {
		if (otpUtility.isSendCallOnTestMobile()) {
			log.debug("Sending call to test mobile number instead of : "
					+ callRequest.getMobileNumber());
			callRequest.setMobileNumber(otpUtility.getTestMobileNumber());
		}
	}

}
