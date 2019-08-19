/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *  @version     1.0, Oct 21, 2010
 *  @author Vikash
 */
package com.snapdeal.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.snapdeal.ums.admin.services.IEmailTemplateService;
import com.snapdeal.base.entity.EmailChannel;
import com.snapdeal.base.notification.INotificationService;
import com.snapdeal.base.utils.ValidatorUtils;
import com.snapdeal.ums.core.dto.EmailChannelDTO;
import com.snapdeal.ums.core.entity.EmailTemplate;
import com.snapdeal.web.controller.forms.EmailTemplateAdminForm;

@Controller
@RequestMapping("/admin")
public class EmailTemplateController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @Autowired
    private IEmailTemplateService emailTemplateService;

    @Autowired
    private INotificationService notificationService;

    @RequestMapping("/emailTemplate")
    public String showEmailTemplate(ModelMap modelMap) {
        EmailTemplateAdminForm emailTemplateAdminForm = new EmailTemplateAdminForm();
        modelMap.addAttribute("emailTemplateAdminForm", emailTemplateAdminForm);
        return "/admin/email/emailTemplate";
    }

    @RequestMapping("/emailTemplate/fetchEmailData")
    public String fetchEmailData(@RequestParam("name") String templateName, ModelMap modelMap) {
        EmailTemplateAdminForm emailTemplateAdminForm;
        EmailTemplate emailTemplate = emailTemplateService.getEmailTemplateByName(templateName);
        if (emailTemplate != null) {
            emailTemplateAdminForm = new EmailTemplateAdminForm(emailTemplate);
            List<EmailChannel> channels = notificationService.getAllEmailChannels();
            List<EmailChannelDTO> channelDTOList = new ArrayList<EmailChannelDTO>();
            for (EmailChannel channel : channels) {
                channelDTOList.add(new EmailChannelDTO(channel));
            }
            emailTemplateAdminForm.setChannels(channelDTOList);
        } else {
            emailTemplateAdminForm = new EmailTemplateAdminForm();
            modelMap.addAttribute("message", "No matching Record found for this template name.");
        }
        modelMap.addAttribute("emailTemplateAdminForm", emailTemplateAdminForm);
        return "/admin/email/emailTemplate";
    }

    @RequestMapping("/emailTemplate/updateEmailTemplate")
    public String updateEmailTemplate(@Valid EmailTemplateAdminForm emailTemplateAdminForm, BindingResult result, ModelMap modelMap) {
        if (!isValidEmail(emailTemplateAdminForm.getEmailTemplate().getFrom())) {
            result.rejectValue("emailTemplate.from", "EMAIL_INVALID", "Invalid From email address.");
        }
        if (emailTemplateAdminForm.getEmailTemplate().getTo().length() > 0) {
            if (!isValidEmail(emailTemplateAdminForm.getEmailTemplate().getTo())) {
                result.rejectValue("emailTemplate.to", "EMAIL_INVALID", "Invalid To email address.");
            }
        }
        if (emailTemplateAdminForm.getEmailTemplate().getCc().length() > 0) {
            if (!isValidEmail(emailTemplateAdminForm.getEmailTemplate().getCc())) {
                result.rejectValue("emailTemplate.cc", "EMAIL_INVALID", "Invalid CC email address.");
            }
        }
        if (emailTemplateAdminForm.getEmailTemplate().getBcc().length() > 0) {
            if (!isValidEmail(emailTemplateAdminForm.getEmailTemplate().getBcc())) {
                result.rejectValue("emailTemplate.bcc", "EMAIL_INVALID", "Invalid BCC email address.");
            }
        }
        if (!isValidEmail(emailTemplateAdminForm.getEmailTemplate().getReplyTo())) {
            result.rejectValue("emailTemplate.replyTo", "EMAIL_INVALID", "Invalid Replyto email address.");
        }

        if (result.hasErrors()) {
            modelMap.addAttribute("emailTemplateAdminForm", emailTemplateAdminForm);
            return "/admin/email/emailTemplate";
        }
        emailTemplateService.update(emailTemplateAdminForm.getEmailTemplate());
        emailTemplateAdminForm.getEmailTemplate().setId(null);
        modelMap.addAttribute("emailTemplateForm", emailTemplateAdminForm);
        modelMap.addAttribute("message", "Successfully Updated.");
        return "/admin/email/emailTemplate";
    }

    private boolean isValidEmail(String email) {

        if (email.contains(",")) {
            String sEmail[] = email.split(",");
            for (int i = 0; i < sEmail.length; i++) {
                email = sEmail[i];
                if (email.contains("<")) {
                    email = email.substring(email.indexOf('<') + 1, email.indexOf('>'));
                }
                if (!ValidatorUtils.isEmailValid(email)) {
                    return false;
                }
            }
        } else {
            if (email.contains("<")) {
                email = email.substring(email.indexOf('<') + 1, email.indexOf('>'));
            }
        }
        return ValidatorUtils.isEmailValid(email);
    }
}

