package com.snapdeal.notifier.email.template;

import com.snapdeal.notifier.email.constant.CommonConstant;
import com.snapdeal.notifier.exception.ValidationException;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * This class will be used to cache all email Templates.
 * User have to call regiterTemplate before using email
 * application
 */
public class EmailTemplateCache {
   
   private Map<String, String> emailTemplates;
   
   private static EmailTemplateCache instance = new EmailTemplateCache();
   
   private EmailTemplateCache(){
      emailTemplates = new HashMap<String, String>();
   }
   
   public static EmailTemplateCache getInstance(){
      return instance;
   }
   
	public String getTemplate(String templateKey) throws ValidationException {

		String templateHTMLStr = emailTemplates.get(templateKey);
		if (templateHTMLStr == null) {
			if (StringUtils.contains(templateKey,
					CommonConstant.VERIFICATION_OTP_EMAIL_TEMPLATE))
				templateHTMLStr = emailTemplates
						.get(CommonConstant.GLOBAL_VERIFICATION_OTP_EMAIL_TEMPLATE);
			if (templateHTMLStr == null) {
				throw new ValidationException("EmailTemplate corresponding to "
						+ templateKey + " Register it first");
			}
		}
		return templateHTMLStr;
	}
   
   public void registerTemplates(Map<String, String> emailTemplates){
      this.emailTemplates = emailTemplates;
   }
}
