package com.snapdeal.notifier.email.service;


import com.snapdeal.notifier.email.reponse.EmailResponse;
import com.snapdeal.notifier.email.request.EmailMessage;
import com.snapdeal.notifier.exception.InternalServerException;
import com.snapdeal.notifier.exception.ValidationException;

public interface IEmailService {
   
   public EmailResponse sendEmail(EmailMessage message, 
                           boolean transactional) 
            throws ValidationException,
                   InternalServerException;
}
