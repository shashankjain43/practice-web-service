package com.snapdeal.notifier.email.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.snapdeal.base.notification.email.EmailMessage;
import com.snapdeal.base.utils.StringUtils;
import com.snapdeal.base.vo.EmailTemplateVO;
import com.snapdeal.mail.client.model.ByteArrayAttachment;
import com.snapdeal.mail.client.model.EmailRequest;
import com.snapdeal.mail.client.model.EmailResponse;
import com.snapdeal.mail.client.service.exceptions.InitializationException;
import com.snapdeal.mail.client.service.exceptions.SendEmailException;
import com.snapdeal.mail.client.service.impl.EmailSender;
import com.snapdeal.notifier.email.task.IEmailSender;
import com.snapdeal.notifier.exception.InternalServerException;
import com.snapdeal.notifier.exception.ValidationException;

@Component
public class OctaneMailSender implements IEmailSender {

	private static final Logger LOG = 
			LoggerFactory.getLogger(OctaneMailSender.class);

	@Value("${snapdeal.notifier.email.octaneClientName}")
	private String octaneClientName;

	@Value("${snapdeal.notifier.email.octaneServiceUrl}")
	private String octaneServiceUrl;

	@Value("${snapdeal.notifier.email.octaneTriggerId}")
	private String octaneTriggerId;

	@Override
	public void initialize() {

		LOG.info("octaneClientName: " + octaneClientName + " octaneServiceUrl: " +
				octaneServiceUrl + " octaneTriggerId: " + octaneTriggerId);

		if (octaneClientName == null || octaneServiceUrl == null || 
				octaneTriggerId == null) {
			String msg = String.format("--octaneClientName: %s, octaneServiceUrl: %s, "
					+ "octaneTriggerId: %s----", octaneClientName, octaneServiceUrl, octaneTriggerId);
			LOG.info(msg);

			throw new IllegalStateException("Octane Api Email Sender "
					+ "is not properly configured");
		}

		boolean initialized = false;
		try {
			
			EmailSender.init(octaneClientName, octaneServiceUrl);
			initialized = true;
		} catch (IllegalStateException e){
			LOG.info("EmailSender already initialized. Trying to update Web service"
					+ " Base URL. Will not update octaneClientName. ");
			EmailSender.getInstance().updateWebServiceBaseURL(octaneServiceUrl);
			initialized = true;
		} catch (InitializationException e){
			LOG.info("EmailSender already initialized. Trying to update Web service"
					+ " Base URL. Will not update octaneClientName. ");
			EmailSender.getInstance().updateWebServiceBaseURL(octaneServiceUrl);
			initialized = true;
		} catch (Exception e) {
			LOG.error("Error while initializing EmailSender. Cause :  ", e);
		}
		if (initialized) {
			LOG.info("Successfully initialized OctaneEmailSender octaneClientName: "+
						octaneClientName + " octaneServiceUrl: " + octaneServiceUrl);
		}

	}

	@Override
	public EmailResponse send(EmailMessage message, 
			String templateHTMLStr, 
			String subject, 
			final Map<String, String> tags)
					throws ValidationException,
					InternalServerException {

			return send(message, 
						getEmailTemplateVO(templateHTMLStr, 
			                               tags,
			                               subject)
			           );
	}

	@Override
	public EmailResponse send(EmailMessage message, String templateName,
			String templateLocation, String subject, Map<String, String> tags)
			throws ValidationException, InternalServerException {
		
		try {
			return send(message, 
					getEmailTemplateVO(templateName, 
			                           tags,
			                           subject,
			                           templateLocation)
			       );
		} catch (IOException e) {
			throw new ValidationException("Exception in parsing tages of template");
		}
	}

	public EmailResponse send(EmailMessage message, EmailTemplateVO template) 
			throws ValidationException,
			InternalServerException{

		this.initialize();
		this.validateEmailInfo(message);
		StringBuilder recipients = new StringBuilder();
		String octaneResponse = "";
		String fromEmail = message.getFrom();
		String fromName = "Snapdeal";

		LOG.info("fromEmail: " + fromEmail + " fromName: " + fromName);
		if (message.getTo() == null || message.getTo().size() == 0) {
			message.addRecepients(template.getTo());
		}
		if (message.getTo() == null || message.getTo().size() == 0) {
			throw new ValidationException("No recipient specified.");
		} else {
			recipients.append(StringUtils.join(',', message.getTo()));
		}

		if (template.getCc() != null) {
			message.addCCs(template.getCc());
		}

		if (message.getCc() != null && message.getCc().size() > 0) {
			recipients.append(',').append(StringUtils.join(',', message.getCc()));
		}

		if (template.getBcc() != null) {
			message.addBCCs(template.getBcc());
		}

		if (message.getBcc() != null && message.getBcc().size() > 0) {
			recipients.append(',').append(StringUtils.join(',', message.getBcc()));
		}

		if (message.getFrom() == null) {
			fromName = template.getFrom().substring(0, template.getFrom().indexOf('<'));
			fromEmail = template.getFrom().substring(template.getFrom().indexOf('<') + 1,
					template.getFrom().indexOf('>'));
		} else {
			fromName = message.getFrom().substring(0, message.getFrom().indexOf('<'));
			fromEmail = message.getFrom().substring(message.getFrom().indexOf('<') + 1,
					message.getFrom().indexOf('>'));
		}

		EmailRequest emailRequest = new EmailRequest();

		emailRequest.setTriggerId(octaneTriggerId);
		emailRequest.setTo(recipients.toString());
		emailRequest.setSubject(StringEscapeUtils.escapeXml(template.getSubjectTemplate().evaluate(
				message.getTemplateParams())));
		emailRequest.setFromEmail(fromEmail);
		emailRequest.setFromName(fromName);

		if (template.getReplyTo() != null && !template.getReplyTo().isEmpty()) {
			emailRequest.setReplyToEmail(template.getReplyTo());
		} else {
			emailRequest.setReplyToEmail(message.getReplyTo());
		}

		// add message
		emailRequest
		.setHtmlContent((template.getBodyTemplate().evaluate(message.getTemplateParams()))
				+ ("<div class=\"unsubscribe\"/>"));

		// add attachment

		Map<String, Object> templateParams = message.getTemplateParams();
		if (templateParams != null && templateParams.containsKey("attachments")) {
			Map<String, byte[]> attachments = 
					(Map<String, byte[]>) templateParams.get("attachments");

			for (Map.Entry<String, byte[]> attachmentEntry : attachments.entrySet()) {
				emailRequest.addAttachment(new ByteArrayAttachment(attachmentEntry.getValue(),
						attachmentEntry.getKey()));
			}
		}

		// send
		LOG.info("Sending email of type {}, to recipients: {} with attchments {}",
				new Object[] { template.getName(), recipients.toString(),
				Joiner.on(",").join(emailRequest.getAttachments()) });
		EmailResponse response = null;
		for (String recipient : recipients.toString().split(",")) {
			emailRequest.setTo(recipient);
			try {
				response = EmailSender.getInstance().sendEmail(emailRequest);
				octaneResponse = response.getRequestId();
				LOG.info(String.format("Response ID  for %s email and recipient [%s]: %s",
						template.getName(), recipient, response.getRequestId()));
			} catch (SendEmailException e) {

				LOG.error(
						"error while sending email through Excomm api for recepient = : " +
								recipient, e);
				
				throw new InternalServerException("error while sending email through Excomm "
				         + "api for recepient = : " + recipient, e);
			}
		}
		return response;
	}

	private boolean validateEmailInfo(EmailMessage message) 
			throws ValidationException {

		try {
			Preconditions.checkNotNull(message);
			Preconditions.checkArgument(!Strings.isNullOrEmpty(message.getFrom()));
			Preconditions.checkArgument(message.getTo().size() != 0);
		} catch (IllegalArgumentException e) {
			throw new ValidationException("Excpetion being thrown while validating EmailMessage: "
					+ e.getMessage());
		}
		return true;
	}

	/**
	 * Loads template from file
	 * @param templateName
	 * @param tags
	 * @param subject
	 * @param templateLocation
	 * @return
	 * @throws IOException
	 */
	private EmailTemplateVO getEmailTemplateVO(String templateName, 
			Map<String, String> tags,
			String subject,
			String templateLocation) throws IOException {
		
		//Loading template from absolute path
		Properties p = new Properties();
		p.setProperty( "file.resource.loader.path", templateLocation);
		final VelocityEngine ve = new VelocityEngine();
		ve.init(p);
		
		Template t = ve.getTemplate(templateName);
		VelocityContext vc = new VelocityContext();
		if(tags!=null){
			for(Map.Entry<String, String> entry : tags.entrySet()){
				vc.put(entry.getKey(), entry.getValue());
			}
		}
		
		StringWriter writer = new StringWriter();
		BufferedWriter bw=new BufferedWriter(writer);
	        t.merge(vc,bw);
	        bw.flush();
	  
		com.snapdeal.base.velocity.Template bodyTemplate = 
				com.snapdeal.base.velocity.Template.compile(writer.toString());
		com.snapdeal.base.velocity.Template subjectTemplate = 
				com.snapdeal.base.velocity.Template.compile(subject);

		EmailTemplateVO template = new EmailTemplateVO();
		template.setSubjectTemplate(subjectTemplate);
		template.setBodyTemplate(bodyTemplate);

		return template;
	}

	/**
	 * Loads template from string
	 * @param templateHTMLStr
	 * @param tags
	 * @param subject
	 * @return
	 */
	private EmailTemplateVO getEmailTemplateVO(
			String templateHTMLStr, 
			Map<String, String> tags,
			String subject){
		
	    VelocityContext context = new VelocityContext();
	    if(tags!=null){
			for(Map.Entry<String, String> entry : tags.entrySet()){
				context.put(entry.getKey(), entry.getValue());
			}
		}

	    StringWriter swOut = new StringWriter();  
	    /**
	     * Merge data and template
	     */
	    Velocity.evaluate( context, swOut, "body log tag name", templateHTMLStr);
	    
	    com.snapdeal.base.velocity.Template bodyTemplate = 
				com.snapdeal.base.velocity.Template.compile(swOut.toString());
	    swOut.getBuffer().setLength(0);
	    
	    Velocity.evaluate( context, swOut, "subject log tag name ", subject);

		com.snapdeal.base.velocity.Template subjectTemplate = 
				com.snapdeal.base.velocity.Template.compile(swOut.toString());

		EmailTemplateVO template = new EmailTemplateVO();
		template.setSubjectTemplate(subjectTemplate);
		template.setBodyTemplate(bodyTemplate);

		return template;
	}
}
