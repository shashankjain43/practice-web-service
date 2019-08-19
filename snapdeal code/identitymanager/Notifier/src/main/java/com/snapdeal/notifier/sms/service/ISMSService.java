package com.snapdeal.notifier.sms.service;

import com.snapdeal.notifier.exception.InternalServerException;
import com.snapdeal.notifier.exception.ValidationException;
import com.snapdeal.notifier.sms.request.SMSRequest;
import com.snapdeal.sms.client.model.SmsSenderResponse;

public interface ISMSService {
   
   public SmsSenderResponse sendSMS(SMSRequest smsRequest) 
            throws ValidationException,
                   InternalServerException;
}
