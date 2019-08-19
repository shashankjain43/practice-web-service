package com.snapdeal.notifier.sms.request;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Data
@Component
public class SMSTemplateInfo {

	@Value("${sms.template.otp.templateId}")
	private Integer templateId;
	@Value("${sms.template.otp.channelId}")
	private Integer channelId;
	@Value("${sms.template.otp.templateName}")
	private String templateName;
	@Value("${sms.template.otp.templateBody}")
	private String templateBody;
	@Value("${sms.template.otp.dndScrubbingOn}")
	private boolean dndScrubbingOn;

}
