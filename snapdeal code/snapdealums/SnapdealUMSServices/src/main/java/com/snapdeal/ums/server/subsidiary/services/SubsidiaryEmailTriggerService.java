package com.snapdeal.ums.server.subsidiary.services;

import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.snapdeal.base.notification.email.EmailMessage;
import com.snapdeal.base.utils.StringUtils;
import com.snapdeal.base.velocity.Template;
import com.snapdeal.mail.client.model.EmailRequest;
import com.snapdeal.mail.client.model.EmailResponse;
import com.snapdeal.mail.client.service.exceptions.SendEmailException;
import com.snapdeal.ums.core.entity.EmailTemplate;

/**
 * 
 * @author Shishir
 *  This service prepares, validates the email request object for
 *         the trigger client and calls it
 * 
 */

@Service
public class SubsidiaryEmailTriggerService
{

    private static final Logger LOG = LoggerFactory
        .getLogger(SubsidiaryEmailTriggerService.class);
    

    public final ExecutorService executorService = new ThreadPoolExecutor(20,
        50, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    
    public void triggerEmail(SubsidiaryEmailMessage message, EmailTemplate template)
        throws SendEmailException
    {

        executorService.execute(new EmailRequestSender(message, template));

    }

    /**
     * 
     * Implements MultiThreading...
     * 
     */
    private final class EmailRequestSender implements Runnable
    {

        private final SubsidiaryEmailMessage message;

        private final EmailTemplate template;

        public EmailRequestSender(SubsidiaryEmailMessage message, EmailTemplate template)
        {

            this.message = message;
            this.template = template;
        }

        public void run()
        {
        	LOG.info("started email thread...");

            try {

                String fromName;
                String fromEmail;
                String replyTo;
                String sendTo;
                StringBuilder toEmail = new StringBuilder();

                replyTo = template.getReplyTo();
                // validating the email pattern

                if (replyTo == null || replyTo.length() == 0) {
                    LOG.info("Reply_to mail not specified");
                }

                if (replyTo.matches(".*<+.*")) {
                    replyTo = template.getReplyTo().substring(
                        template.getReplyTo().indexOf('<') + 1,
                        template.getReplyTo().indexOf('>'));
                }

                if (template.getFrom() == null
                    || template.getFrom().length() == 0) {
                    LOG.info("From mail and name not specified");
                }

                if (message.getFrom() == null) {
                    fromName = template.getFrom().substring(0,
                        template.getFrom().indexOf('<'));
                    fromEmail = template.getFrom().substring(
                        template.getFrom().indexOf('<') + 1,
                        template.getFrom().indexOf('>'));
                }
                else {
                    fromName = message.getFrom().substring(0,
                        message.getFrom().indexOf('<'));
                    fromEmail = message.getFrom().substring(
                        message.getFrom().indexOf('<') + 1,
                        message.getFrom().indexOf('>'));
                }

                if (template.getTo().matches(".*<+.*")) {
                    message.addRecepient(template.getTo().substring(
                        template.getTo().indexOf('<') + 1,
                        template.getTo().indexOf('>')));
                }

                if (message.getTo() == null || message.getTo().size() == 0) {
                    message.addRecepient(template.getTo());

                }

                if (message.getTo() == null || message.getTo().size() == 0) {
                    throw new IllegalArgumentException(
                        "No recipient specified.");
                }
                else {
                    toEmail.append(StringUtils.join(',', message.getTo()));
                }

                sendTo = toEmail.toString();

                // TODO Enhancement of multiple TO Mail ID's
                if (sendTo.matches(".*,+.*")) {
                	LOG.info("sending email to multiple clients");
                    sendMailToMultipleIDs(fromName, fromEmail, replyTo, sendTo);
                }
                else {
                	LOG.info("sending email to single client");
                    sendMailToSingleID(fromName, fromEmail, replyTo, sendTo);
                }

            }
            catch (SendEmailException e) {
                LOG.error("Failed sending email", e);
            }

        }

        private void sendMailToSingleID(String fromName, String fromEmail,
            String replyTo, String sendTo) throws SendEmailException
        {

            LOG.info("Preparing email request object to pass to email trigger client for: "
                + sendTo);

            if (message != null && template != null) {

                EmailRequest emailRequest = new EmailRequest();
                emailRequest.setTo(sendTo.trim());
                emailRequest.setFromEmail(fromEmail);

                emailRequest.setFromName(fromName);

                emailRequest.setHtmlContent(Template.compile(
                    template.getBodyTemplate()).evaluate(
                    message.getTemplateParams()));
                emailRequest.setTextContent(Template.compile(
                    template.getBodyTemplate()).evaluate(
                    message.getTemplateParams()));
                emailRequest.setReplyToEmail(replyTo);
                emailRequest.setSubject(Template.compile(
                    template.getSubjectTemplate()).evaluate(
                    message.getTemplateParams()));
                emailRequest.setTriggerId(template.getTriggerId());

                
                try{
                EmailResponse emailResponse = com.snapdeal.mail.client.service.impl.EmailSender
                    .getInstance().sendEmail(emailRequest);
                
                
                }catch(Exception e){
                    LOG.error("Exception while sending "+template.getName()+" to "+sendTo,e);
                    e.printStackTrace();
                }
            }
        }

        private void sendMailToMultipleIDs(String fromName, String fromEmail,
            String replyTo, String sendTo) throws SendEmailException
        {

            StringTokenizer mailIds = new StringTokenizer(sendTo, ",");
            while (mailIds.hasMoreElements()) {
                sendTo = mailIds.nextToken();

                sendMailToSingleID(fromName, fromEmail, replyTo, sendTo);
            }
        }
    }
}

