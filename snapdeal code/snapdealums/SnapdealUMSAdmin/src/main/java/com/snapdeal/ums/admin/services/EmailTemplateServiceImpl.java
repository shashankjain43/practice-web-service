/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *  @version     1.0, Oct 21, 2010
 *  @author Vikash
 */
package com.snapdeal.ums.admin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.ums.admin.dao.IEmailTemplateDao;
import com.snapdeal.ums.admin.services.IEmailTemplateService;
import com.snapdeal.ums.core.entity.EmailTemplate;

@Transactional
@Service("emailTemplateService")
public class EmailTemplateServiceImpl implements IEmailTemplateService {
    private IEmailTemplateDao emailTemplateDao;

    @Autowired
    public void setEmailTemplateDao(IEmailTemplateDao emailTemplateDao) {
        this.emailTemplateDao = emailTemplateDao;
    }

    @Override
    public EmailTemplate update(EmailTemplate emailTemplate) {
        return emailTemplateDao.update(emailTemplate);
    }

    @Override
    public EmailTemplate getEmailTemplateByName(String templateName) {
        return emailTemplateDao.getEmailTemplateByName(templateName);
    }
}

