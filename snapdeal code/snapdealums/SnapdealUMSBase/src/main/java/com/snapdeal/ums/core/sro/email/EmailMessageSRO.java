/*
*  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
*  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*  
*  @version     1.0, 08-Jan-2013
*  @author naveen
*/
package com.snapdeal.ums.core.sro.email;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.notification.email.EmailMessage;
import com.snapdeal.core.sro.email.TemplateParam;

/**
 * Pls note you have to explicity set the Template Params in the SRO
 *  using setTemplateParams.
 * The constructore <code>EmailMessageSRO</code> won't copy the templateParams from EmailMessage entity.
 * @author naveen
 *
 */
public class EmailMessageSRO implements Serializable {

    /**
     * 
     */
    private static final long   serialVersionUID = -684862303801847711L;
    @Tag(1)
    private String              from;
    @Tag(2)
    private String              replyTo;
    @Tag(3)
    private List<String>        to               = new ArrayList<String>();
    @Tag(4)
    private List<String>        cc               = new ArrayList<String>();
    @Tag(5)
    private List<String>        bcc              = new ArrayList<String>();
    @Tag(6)
    private String              templateName;
    @Tag(7)
    private String              mailHTML;
    @Tag(8)
    private List<TemplateParam<? extends Serializable>> templateParams   = new ArrayList<TemplateParam<? extends Serializable>>();

    public EmailMessageSRO(EmailMessage message) {
        this.from = message.getFrom();
        this.replyTo = message.getReplyTo();
        this.to = message.getTo();
        this.cc = message.getCc();
        this.bcc = message.getBcc();
        this.templateName = message.getTemplateName();
        this.mailHTML = message.getMailHTML();
    }

    public EmailMessageSRO(List<String> to, String templateName) {
        this.to = to;
        this.templateName = templateName;
    }
    
    public EmailMessageSRO(String to, String templateName) {
        this.to = new ArrayList<String>(1);
        this.to.add(to);
        this.templateName = templateName;
    }
    
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public List<String> getCc() {
        return cc;
    }

    public void setCc(List<String> cc) {
        this.cc = cc;
    }

    public List<String> getBcc() {
        return bcc;
    }

    public void setBcc(List<String> bcc) {
        this.bcc = bcc;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getMailHTML() {
        return mailHTML;
    }

    public void setMailHTML(String mailHTML) {
        this.mailHTML = mailHTML;
    }

    public List<TemplateParam<? extends Serializable>> getTemplateParams() {
        return templateParams;
    }

    public void setTemplateParams(List<TemplateParam<? extends Serializable>> templateParams) {
        if (templateParams == null)
            return;
            this.templateParams = templateParams;
    }
    
}
