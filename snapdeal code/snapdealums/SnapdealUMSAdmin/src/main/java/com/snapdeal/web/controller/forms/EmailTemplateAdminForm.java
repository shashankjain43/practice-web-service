/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *  @version     1.0, Oct 21, 2010
 *  @author Vikash
 */
package com.snapdeal.web.controller.forms;

import java.util.List;

import javax.validation.Valid;

import com.snapdeal.ums.core.dto.EmailChannelDTO;
import com.snapdeal.ums.core.entity.EmailTemplate;

public class EmailTemplateAdminForm {
    @Valid
    EmailTemplate emailTemplate;

    private List<EmailChannelDTO> channels;

    public EmailTemplateAdminForm() {
    }

    public EmailTemplateAdminForm(EmailTemplate emailTemplate) {
        this.emailTemplate = emailTemplate;
    }

    public EmailTemplate getEmailTemplate() {
        return this.emailTemplate;
    }

    public void setEmailTemplate(EmailTemplate emailTemplate){
        this.emailTemplate=emailTemplate;
    }

    public void setChannels(List<EmailChannelDTO> channels) {
        this.channels = channels;
    }

    public List<EmailChannelDTO> getChannels() {
        return channels;
    }

}

