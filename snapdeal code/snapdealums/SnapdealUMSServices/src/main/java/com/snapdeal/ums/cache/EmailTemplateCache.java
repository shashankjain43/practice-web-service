/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Aug 20, 2010
 *  @author singla
 */
package com.snapdeal.ums.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.snapdeal.base.annotations.Cache;
import com.snapdeal.base.utils.StringUtils;
import com.snapdeal.base.velocity.Template;
import com.snapdeal.base.vo.EmailTemplateVO;
import com.snapdeal.ums.core.entity.EmailTemplate;

@Cache(name = "emailTemplateCache")
public class EmailTemplateCache {
    private Map<String, EmailTemplate> emailTemplates    = new HashMap<String, EmailTemplate>();
    private List<EmailTemplate>        emailTemplateList = new ArrayList<EmailTemplate>();

    public void addEmailTemplate(EmailTemplate template) {
//        EmailTemplateVO templateVO = new EmailTemplateVO();
//        templateVO.setName(template.getName());
//        if (StringUtils.isNotEmpty(template.getTo())) {
//            templateVO.setTo(StringUtils.split(template.getTo()));
//        }
//        if (StringUtils.isNotEmpty(template.getCc())) {
//            templateVO.setCc(StringUtils.split(template.getCc()));
//        }
//        if (StringUtils.isNotEmpty(template.getBcc())) {
//            templateVO.setBcc(StringUtils.split(template.getBcc()));
//        }
//        if (StringUtils.isNotEmpty(template.getFrom())) {
//            templateVO.setFrom(template.getFrom());
//        }
//        if (StringUtils.isNotEmpty(template.getReplyTo())) {
//            templateVO.setReplyTo(template.getReplyTo());
//        }
//        templateVO.setBodyTemplate(Template.compile(template.getBodyTemplate()));
//        templateVO.setSubjectTemplate(Template.compile(template.getSubjectTemplate()));
//        templateVO.setEmailChannelId(template.getEmailChannelId());
        emailTemplates.put(template.getName(), template);
        emailTemplateList.add(template);
    }


    public EmailTemplate getTemplateByName(String name) {
        return emailTemplates.get(name);
    }

    public List<EmailTemplate> getEmailTemplates() {
        return emailTemplateList;
    }
}
