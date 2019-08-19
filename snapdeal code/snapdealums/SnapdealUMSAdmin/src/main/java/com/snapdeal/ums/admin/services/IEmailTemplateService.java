/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *  @version     1.0, Oct 21, 2010
 *  @author Vikash
 */
package com.snapdeal.ums.admin.services;

import com.snapdeal.ums.core.entity.EmailTemplate;

public interface IEmailTemplateService {
    public EmailTemplate update(EmailTemplate emailTemplate);

    public EmailTemplate getEmailTemplateByName(String templateName);
}

