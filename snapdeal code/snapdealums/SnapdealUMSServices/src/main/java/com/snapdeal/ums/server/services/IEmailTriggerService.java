package com.snapdeal.ums.server.services;

import com.snapdeal.base.notification.email.EmailMessage;
import com.snapdeal.mail.client.service.exceptions.SendEmailException;
import com.snapdeal.ums.core.entity.EmailTemplate;
/**
 * 
 * @author lovey
 *
 */

public interface IEmailTriggerService
{

    public void triggerEmail(EmailMessage message, EmailTemplate template) throws SendEmailException;
}

