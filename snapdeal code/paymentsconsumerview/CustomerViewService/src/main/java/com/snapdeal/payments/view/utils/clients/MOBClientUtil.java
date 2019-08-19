package com.snapdeal.payments.view.utils.clients;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.snapdeal.mob.client.impl.MerchantServicesImpl;
import com.snapdeal.mob.exception.ServiceException;
import com.snapdeal.mob.request.GetMerchantDetailsByMerchantIdRequest;
import com.snapdeal.mob.response.GetMerchantDetails;
import com.snapdeal.mob.utils.ClientDetails;
import com.snapdeal.payments.view.commons.enums.ExceptionType;
import com.snapdeal.payments.view.commons.exception.PaymentsViewServiceException;
import com.snapdeal.payments.view.commons.exception.codes.PaymentsViewServiceExceptionCodes;

@Slf4j
@Component("merchantOnBoardingClientUtil")
public class MOBClientUtil {


private MerchantServicesImpl merchantServiceImpl = null;
   
   @Value("${com.snapdeal.payments.merchant.onboarding.clientName}")
	public String merchantOnBoardingClientName ;
	
	@Value("${com.snapdeal.payments.merchant.onboarding.ip}")
	public String merchantOnBoardingIP ;
	
	@Value("${com.snapdeal.payments.merchant.onboarding.port}")
	public String merchantOnboardingPort ;
	
	private int merchantOnboardingApiTimeOut=15000;


   @PostConstruct
   public void getMerchantServiceClient() {
      try {
         ClientDetails.init(merchantOnBoardingClientName,
        		 			merchantOnBoardingIP,
        		 			merchantOnboardingPort,
        		 			merchantOnboardingApiTimeOut);
         merchantServiceImpl = new MerchantServicesImpl();
      } catch (Exception e) {
         // TODO Auto-generated catch block
         log.info(e.getMessage());
         throw new PaymentsViewServiceException(
                  PaymentsViewServiceExceptionCodes.UNABLE_TO_CONNECT_TO_MERCHANT_ONBOARDING.errCode(),
                           e.getMessage(),
                           ExceptionType.MERCHANT_ON_BOARDING);
      }
      merchantServiceImpl = new MerchantServicesImpl();
   }

	public GetMerchantDetails getMerchantDisplayInfoById(String merchantId) {
		GetMerchantDetailsByMerchantIdRequest req = new GetMerchantDetailsByMerchantIdRequest();
		req.setMerchantId(merchantId);
		GetMerchantDetails response = null;
		try {
			log.info("fetching merchant details from merchantOnBoarding");
			response = merchantServiceImpl.getMerchantDisplayInfo(req);
		} catch (ServiceException e) {
			log.info("service exception occurred while connecting to merchant on boarding.");
			log.info(e.getMessage());
			throw new PaymentsViewServiceException(
					PaymentsViewServiceExceptionCodes.ERROR_OCCURED_ON_MERCHANT_ONBOARDING
							.errCode(), e.getMessage(),
					ExceptionType.MERCHANT_ON_BOARDING);
		}
		return response;
	}
   
}